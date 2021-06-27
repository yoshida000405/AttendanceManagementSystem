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
 * Servlet implementation class LoginChk
 * ユーザーのログインをチェックするクラス。
 */
@WebServlet("/LoginChk")
public class LoginChk extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対してユーザーが既にログインしていたらホーム画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
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
	 * データベースに接続してユーザーのログインをチェックする。<br>
	 * ログインに成功したらセッション情報にユーザーIDとユーザー権限をセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		// ロガーを取得してログレベルをINFOに設定
        Logger logger = Logger.getLogger(LoginChk.class.getName());
        logger.setLevel(Level.INFO);

        // ハンドラーを作成してロガーに登録
        Handler handler = new FileHandler(Properties.LOGIN_LOG_PATH,true);
        logger.addHandler(handler);

        // フォーマッターを作成してハンドラーに登録
        Formatter formatter =  new SimpleFormatter();
        handler.setFormatter(formatter);

		StaffDAO staffDAO = StaffDAO.getInstance();
		Staff staff = new Staff();

		boolean loginUserChkFlag = false;
		try {
			// 接続
			staffDAO.dbConnect(logger);
			// ステートメント作成
			staffDAO.createSt();
			// ID,Passwordが一致すればtrueが返る
			loginUserChkFlag = staffDAO.loginUser(userId, password, staff);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "[LoginChk.java]"+e.toString(), e.getMessage());
		} finally {
			staffDAO.dbDiscon();
		}

		if (loginUserChkFlag) {
			session.setAttribute("loginUserId", userId);
			session.setAttribute("loginUserAuthority", staff.getAuthority());
			logger.log(Level.INFO, "[LoginChk.java]/"+userId+"/ login");
			handler.close();
			response.sendRedirect("home.jsp");
		} else {
			handler.close();
			response.sendRedirect("login_error.jsp");
		}
	}
}
