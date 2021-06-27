package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.PTO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchAttendance
 * 対象月のユーザーの勤務表を更新するクラス。
 */
@WebServlet("/UpdateAttendance")
public class UpdateAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateAttendance() {
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
	 * データベースに接続して対象月のユーザーの勤務表を更新する。
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
			Logger logger = Logger.getLogger(UpdateAttendance.class.getName());
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
				int month = LocalDate.now(ZoneId.of("Asia/Tokyo")).getMonthValue();
				if (request.getParameter("month") != null) {
					month = Integer.parseInt(request.getParameter("month"));
				}
				int year = LocalDateTime.now().getYear();
				if (LocalDateTime.now().getMonthValue() == 1 && month == 12) {
					year--;
				}
				if (session.getAttribute("id") != null) {
					attendanceId = Integer.parseInt((String) (session.getAttribute("id")));
				}
				String type = request.getParameter("type");

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				if (request.getParameter("submit").contentEquals("1")) {
					staff.setAttendanceFlag(1, 1);
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 提出状態に更新する
						staffDAO.updateFlag(attendanceId, Integer.parseInt(userId), 1, staff);
						logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId + "/Year : "
								+ year + "/Month : " + month + "/updateFlag");
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						staffDAO.dbDiscon();
					}

					mapMsg.put("submitFlag", "提出");
				}

				// 基本業務時間変更
				if (type.contentEquals("1")) {
					staff = new Staff();

					staff.setStartTime(1, request.getParameter("defaultStart"));
					staff.setFinishTime(1, request.getParameter("defaultFinish"));

					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 基本時間を更新する
						staffDAO.updateDefaultTime(attendanceId, Integer.parseInt(userId), staff);
						logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId + "/Year : "
								+ year + "/Month : " + month + "/updateDefaultTime");
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						staffDAO.dbDiscon();
					}
					session.setAttribute("month", month);
				}

				// 通常の勤怠更新
				if (type.contentEquals("2")) {
					AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
					Attendance attendance = new Attendance();

					//取得した月の日数
					int days = DateTimeController.getMonthDays(year, month);

					//attendanceインスタンスに更新するデータをセット
					for (int i = 1; i <= days; i++) {
						if (request.getParameter("division" + i).isEmpty()) {
							attendance.setDivision(i, 0);
						} else {
							attendance.setDivision(i, Integer.parseInt(request.getParameter("division" + i)));
						}
						attendance.setBreakTime(i, request.getParameter("break" + i));
						attendance.setStartTime(i, request.getParameter("start" + i));
						attendance.setFinishTime(i, request.getParameter("finish" + i));
						attendance.setRemarks(i, request.getParameter("remarks" + i));
						attendance.setWorkHours(i);
					}
					attendance.setMemo(1, request.getParameter("memo"));

					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();

						attendanceDAO.updateNormalAttendance(attendanceId, Integer.parseInt(userId), year, month, 1,
								attendance);
						logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId + "/Year : "
								+ year + "/Month : " + month + "/updateDirectAttendance");
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						attendanceDAO.dbDiscon();
					}

					PTODAO ptoDAO = PTODAO.getInstance();
					PTO pto = new PTO();
					String[] array = request.getParameter("array").split(",");
					int index = 0;
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();
						index = -1;
						for (String param : array) {
							index++;
							if (index > 0) {
								ptoDAO.searchPTOId(attendanceId, pto, year, month, Integer.parseInt(param), index);
								ptoDAO.cancelPTO(Integer.parseInt(userId), pto.getPTOId(index), 2);
								logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId
										+ "/Year : " + year + "/Month : " + month + "/Day : " + param + "/deletePTO");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						ptoDAO.dbDiscon();
					}
					LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 取消対象のユーザー情報取得
						staffDAO.searchUser(attendanceId, staff);
						index = -1;
						for (String param : array) {
							index++;
							if (index > 0) {
								// 取得区分が終日か半日かで条件分岐
								if (pto.getDivision(index) == 1) {
									// 対象年が今年であれば年間消費数を修正
									if (pto.getTargetYear(index) == ld.getYear()) {
										staff.setPTO_Consume(1, staff.getPTO_Consume(1) - 1);
									} else {
										staff.setPTO_Consume(1, staff.getPTO_Consume(1));
									}
									staff.setPTO(1, staff.getPTO(1) + 1);
								} else {
									// 対象年が今年であれば年間消費数を修正
									if (pto.getTargetYear(index) == ld.getYear()) {
										staff.setPTO_Consume(1, staff.getPTO_Consume(1) - 0.5);
									} else {
										staff.setPTO_Consume(1, staff.getPTO_Consume(1));
									}
									staff.setPTO(1, staff.getPTO(1) + 0.5);
								}
							}
						}
						// 残り有給数と年間消費数を修正
						staffDAO.updatePTO(1, 1, staff, 2);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						staffDAO.dbDiscon();
					}

					if (session.getAttribute("loginUserAuthority").toString().contentEquals("2")) {
						session.setAttribute("month", month);
					}
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
				}

				// 直接入力の勤怠更新
				if (type.contentEquals("3")) {
					AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
					Attendance attendance = new Attendance();

					attendance.setDivision(1, 10);
					attendance.setStartTime(1, request.getParameter("operatingTime"));
					attendance.setFinishTime(1, request.getParameter("operatingDays"));
					attendance.setBreakTime(1, request.getParameter("absenceDays"));
					attendance.setRemarks(1, request.getParameter("substituteDays"));
					attendance.setMemo(1, request.getParameter("paidDays"));

					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();

						attendanceDAO.updateDirectAttendance(attendanceId, Integer.parseInt(userId), year, month, 1,
								attendance);
						logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId + "/Year : "
								+ year + "/Month : " + month + "/updateAttendance");
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						attendanceDAO.dbDiscon();
					}
					if (session.getAttribute("loginUserAuthority").toString().contentEquals("2")) {
						session.setAttribute("month", month);
					}
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
				}

				// 業務外の勤怠更新
				if (Integer.parseInt(type) > 3) {

					AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();

					Attendance attendance = new Attendance();

					attendance.setDivision(1, 10);
					int day = 0;
					int index = 0;

					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();

						// 登録
						if (Integer.parseInt(type) == 4) {
							day = Integer.parseInt(request.getParameter("inDay"));
							attendance.setStartTime(1, request.getParameter("inStart"));
							attendance.setFinishTime(1, request.getParameter("inFinish"));
							attendance.setBreakTime(1, "00:00");
							attendance.setRemarks(1, request.getParameter("inRemarks"));
							attendance.setWorkHours(1);
							attendanceDAO.updateNonTimeEntryAttendance(attendanceId, Integer.parseInt(userId), year,
									month, day, attendance);
							// 更新
						} else if (Integer.parseInt(type) == 5) {
							attendanceDAO.searchInAttendance(attendanceId, month, year, attendance);
							index = attendance.getNumbersRecords();
							for (int i = 1; i <= index; i++) {
								attendance.setDay(i, request.getParameter("inDay" + i));
								attendance.setStartTime(i, request.getParameter("inStart" + i));
								attendance.setFinishTime(i, request.getParameter("inFinish" + i));
								attendance.setBreakTime(i, "00:00");
								attendance.setRemarks(i, request.getParameter("inRemarks" + i));
								attendance.setWorkHours(i);
								attendanceDAO.updateNonTimeUpdateAttendance(attendanceId, Integer.parseInt(userId),
										year, month, 0, attendance, index);
							}
							// 削除
						} else if (type.contentEquals("6")) {
							day = Integer.parseInt(request.getParameter("inDay"));
							attendanceDAO.updateNonTimeDeleteAttendance(attendanceId, Integer.parseInt(userId), year,
									month, day, attendance);
						}
						logger.log(Level.INFO, "[UpdateAttendance.java]/UpdateUser : " + attendanceId + "/Year : "
								+ year + "/Month : " + month + "/updateInAttendance");
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						attendanceDAO.dbDiscon();
					}
					session.setAttribute("month", month);
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
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[UpdateAttendance.java]" + e.toString(), e.getMessage());
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