package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
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

import model.dao.AttendanceDAO;
import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.PTO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class CancelPTO
 * 有給取得申請を取り消すクラス。
 */
@WebServlet("/CancelPTO")
public class CancelPTO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelPTO() {
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
	 * データベースに接続して対象有給申請のフラッグを更新またはレコードを削除する。
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

			String userId = (String)session.getAttribute("loginUserId");
			int updateId = Integer.parseInt(userId);

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(CancelPTO.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
				Attendance attendance = new Attendance();

				PTODAO ptoDAO = PTODAO.getInstance();
				PTO pto = new PTO();

				int ptoId = Integer.parseInt(request.getParameter("id"));
				int type = Integer.parseInt(request.getParameter("type"));

				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();
					// 取り消す有給情報取得
					ptoDAO.searchPTO(ptoId, staff, pto);
					// 有給取消
					ptoDAO.cancelPTO(updateId, ptoId, type);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[CancelPTO.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					ptoDAO.dbDiscon();
				}
				logger.log(Level.INFO, "[CancelPTO.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Type : "+type+"/Year : "+pto.getTargetYear(1)+"/Month : "+pto.getTargetMonth(1)+"/Day : "+pto.getTargetDay(1));
				//承認済みの有給取消の場合
				if(type==2&&errorCheck==0) {
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 取消対象のユーザー情報取得
						staffDAO.searchUser(staff.getStaffId(1), staff);
						// 取得区分が終日か半日かで条件分岐
						if(pto.getDivision(1)==1) {
							// 対象年が今年であれば年間消費数を修正
							if(pto.getTargetYear(1)==ld.getYear()) {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1)-1);
							}else {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1));
							}
							staff.setPTO(1, staff.getPTO(1)+1);
						}else {
							// 対象年が今年であれば年間消費数を修正
							if(pto.getTargetYear(1)==ld.getYear()) {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1)-0.5);
							}else {
								staff.setPTO_Consume(1, staff.getPTO_Consume(1));
							}
							staff.setPTO(1, staff.getPTO(1)+0.5);
						}
						// 残り有給数と年間消費数を修正
						staffDAO.updatePTO(1, 1, staff, 2);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[CancelPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						staffDAO.dbDiscon();
					}
					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();
						// 勤務表に有給取消を反映
						attendanceDAO.updateCancelPtoAttendance(staff.getStaffId(1), updateId, pto.getTargetYear(1), pto.getTargetMonth(1), pto.getTargetDay(1),attendance);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[CancelPTO.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						attendanceDAO.dbDiscon();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[CancelPTO.java]"+e.toString(), e.getMessage());
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
