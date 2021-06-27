package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.DateTimeController;
import model.dao.AttendanceDAO;
import model.dao.HolidayDAO;
import model.dao.MonthDAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.Holiday;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchAttendance
 * 対象月のユーザーの勤務表を取得するクラス。
 */
@WebServlet("/SearchAttendance")
public class SearchAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchAttendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対してはホーム画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して対象月のユーザーの勤務表を取得する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp = "attendance_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SearchAttendance.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = (String) session.getAttribute("loginUserId");
				int attendanceId = Integer.parseInt(userId);
				int month = Integer.parseInt(request.getParameter("month"));
				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				// 管理者権限の場合、3日まで前の月の勤務表を表示
				// 一般権限の場合、2日まで前の月の勤務表を表示
				if (month == 13) {
					if (session.getAttribute("loginUserAuthority").toString().contentEquals("2")) {
						if (ld.getDayOfMonth() < 4) {
							month = ld.minusMonths(1).getMonthValue();
						} else {
							month = ld.getMonthValue();
						}
					} else {
						if (ld.getDayOfMonth() < 3) {
							month = ld.minusMonths(1).getMonthValue();
						} else {
							month = ld.getMonthValue();
						}
					}
				}

				if (session.getAttribute("type") != null) {
					// 勤務表入力画面以外の遷移の場合、セッション情報を削除
					if (!session.getAttribute("type").toString().contentEquals("1")) {
						session.removeAttribute("id");
						session.removeAttribute("month");
						session.removeAttribute("type");
					} else {
						// 管理者権限で対象ユーザーの編集の場合、IDと対象月をセット
						if (session.getAttribute("month") != null && session.getAttribute("id") != null) {
							month = Integer.parseInt(session.getAttribute("month").toString());
							attendanceId = Integer.parseInt(session.getAttribute("id").toString());
							session.removeAttribute("month");
						} else if (session.getAttribute("id") != null) {
							attendanceId = Integer.parseInt((String) (session.getAttribute("id")));
						}
					}
				}

				MonthDAO monthDAO = MonthDAO.getInstance();

				// 接続
				monthDAO.dbConnect(logger);
				// ステートメント作成
				monthDAO.createSt();

				int normalDay = monthDAO.searchNormalDay(month);

				// 1月時点で12月を対象とした場合は昨年になる
				int year = ld.getYear();
				if (ld.getMonthValue() == 1 && month == 12) {
					year--;
				}
				logger.log(Level.INFO,
						"[SearchAttendance.java]/LoginUser : " + userId + "/Year:" + year + "/Month:" + month);

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 対象ユーザーの情報を取得
					staffDAO.searchUser(attendanceId, staff);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchAttendance.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}

				HolidayDAO holidayDAO = HolidayDAO.getInstance();
				Holiday holiday = new Holiday();

				if (month != 6 && month != 10 && month != 12) {
					try {
						// 接続
						holidayDAO.dbConnect(logger);
						// ステートメント作成
						holidayDAO.createSt();
						// 対象月の休日情報を取得する
						holidayDAO.setHoliday(year, month, holiday);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						holidayDAO.dbDiscon();
					}
				}

				AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
				Attendance attendance = new Attendance();

				try {
					// 接続
					attendanceDAO.dbConnect(logger);
					// ステートメント作成
					attendanceDAO.createSt();
					// 対象月の勤務表を取得(直接入力かどうかを判定)
					attendanceDAO.searchAttendance(attendanceId, month, year, attendance);

					//取得した月の日数
					int days = DateTimeController.getMonthDays(year, month);
					// 直接入力の場合は、必要情報を取得。違う場合は再度勤務表を取得する。
					if (attendance.getDivision(1) == 10) {
						mapMsg.put("operatingTime", "" + attendance.getStartTime(1));
						mapMsg.put("operatingDays", "" + attendance.getFinishTime(1));
						mapMsg.put("absenceDays", "" + attendance.getBreakTime(1));
						mapMsg.put("substituteDays", "" + attendance.getRemarks(1));
						mapMsg.put("paidDays", "" + attendance.getMemo(1));
						mapMsg.put("type", "1");
						if (days != 29) {
							mapMsg.put("month", "" + month);
						} else {
							mapMsg.put("month", "22");
						}
						attendanceDAO.searchAttendance(attendanceId, month, year, attendance, holiday);
						//追加
						for (int i = 1; i <= days; i++) {
							mapMsg.put("break" + i, attendance.getBreakTime(i));
							mapMsg.put("division" + i, "" + attendance.getDivision(i));
							if (attendance.getStartTime(i).contentEquals("00:00")) {
								if (attendance.getDivision(i) == 1) {
									mapMsg.put("start" + i, staff.getStartTime(1));
								} else {
									mapMsg.put("start" + i, attendance.getStartTime(i));
								}
							} else {
								mapMsg.put("start" + i, attendance.getStartTime(i));
							}
							if (attendance.getFinishTime(i).contentEquals("00:00")) {
								if (attendance.getDivision(i) == 1) {
									mapMsg.put("finish" + i, staff.getFinishTime(1));
								} else {
									mapMsg.put("finish" + i, attendance.getFinishTime(i));
								}
							} else {
								mapMsg.put("finish" + i, attendance.getFinishTime(i));
							}
							mapMsg.put("remarks" + i, attendance.getRemarks(i));
						}
						if (month != 6 || month != 10 || month != 12) {
							for (int i = 1; i <= holiday.getNumbersHoliday(); i++) {
								mapMsg.put("holiday" + i, holiday.getHoliday(i));
							}
						}
						mapMsg.put("numbersHoliday", "" + holiday.getNumbersHoliday());
						mapMsg.put("memo", attendance.getMemo(1));
					} else {
						// 再度、対象月の勤務表を取得
						attendanceDAO.searchAttendance(attendanceId, month, year, attendance, holiday);
						//追加
						for (int i = 1; i <= days; i++) {
							mapMsg.put("break" + i, attendance.getBreakTime(i));
							mapMsg.put("division" + i, "" + attendance.getDivision(i));
							if (attendance.getStartTime(i).contentEquals("00:00")) {
								if (attendance.getDivision(i) == 1) {
									mapMsg.put("start" + i, staff.getStartTime(1));
								} else {
									mapMsg.put("start" + i, attendance.getStartTime(i));
								}
							} else {
								mapMsg.put("start" + i, attendance.getStartTime(i));
							}
							if (attendance.getFinishTime(i).contentEquals("00:00")) {
								if (attendance.getDivision(i) == 1) {
									mapMsg.put("finish" + i, staff.getFinishTime(1));
								} else {
									mapMsg.put("finish" + i, attendance.getFinishTime(i));
								}
							} else {
								mapMsg.put("finish" + i, attendance.getFinishTime(i));
							}
							mapMsg.put("remarks" + i, attendance.getRemarks(i));
						}
						// 祝日情報を追加
						if (month != 6 || month != 10 || month != 12) {
							for (int i = 1; i <= holiday.getNumbersHoliday(); i++) {
								mapMsg.put("holiday" + i, holiday.getHoliday(i));
							}
						}
						mapMsg.put("numbersHoliday", "" + holiday.getNumbersHoliday());
						//閏年場合のみ22を受渡しJS側で条件分岐する
						if (days != 29) {
							mapMsg.put("month", "" + month);
						} else {
							mapMsg.put("month", "22");
						}
						mapMsg.put("operatingDays", "" + attendance.getOperatingDays());
						mapMsg.put("operatingTime", "" + attendance.getOperatingTime());
						mapMsg.put("absenceDays", "" + attendance.getAbsence());
						mapMsg.put("overNightDays", "" + attendance.getOverNight());
						mapMsg.put("substituteDays", "" + attendance.getSubstitute());
						mapMsg.put("paidDays", "" + attendance.getPaid());
						mapMsg.put("type", "0");
						mapMsg.put("memo", attendance.getMemo(1));
					}
					// 自社稼働の状況を取得
					attendanceDAO.searchInAttendance(attendanceId, month, year, attendance);
					for (int i = 1; i <= attendance.getNumbersRecords(); i++) {
						mapMsg.put("inDay" + i, attendance.getDay(i));
						mapMsg.put("inStart" + i, attendance.getStartTime(i));
						mapMsg.put("inFinish" + i, attendance.getFinishTime(i));
						mapMsg.put("inRemarks" + i, attendance.getRemarks(i));
					}
					mapMsg.put("inDays", "" + attendance.getNumbersRecords());
					mapMsg.put("inTime", "" + attendance.getInTime());
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchAttendance.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					attendanceDAO.dbDiscon();
				}
				// 勤怠報告書のデータがあるかを確認
				if (new File(Properties.SAVE_DIR_PATH + attendanceId + "/workschedule" + year + month + ".pdf")
						.exists()) {
					mapMsg.put("file", "exist");
				} else {
					mapMsg.put("file", "none");
				}
				mapMsg.put("defaultStart", "" + staff.getStartTime(1));
				mapMsg.put("defaultFinish", "" + staff.getFinishTime(1));
				mapMsg.put("authority", "" + session.getAttribute("loginUserAuthority"));
				mapMsg.put("editUser", staff.getStaffName(1));
				if (staff.getAttendanceFlag(1) == 1) {
					mapMsg.put("submitFlag", "提出");
				} else {
					mapMsg.put("submitFlag", "未提出");
				}
				mapMsg.put("userId", "" + attendanceId);
				mapMsg.put("year", "" + year);
				mapMsg.put("normalDay", "" + normalDay);

				//マッパ(JSON <-> Map, List)
				ObjectMapper mapper = new ObjectMapper();
				//json文字列
				String jsonStr = mapper.writeValueAsString(mapMsg); //list, map
				//ヘッダ設定
				response.setContentType("application/json;charset=UTF-8"); //JSON形式, UTF-8
				//pwオブジェクト
				PrintWriter pw = response.getWriter();
				//出力
				pw.print(jsonStr);
				//クローズ
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[SearchAttendance.java]" + e.toString(), e.getMessage());
				errorCheck = 1;
			} finally {
				handler.close();
				if (errorCheck == 1 || errorCheck == 2) {
					response.sendRedirect(jsp);
				}
			}
		}
	}
}
