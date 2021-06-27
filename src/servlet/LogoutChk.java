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

import model.properties.Properties;

/**
 * Servlet implementation class LogoutChk
 * ユーザーのログアウトをチェックするクラス。
 */
@WebServlet("/LogoutChk")
public class LogoutChk extends HttpServlet {
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
	 * セッション情報からユーザーIDとユーザー権限、対象ユーザーID、対象月を削除する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		// セッション情報が期限切れしていない場合
        if(session.getAttribute("loginUserId")!=null) {
        	// ロガーを取得してログレベルをINFOに設定
            Logger logger = Logger.getLogger(LogoutChk.class.getName());
            logger.setLevel(Level.INFO);

            // ハンドラーを作成してロガーに登録
            Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
            logger.addHandler(handler);

            // フォーマッターを作成してハンドラーに登録
            Formatter formatter =  new SimpleFormatter();
            handler.setFormatter(formatter);

        	logger.log(Level.INFO, "[LogoutChk.java]/"+session.getAttribute("loginUserId").toString()+"/ logout");

			//保持しているユーザID情報を削除
			session.removeAttribute("loginUserId");
			session.removeAttribute("id");
			session.removeAttribute("month");
			session.removeAttribute("loginUserAuthority");

			handler.close();
		}
		response.sendRedirect("login.jsp");
	}
}