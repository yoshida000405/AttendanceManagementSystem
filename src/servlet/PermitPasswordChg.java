package servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import model.dao.PasswordChgDAO;
import model.properties.Properties;

/**
 * Servlet implementation class PermitPasswordChg
 * ユーザーのパスワードを変更するクラス。
 */
@WebServlet("/PermitPasswordChg/*")
public class PermitPasswordChg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PermitPasswordChg() {
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
		String jsp = "";
		try {
			request.setCharacterEncoding("UTF-8");

			PasswordChgDAO passwordChgDAO = PasswordChgDAO.getInstance();

			boolean passwordChangeChkFlag = false;
			String str = "";
			String userId = "";
			String url = "";
			int staffId = 0;
			try {
				str = new String(request.getRequestURL())
						.replace(Properties.HOME_PATH + "AttendanceRigare/PermitPasswordChg/", "").trim();
				userId = str.substring(20);
				url = str.substring(0, 20);
				staffId = Integer.parseInt(userId);
			} catch (Exception e) {
				jsp = "/AttendanceRigare/password_error.jsp";
			}

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(PermitPasswordChg.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + userId + "_webApp.log", true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				// 接続
				passwordChgDAO.dbConnect(logger);
				// ステートメント作成
				passwordChgDAO.createSt();

				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				byte[] passwordDigest = digest.digest(url.getBytes());
				String sha1 = String.format("%040x", new BigInteger(1, passwordDigest));
				logger.log(Level.INFO, "[PermitPasswordChg.java]/searchPasswordChg/sha1 : " + sha1);

				LocalDateTime dt1 = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
				LocalDateTime dt2 = dt1.plusDays(1);
				String limitTime = dt2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

				logger.log(Level.INFO, "[PermitPasswordChg.java]/searchPasswordChg/limitTime : " + limitTime);

				if (passwordChgDAO.searchPasswordChg(staffId, sha1, limitTime)) {
					passwordChgDAO.deletePasswordChg(staffId);
					passwordChangeChkFlag = true;
					logger.log(Level.INFO, "[PermitPasswordChg.java]/searchPasswordChg/changeUser : " + userId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[PermitPasswordChg.java]" + e.toString(), e.getMessage());
			} finally {
				passwordChgDAO.dbDiscon();
			}

			if (!passwordChangeChkFlag) {
				handler.close();
				jsp = "/AttendanceRigare/password_error.jsp";
			} else {
				logger.log(Level.INFO,
						"[PermitPasswordChg.java]/searchPasswordChg/passwordChangeChkFlag : " + passwordChangeChkFlag);
				handler.close();
				jsp = "/AttendanceRigare/PasswordChg";
			}
		} finally {
			response.sendRedirect(jsp);
		}
	}
}
