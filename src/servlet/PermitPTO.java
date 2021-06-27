package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.SendMailController;
import model.dao.AttendanceDAO;
import model.dao.HolidayDAO;
import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.Holiday;
import model.entity.PTO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class PermitPTO
 * 有給取得申請を承認するクラス。
 */
@WebServlet("/PermitPTO")
public class PermitPTO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String SUBJECT = "有給取得申請承認のお知らせ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PermitPTO() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して対象有給申請のフラッグを更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="pto_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(PermitPTO.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = (String)session.getAttribute("loginUserId");
				int updateId = Integer.parseInt(userId);

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
				Attendance attendance = new Attendance();

				PTODAO ptoDAO = PTODAO.getInstance();
				PTO pto = new PTO();

				int ptoId = Integer.parseInt(request.getParameter("id"));

				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();
					// 承認する有給情報取得
					ptoDAO.searchPTO(ptoId, staff, pto);
					// 有給承認
					ptoDAO.permitPTO(updateId, ptoId);
					logger.log(Level.INFO, "[PermitPTO.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Year : "+pto.getTargetYear(1)+"/Month : "+pto.getTargetMonth(1)+"/Day : "+pto.getTargetDay(1));

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					ptoDAO.dbDiscon();
				}
				if(errorCheck==0) {
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 取消対象のユーザー情報取得
						staffDAO.searchUser(staff.getStaffId(1), staff);
						// 取得区分が終日か半日かで条件分岐
						if(pto.getDivision(1)==1) {
							// 対象年が今年であれば年間消費数を更新
							if(pto.getTargetYear(1)==ld.getYear()) {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1)+1);
							}else {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1));
							}
							staff.setPTO(1, staff.getPTO(1)-1);
						}else {
							// 対象年が今年であれば年間消費数を更新
							if(pto.getTargetYear(1)==ld.getYear()) {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1)+0.5);
							}else {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1));
							}
							staff.setPTO(1, staff.getPTO(1)-0.5);
						}
						// 残り有給数と年間消費数を更新
						staffDAO.updatePTO(1, 1, staff, 2);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						staffDAO.dbDiscon();
					}

					HolidayDAO holidayDAO = HolidayDAO.getInstance();
					Holiday holiday = new Holiday();

					if(pto.getTargetMonth(1)!=6&&pto.getTargetMonth(1)!=10&&pto.getTargetMonth(1)!=12) {
						try {
							// 接続
							holidayDAO.dbConnect(logger);
							// ステートメント作成
							holidayDAO.createSt();
							// 対象月の祝日情報を取得
							holidayDAO.setHoliday(pto.getTargetYear(1), pto.getTargetMonth(1), holiday);

						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchAttendance.java]"+e.toString(), e.getMessage());
							errorCheck=1;
						} finally {
							holidayDAO.dbDiscon();
						}
					}

					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();
						// 取得日の勤務表上を取得
						attendanceDAO.searchAttendance(staff.getStaffId(1), pto.getTargetMonth(1), pto.getTargetYear(1), attendance, holiday);

						switch(pto.getDivision(1)) {
						case 1:
							attendance.setDivision(1, 4);
							break;
						case 2:
							attendance.setDivision(1, 5);
							break;
						case 3:
							attendance.setDivision(1, 6);
							break;
						}
						// 勤務表に有給承認を反映
						attendanceDAO.updatePermitPtoAttendance(staff.getStaffId(1), updateId, pto.getTargetYear(1), pto.getTargetMonth(1), pto.getTargetDay(1),attendance);
						logger.log(Level.INFO, "[PermitPTO.java]/AttendanceChange/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Year : "+pto.getTargetYear(1)+"/Month : "+pto.getTargetMonth(1)+"/Day : "+pto.getTargetDay(1)+"/Division : "+attendance.getDivision(1));


					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						attendanceDAO.dbDiscon();
					}

					String division = "";
					switch(pto.getDivision(1)) {
					case 1:
						division = "終日";
						break;
					case 2:
						division = "午前休";
						break;
					case 3:
						division = "午後休";
						break;
					}
					String content = "有給取得の申請が承認されました。\n\n対象日："+pto.getTargetYear(1)+"/"+pto.getTargetMonth(1)+"/"+pto.getTargetDay(1)+"\n\n区分："+division+"\n\nご確認をお願いします。";
					String[] address = {staff.getMailAddress(1)};
					SendMailController con = new SendMailController();
					try {
						// メール送信
						con.send(SUBJECT, content, address, staff, 1, logger);
						logger.log(Level.INFO, "[PermitPTO.java]/send/address :"+staff.getMailAddress(1));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} catch (MessagingException e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[PermitPTO.java]"+e.toString(), e.getMessage());
				errorCheck=1;
			}finally {
				handler.close();
				if(errorCheck==1||errorCheck==2) {
					response.sendRedirect(jsp);
				}
			}
		}
	}
}
