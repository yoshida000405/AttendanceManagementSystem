package servlet;

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

import model.dao.StaffDAO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class UpdateUserInfo
 * 対象月ユーザーの登録情報を更新するクラス。
 */
@WebServlet("/UpdateUserInfo")
public class UpdateUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserInfo() {
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
	 * データベースに接続して対象ユーザーの登録情報を更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp = "editInfo_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {
			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(UpdateUserInfo.class.getName());
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
				int staffId = Integer.parseInt(request.getParameter("id"));
				if (session.getAttribute("id") != null) {
					staffId = Integer.parseInt((String) (session.getAttribute("id")));
				}
				boolean type = request.getParameter("type") == null ? false
						: request.getParameter("type").equals("delete") ? true : false;
				String password = request.getParameter("password");

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();

					if (type) {
						if (staffDAO.loginUser(userId, password, staff)) {
							staffDAO.deleteUser(staffId, Integer.parseInt(userId), staff);
							logger.log(Level.INFO, "[UpdateUserInfo.java]/Delete/UserId : " + staffId);
						}
					} else {
						//staffインスタンスに更新するデータをセット
						staff.setFirstName(1, request.getParameter("firstName"));
						staff.setFirstNameKana(1, request.getParameter("firstNameKana"));
						staff.setFirstNameEnglish(1, request.getParameter("firstNameEnglish"));
						staff.setLastName(1, request.getParameter("lastName"));
						staff.setLastNameKana(1, request.getParameter("lastNameKana"));
						staff.setLastNameEnglish(1, request.getParameter("lastNameEnglish"));
						staff.setGender(1, Integer.parseInt(request.getParameter("gender")));
						staff.setGroup(1, request.getParameter("group"));
						staff.setHireDate(1, request.getParameter("hireDate"));
						staff.setMailAddress(1, request.getParameter("mailAddress"));
						staff.setStation(1, request.getParameter("station"));
						staff.setAuthority(Integer.parseInt(request.getParameter("authority")));
						staff.setStaffNameInitial(1, staff.getLastNameEnglish(1).substring(0, 1) + "."
								+ staff.getFirstNameEnglish(1).substring(0, 1));

						staffDAO.updateUserInfo(staffId, Integer.parseInt(userId), staff);

						logger.log(Level.INFO, "[UpdateUserInfo.java]/Update/UserId : " + staffId);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[UpdateUserInfo.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[UpdateUserInfo.java]" + e.toString(), e.getMessage());
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