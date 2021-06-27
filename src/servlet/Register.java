package servlet;

import java.io.File;
import java.io.IOException;
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

import controller.SendMailController;
import model.dao.StaffDAO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class Register
 * ユーザーを新規登録するクラス。
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String SUBJECT = "登録完了のお知らせ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
	 * 直接アクセスに対してユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserId") == null || session.getAttribute("loginUserAuthority") != "2") {
			response.sendRedirect("home.jsp");
		} else {
			response.sendRedirect("register.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * ユーザーを新規登録する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp = "register_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(Register.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				// 管理者権限による編集の対象外なのでセッション情報を削除
				if (session.getAttribute("type") != null) {
					session.removeAttribute("type");
				}

				String password = "password";

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				staff.setFirstName(1, request.getParameter("firstName"));
				staff.setFirstNameKana(1, request.getParameter("firstNameKana"));
				staff.setFirstNameEnglish(1, request.getParameter("firstNameEnglish"));
				staff.setLastName(1, request.getParameter("lastName"));
				staff.setLastNameKana(1, request.getParameter("lastNameKana"));
				staff.setLastNameEnglish(1, request.getParameter("lastNameEnglish"));
				staff.setGender(1, Integer.parseInt(request.getParameter("gender")));
				staff.setHireDate(1, request.getParameter("hireDate"));
				staff.setMailAddress(1, request.getParameter("mailAddress"));
				staff.setGroup(1, request.getParameter("group"));
				staff.setStation(1, request.getParameter("station"));
				staff.setStaffNameInitial(1, staff.getLastNameEnglish(1).substring(0, 1) + "."
						+ staff.getFirstNameEnglish(1).substring(0, 1));
				staff.setPTO(1, Double.parseDouble(request.getParameter("pto")));

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 新規ユーザー登録
					staffDAO.insertUser(staff, password);
					logger.log(Level.INFO, "[Register.java]/insertUser/loginUser : "
							+ session.getAttribute("loginUserId").toString() + "/sattfId : " + staff.getStaffId(1));
					// 個人フォルダ作成
					File newdir = new File("webapps/AttendanceRigare/resources/confidential/" + staff.getStaffId(1));
					newdir.mkdir();
					logger.log(Level.INFO, "[Register.java]/mkdir/loginUser : "
							+ session.getAttribute("loginUserId").toString() + "/sattfId : " + staff.getStaffId(1));
					// 登録完了メール送信
					String content = "管理アプリのアカウント登録が完了しました。\n\nID：" + staff.getStaffId(1)
							+ "\n\n初回パスワード：password\n\nログイン後に初回パスワードの変更をお願いします。";
					String[] address = { staff.getMailAddress(1) };
					SendMailController con = new SendMailController();
					con.send(SUBJECT, content, address, staff, 1, logger);
					logger.log(Level.INFO, "[Register.java]/loginUser : "
							+ session.getAttribute("loginUserId").toString() + "/Address : " + staff.getMailAddress(1));
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[Register.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[Register.java]" + e.toString(), e.getMessage());
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
