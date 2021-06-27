package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import model.dao.StaffDAO;
import model.properties.Properties;

/**
 * Servlet implementation class PasswordChg
 * ユーザーのパスワードを変更するクラス。
 */
@WebServlet("/PasswordChg")
public class PasswordChg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PasswordChg() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			session.setAttribute("key","key");
			response.sendRedirect("password_change.jsp");
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
	 * データベースに接続してユーザーのパスワードを変更する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		// ロガーを取得してログレベルをINFOに設定
		Logger logger = Logger.getLogger(PasswordChg.class.getName());
		logger.setLevel(Level.INFO);

		// ハンドラーを作成してロガーに登録
		Handler handler = new FileHandler(Properties.LOG_PATH+userId+"_webApp.log",true);
		logger.addHandler(handler);

		// フォーマッターを作成してハンドラーに登録
		Formatter formatter =  new SimpleFormatter();
		handler.setFormatter(formatter);

		int errorCheck=0;

		try {

			StaffDAO staffDAO = StaffDAO.getInstance();

			boolean passwordChangeChkFlag = false;
			try {
				// 接続
				staffDAO.dbConnect(logger);
				// ステートメント作成
				staffDAO.createSt();
				// パスワードを変更
				passwordChangeChkFlag = staffDAO.passwordChange(userId, password);

				logger.log(Level.INFO, "[PasswordChg.java]/passwordChange/changeUser : "+userId);

			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[PasswordChg.java]"+e.toString(), e.getMessage());
				errorCheck=1;
			} finally {
				staffDAO.dbDiscon();
			}

			if (!passwordChangeChkFlag) {
				errorCheck=1;
			}else {
				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();
				mapMsg.put("msg", "パスワードが変更されました！");
				//マッパ(JSON <-> Map, List)
				ObjectMapper mapper = new ObjectMapper();
				//json文字列
				String jsonStr = mapper.writeValueAsString(mapMsg);  //list, map
				//ヘッダ設定
				response.setContentType("application/json;charset=UTF-8");   //JSON形式, UTF-8
				//pwオブジェクト
				PrintWriter pw = response.getWriter();
				//出力
				pw.print(jsonStr);
				//クローズ
				pw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "[PasswordChg.java]"+e.toString(), e.getMessage());
			errorCheck=1;
		}finally {
			handler.close();
			if(errorCheck==1) {
				response.sendRedirect("password_error.jsp");
			}
		}
	}
}
