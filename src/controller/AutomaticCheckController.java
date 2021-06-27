package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer; // 今回追加する処理
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import model.dao.SkillDAO;
import model.dao.StaffDAO;
import model.dao.TimerDAO;
import model.entity.Skill;
import model.entity.Staff;

/**
 * Controller implementation class SubmitCheckController
 * 自動処理を行うクラス。
 */
public class AutomaticCheckController {
	/**
	 * 自動処理を設定する。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void automatic(Logger logger) throws SecurityException, IOException {

		String subject1 = "勤務表の提出依頼";
		String content1 = "お疲れ様です。\n\n今月分の勤務表が未提出です。\n確認の後、提出をお願いします。\n\n以上、よろしくお願い致します。";
		String subject2 = "請求書の提出依頼";
		String content2 = "お疲れ様です。\n\n今月分の請求書が未提出です。\n確認の後、提出をお願いします。\n\n以上、よろしくお願い致します。";
		String subject3 = "勤務表及び請求書の提出依頼";
		String content3 = "お疲れ様です。\n\n今月分の勤務表及び請求書が未提出です。\n確認の後、提出をお願いします。\n\n以上、よろしくお願い致します。";
		String subject4 = "スキルシートの更新依頼";
		String content4 = "お疲れ様です。\n\n今月分のスキルシートが更新されていません。\n確認の後、更新をお願いします。\n\n以上、よろしくお願い致します。";

		LocalDate days = LocalDate.now();
		int year = days.getYear();
		int month = days.getMonthValue();
		int day = days.getDayOfMonth();

		Timer timer1 = new Timer();
		Timer timer2 = new Timer();
		Timer timer3 = new Timer();
		TimerTask task1 = new TimerTask() {
			public void run() {
				StaffDAO staffDAO = StaffDAO.getInstance();

				Staff staff = new Staff();

				boolean mailChkFlag = false;

				String[] address = new String[100];
				String[] subject = new String[100];
				String[] content = new String[100];
				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();

					mailChkFlag = staffDAO.searchAllUser(staff);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
				} finally {
					staffDAO.dbDiscon();
				}

				if (mailChkFlag) {
					int index = 0;
					for (int i = 0; i < staff.getNumbersRecords(); i++) {
						if (staff.getAttendanceFlag(i + 1) == 0 && staff.getDemandFlag(i + 1) == 0) {
							if (staff.getGroup(i + 1).contains(",5,")) {
								address[index] = staff.getMailAddress(i + 1);
								subject[index] = subject3;
								content[index] = content3;
								staff.setStaffName(index + 1, staff.getStaffName(i + 1));
								index++;
							} else {
								address[index] = staff.getMailAddress(i + 1);
								subject[index] = subject2;
								content[index] = content2;
								staff.setStaffName(index + 1, staff.getStaffName(i + 1));
								index++;
							}
						} else if (staff.getAttendanceFlag(i + 1) == 0) {
							if (staff.getGroup(i + 1).contains(",5,")) {
								address[index] = staff.getMailAddress(i + 1);
								subject[index] = subject1;
								content[index] = content1;
								staff.setStaffName(index + 1, staff.getStaffName(i + 1));
								index++;
							}
						} else if (staff.getDemandFlag(i + 1) == 0) {
							address[index] = staff.getMailAddress(i + 1);
							subject[index] = subject2;
							content[index] = content2;
							staff.setStaffName(index + 1, staff.getStaffName(i + 1));
							index++;
						}
						if (staff.getSkillFlag(i + 1) == 0) {
							address[index] = staff.getMailAddress(i + 1);
							subject[index] = subject4;
							content[index] = content4;
							staff.setStaffName(index + 1, staff.getStaffName(i + 1));
							index++;
						}
					}
					SendMailController con = new SendMailController();
					try {
						try {
							mailChkFlag = con.send(subject, content, address, staff, index, logger);

							logger.log(Level.INFO, "[SubmitCheckController.java]/send");
						} catch (SecurityException | IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
							logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
						}
					} catch (MessagingException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
					}

				}
			}
		};

		TimerTask task2 = new TimerTask() {
			public void run() {
				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				SkillDAO skillDAO = SkillDAO.getInstance();
				Skill skill = new Skill();

				int month = days.getMonthValue();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 社員情報を取得
					staffDAO.resetFlag();
					// 対象月の全社員分のスキルシート情報を取得
					skillDAO.searchAllSkill(month, year, skill, staff);
					// 対象月の全社員分のスキルシート情報を横置き
					skillDAO.writeAllSkill(month, year, staff);

					logger.log(Level.INFO, "[SubmitCheckController.java]/autoWriteSkill");
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
				} finally {
					staffDAO.dbDiscon();
				}

				TimerDAO timerDAO = TimerDAO.getInstance();
				model.entity.Timer timer = new model.entity.Timer();

				try {
					// 接続
					timerDAO.dbConnect(logger);
					// ステートメント作成
					timerDAO.createSt();
					// タイマーを未稼働状態に戻す
					timerDAO.resetTimer(timer, 3);
					logger.log(Level.INFO, "[SubmitCheckController.java]/resetTimer");
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
				} finally {
					timerDAO.dbDiscon();
				}
			}
		};

		TimerTask task3 = new TimerTask() {
			public void run() {
				StaffDAO staffDAO = StaffDAO.getInstance();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 全ての提出フラッグを未提出にする
					staffDAO.resetFlag();
					logger.log(Level.INFO, "[SubmitCheckController.java]/resetFlag");
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
				} finally {
					staffDAO.dbDiscon();
				}

				TimerDAO timerDAO = TimerDAO.getInstance();
				model.entity.Timer timer = new model.entity.Timer();

				try {
					// 接続
					timerDAO.dbConnect(logger);
					// ステートメント作成
					timerDAO.createSt();
					// タイマーを未稼働状態に戻す
					timerDAO.resetTimer(timer, 2);
					logger.log(Level.INFO, "[SubmitCheckController.java]/resetTimer");
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
				} finally {
					timerDAO.dbDiscon();
				}
			}
		};

		String time = "00:00:00";

		switch (month) {
		case 2:
			if (year % 4 == 0) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date;
		try {
			//			date = format.parse(""+year+"/"+month+"/"+day+" "+time);
			//			timer1.schedule(task1,date);
			//			logger.log(Level.INFO, "[SubmitCheckController.java]/schedule1/"+date);
			if (month == 12) {
				month = 1;
			} else {
				month++;
			}
			date = format.parse("" + year + "/" + month + "/03 " + time);
			timer2.schedule(task2, date);
			logger.log(Level.INFO, "[SubmitCheckController.java]/schedule2/" + date);

			date = format.parse("" + year + "/" + month + "/04 " + time);
			timer3.schedule(task3, date);
			logger.log(Level.INFO, "[SubmitCheckController.java]/schedule3/" + date);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			logger.log(Level.WARNING, "[SubmitCheckController.java]" + e.toString(), e.getMessage());
		}
	}
}