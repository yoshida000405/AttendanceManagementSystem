package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import controller.PTOController;
import model.dao.AttendanceDAO;
import model.dao.BoardDAO;
import model.dao.HolidayDAO;
import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.dao.TimerDAO;
import model.entity.Attendance;
import model.entity.Board;
import model.entity.Holiday;
import model.entity.PTO;
import model.entity.Staff;
import model.entity.Timer;
import model.properties.Properties;

/**
 * Servlet implementation class Home
 * ホーム遷移時のイベント設定するクラス。
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対してユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("home.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * セッション情報から対象ユーザーIDと対象月を削除する。
	 * フラッグの判定により自動処理の設定を行う。
	 * 勤怠及び請求書のフラッグ判定によりユーザーへの催促通知を設定する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String jsp = "home_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			String userId = (String) session.getAttribute("loginUserId");
			int staffId = Integer.parseInt(userId);

			//保持しているユーザID情報を削除
			if (session.getAttribute("id") != null) {
				session.removeAttribute("id");
			}
			if (session.getAttribute("month") != null) {
				session.removeAttribute("month");
			}

			int month = LocalDateTime.now().getMonthValue();
			int year = LocalDateTime.now().getYear();

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(Home.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// ログインユーザーの情報を取得
					staffDAO.searchUser(staffId, staff);
					mapMsg.put("name", staff.getStaffName(1));
					// ログインユーザーのパスワードフラッグが0の場合、セッション情報を取り消す
					if (staff.getFlag(1) == 0) {
						session.removeAttribute("loginUserId");
						session.removeAttribute("loginUserAuthority");
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}

				TimerDAO timerDAO = TimerDAO.getInstance();
				Timer timer = new Timer();

				try {
					// 接続
					timerDAO.dbConnect(logger);
					// ステートメント作成
					timerDAO.createSt();
					// タイマーのフラッグが0であればタイマーを設定してフラッグを１に変更
					timerDAO.checkTimer(timer, 1, logger);
					timerDAO.checkTimer(timer, 2, logger);
					timerDAO.checkTimer(timer, 3, logger);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					timerDAO.dbDiscon();
				}

				HolidayDAO holidayDAO = HolidayDAO.getInstance();
				Holiday holiday = new Holiday();

				if (month != 6 && month != 10 && month != 12) {
					try {
						// 接続
						holidayDAO.dbConnect(logger);
						// ステートメント作成
						holidayDAO.createSt();
						// 当月の祝日情報を取得
						holidayDAO.setHoliday(year, month, holiday);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
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
					// ログインユーザーの当月の稼働時間を取得
					attendanceDAO.searchAttendance(staffId, month, year, attendance, holiday);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					attendanceDAO.dbDiscon();
				}

				if (attendance.getDivision(2) != 6) {
					mapMsg.put("operatingTime", "" + attendance.getOperatingTime());
				} else {
					mapMsg.put("operatingTime", "" + attendance.getStartTime(2));
				}

				BoardDAO boardDAO = BoardDAO.getInstance();
				Board board = new Board();

				try {
					// 接続
					boardDAO.dbConnect(logger);
					// ステートメント作成
					boardDAO.createSt();
					// 最新の掲示板投稿の情報を取得
					boardDAO.searchBoard(board);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					boardDAO.dbDiscon();
				}

				PTODAO ptoDAO = PTODAO.getInstance();
				PTO pto = new PTO();

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();
					// 申請された有給の未承認数を取得
					ptoDAO.searchAllPTO(pto, year);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					ptoDAO.dbDiscon();
				}

				int number = 0;
				for (int i = 1; i <= pto.getNumbersRecords(); i++) {
					if (pto.getFlag(i) == 0) {
						number++;
					}
				}
				mapMsg.put("ptoNumber", "" + number);

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();
					// ログインユーザーの有給DBの最終更新日取得
					ptoDAO.searchPTO(staffId, year, pto);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					ptoDAO.dbDiscon();
				}
				// ログインユーザーの各種DBの最終更新日から何秒、何時間、何日前か計算
				if (pto.getLastUpdate(1) != null) {
					LocalDateTime ldt1 = LocalDateTime.parse(pto.getLastUpdate(1),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					LocalDate ld1 = LocalDate.parse(pto.getLastUpdate(1).substring(0, 10),
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					Duration d1 = Duration.between(ldt1, LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
					if (d1.getSeconds() < 60) {
						mapMsg.put("ptoUPD", "" + d1.getSeconds());
						mapMsg.put("ptoMsg", "seconds");
					} else if (d1.getSeconds() < 3600) {
						mapMsg.put("ptoUPD", "" + (d1.getSeconds() / 60));
						mapMsg.put("ptoMsg", "minutes");
					} else if (d1.getSeconds() < 3600 * 24) {
						mapMsg.put("ptoUPD", "" + (d1.getSeconds() / 3600));
						mapMsg.put("ptoMsg", "hours");
					} else {
						Period p1 = Period.between(ld1, LocalDate.now(ZoneId.of("Asia/Tokyo")));
						mapMsg.put("ptoUPD", "" + p1.getDays());
						mapMsg.put("ptoMsg", "days");
					}
				}
				if (attendance.getLastUpdate(1) != null) {
					LocalDateTime ldt2 = LocalDateTime.parse(attendance.getLastUpdate(1),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					LocalDate ld2 = LocalDate.parse(attendance.getLastUpdate(1).substring(0, 10),
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					Duration d2 = Duration.between(ldt2, LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
					if (d2.getSeconds() < 60) {
						mapMsg.put("attendanceUPD", "" + d2.getSeconds());
						mapMsg.put("attendanceMsg", "seconds");
					} else if (d2.getSeconds() < 3600) {
						mapMsg.put("attendanceUPD", "" + (d2.getSeconds() / 60));
						mapMsg.put("attendanceMsg", "minutes");
					} else if (d2.getSeconds() < 3600 * 24) {
						mapMsg.put("attendanceUPD", "" + (d2.getSeconds() / 3600));
						mapMsg.put("attendanceMsg", "hours");
					} else {
						Period p2 = Period.between(ld2, LocalDate.now(ZoneId.of("Asia/Tokyo")));
						mapMsg.put("attendanceUPD", "" + p2.getDays());
						mapMsg.put("attendanceMsg", "days");

					}
				}
				if (board.getUpdDate(1) != null) {
					LocalDateTime ldt3 = LocalDateTime.parse(board.getUpdDate(1),
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					LocalDate ld3 = LocalDate.parse(board.getUpdDate(1).substring(0, 10),
							DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					Duration d3 = Duration.between(ldt3, LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
					if (d3.getSeconds() < 60) {
						mapMsg.put("boardUPD", "" + d3.getSeconds());
						mapMsg.put("boardMsg", "seconds");
					} else if (d3.getSeconds() < 3600) {
						mapMsg.put("boardUPD", "" + (d3.getSeconds() / 60));
						mapMsg.put("boardMsg", "minutes");
					} else if (d3.getSeconds() < 3600 * 24) {
						mapMsg.put("boardUPD", "" + (d3.getSeconds() / 3600));
						mapMsg.put("boardMsg", "hours");
					} else {
						Period p3 = Period.between(ld3, LocalDate.now(ZoneId.of("Asia/Tokyo")));
						mapMsg.put("boardUPD", "" + p3.getDays());
						mapMsg.put("boardMsg", "days");

					}
				}

				mapMsg.put("authority", "" + session.getAttribute("loginUserAuthority"));
				mapMsg.put("flag", "" + staff.getFlag(1));
				mapMsg.put("attendanceFlag", "" + staff.getAttendanceFlag(1));
				mapMsg.put("demandFlag", "" + staff.getDemandFlag(1));
				mapMsg.put("skillFlag", "" + staff.getSkillFlag(1));
				mapMsg.put("boardSubject", "" + board.getSubject(1));
				double[] result = PTOController.info(staff, 1, logger);
				mapMsg.put("remainingTime", "" + (int) (result[0]));
				mapMsg.put("nowPTO", "" + result[2]);
				mapMsg.put("consumePTO", "" + result[3]);
				mapMsg.put("lostPTO", "" + result[1]);

				// ホーム画面のメッセージの選択
				String msg = "";
				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				String date = "";
				if (ld.getMonthValue() < 10) {
					if (ld.getDayOfMonth() < 10) {
						date = "0" + ld.getMonthValue() + "0" + ld.getDayOfMonth();
					} else {
						date = "0" + ld.getMonthValue() + ld.getDayOfMonth();
					}
				} else {
					if (ld.getDayOfMonth() < 10) {
						date = "" + ld.getMonthValue() + "0" + ld.getDayOfMonth();
					} else {
						date = "" + ld.getMonthValue() + ld.getDayOfMonth();
					}
				}

				switch (ld.getMonthValue()) {
				case 1:
					msg = "";
					break;
				case 2:
					msg = "";
					break;
				case 3:
					msg = "";
					break;
				case 4:
					msg = "";
					break;
				case 5:
					msg = "";
					break;
				case 6:
					msg = "";
					break;
				case 7:
					msg = "";
					break;
				case 8:
					msg = "";
					break;
				case 9:
					msg = "";
					break;
				case 10:
					msg = "";
					break;
				case 11:
					msg = "朝晩の冷え込みが厳しくなってきましたね、体調管理にお気を付けて！";
					break;
				case 12:
					msg = "今年も残すところわずかとなりましたね、年末までもう一踏ん張りです！";
					break;
				}

				switch (date) {
				case "0101":
					msg = "明けましておめでとうございます。本年もどうぞよろしくお願い致します！";
					break;
				case "0214":
					msg = "";
					break;
				case "0314":
					msg = "";
					break;
				case "0707":
					msg = "";
					break;
				case "1031":
					msg = "";
					break;
				case "1225":
					msg = "";
					break;
				case "1231":
					msg = "今年も一年お疲れ様でした。くれぐれもご自愛の上、おすこやかに新年をお迎えください";
					break;
				}
				mapMsg.put("welcome", msg);
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
				logger.log(Level.WARNING, "[Home.java]" + e.toString(), e.getMessage());
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