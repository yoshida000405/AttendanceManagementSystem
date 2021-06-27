package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.dao.HolidayDAO;
import model.dao.MonthDAO;
import model.entity.Holiday;

/**
 * Controller implementation class DateTimeController
 * 日付を扱うクラス。
 */
public class DateTimeController {
	public void check(Logger logger) throws SecurityException, IOException {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				MonthDAO monthDAO = MonthDAO.getInstance();
				try {
					monthDAO.resetDemandCheck();

					HolidayDAO holidayDAO = HolidayDAO.getInstance();
					Holiday holiday = new Holiday();

					int normalDay = 0;
					int year = LocalDate.now().getYear();
					Calendar cal = Calendar.getInstance();
					List<Integer> holidayList = new ArrayList<Integer>();
					for (int j = 1; j <= 12; j++) {
						holidayDAO.setHoliday(year, j, holiday);
						for (int k = 1; k < holiday.getNumbersHoliday(); k++) {
							holidayList.add(Integer.parseInt(holiday.getHoliday(k)));
						}
						for (int i = 0; i < 31; i++) {
							cal.set(year, j, i);
							int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
							//土曜日もしくは日曜日でない場合
							if ((i + dayOfWeek) % 7 != 0 && (i + dayOfWeek) % 7 != 1) {
								if (j == 1) {
									if (i != 1 || i != 2) {
										normalDay++;
									}
								} else {
									normalDay++;
								}
								if (holidayList.contains(i)) {
									normalDay--;
								}
								if (i > 28) {
									switch (j) {
									case 2:
										if ((year % 4) == 0) {
											if (i == 29) {
												normalDay++;
											}
										}
										break;
									case 1:
									case 3:
									case 5:
									case 7:
									case 8:
									case 10:
									case 12:
										if (j != 12) {
											normalDay++;
										}
										break;
									case 4:
									case 6:
									case 9:
									case 11:
										if (i < 31) {
											normalDay++;
										}
										break;
									}
								} else {
									normalDay++;
								}
							}
						}
						monthDAO.updateNormalDay(j, normalDay);
						logger.log(Level.INFO,
								"[DatetimeController.java]/updateNormalDay/Month: " + j + "/NormalDay: " + normalDay);
					}
					logger.log(Level.INFO, "[DatetimeController.java]/resetTimer");
				} catch (NoSuchAlgorithmException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
			}
		};

		try {
			LocalDate days = LocalDate.now();
			int year = days.getYear();
			int month = 1;
			int day = 1;
			String time = "00:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date = format.parse("" + year + "/" + month + "/" + day + " " + time);
			timer.schedule(task, date);

			logger.log(Level.INFO, "[PTOController.java]/schedule/" + date);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			logger.log(Level.WARNING, "[PTOController.java]" + e.toString(), e.getMessage());
		}
	}

	/**
	 * @param month - 月
	 * @param year - 年
	 * 指定されたyearとmonthから月末日を取得する。
	 */
	public static int getMonthDays(int year, int month) {
		int days = 0;

		switch (month) {
		case 2:
			if (year % 4 == 0) {
				days = 29;
			} else {
				days = 28;
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		}

		return days;
	}
}
