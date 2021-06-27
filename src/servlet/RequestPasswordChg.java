package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
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

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.SendMailController;
import model.dao.PasswordChgDAO;
import model.dao.StaffDAO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class RequestPasswordChg
 * ユーザーのパスワードを変更を申請するクラス。
 */
@WebServlet("/RequestPasswordChg")
public class RequestPasswordChg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String SUBJECT = "パスワードの再設定";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestPasswordChg() {
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
		if (session.getAttribute("loginUserId") == null) {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");

		// ロガーを取得してログレベルをINFOに設定
		Logger logger = Logger.getLogger(RequestPasswordChg.class.getName());
		logger.setLevel(Level.INFO);

		// ハンドラーを作成してロガーに登録
		Handler handler = new FileHandler(Properties.PASSWORD_CHANGE_PATH, true);
		logger.addHandler(handler);

		// フォーマッターを作成してハンドラーに登録
		Formatter formatter = new SimpleFormatter();
		handler.setFormatter(formatter);

		int errorCheck = 0;

		try {
			int staffId = Integer.parseInt(userId);

			PasswordChgDAO passwordChgDAO = PasswordChgDAO.getInstance();

			String url = "";
			try {
				// 接続
				passwordChgDAO.dbConnect(logger);
				// ステートメント作成
				passwordChgDAO.createSt();

				String str1 = RandomStringUtils.randomAlphabetic(20);

				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				byte[] passwordDigest = digest.digest(str1.getBytes());
				String sha1 = String.format("%040x", new BigInteger(1, passwordDigest));

				LocalDateTime dt1 = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
				LocalDateTime dt2 = dt1.plusDays(1);
				String limitTime = dt2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

				url = Properties.HOME_PATH + "AttendanceRigare/PermitPasswordChg/" + str1 + userId;

				// 有効なパスワード変更キーがあれば削除する
				if (passwordChgDAO.searchPasswordChg(staffId)) {
					passwordChgDAO.deletePasswordChg(staffId);
				}
				// 有効なパスワード変更キーを作成する
				passwordChgDAO.insertPasswordChg(staffId, sha1, limitTime);
				logger.log(Level.INFO, "[RequestPasswordChg.java]/insertPasswordChg/changeUser : " + userId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[RequestPasswordChg.java]" + e.toString(), e.getMessage());
				errorCheck = 1;
			} finally {
				passwordChgDAO.dbDiscon();
			}

			StaffDAO staffDAO = StaffDAO.getInstance();
			Staff staff = new Staff();

			try {
				// 接続
				staffDAO.dbConnect(logger);
				// ステートメント作成
				staffDAO.createSt();
				// 対象ユーザー情報を取得
				staffDAO.searchUser(staffId, staff);
				logger.log(Level.INFO, "[RequestPasswordChg.java]/searchUser/searchUser : " + userId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[PasswordChg.java]" + e.toString(), e.getMessage());
				errorCheck = 1;
			} finally {
				staffDAO.dbDiscon();
			}

			if (errorCheck == 0) {
				String content = "パスワードの再設定を以下のURLから行ってください。\n\n" + url
						+ "\n\nなおURLの有効期限は24時間で、再び再設定の申請をした場合はURLは無効となります";
				String[] address = { staff.getMailAddress(1) };

				SendMailController con = new SendMailController();
				try {
					con.send(SUBJECT, content, address, staff, 1, logger);

					logger.log(Level.INFO, "[RequestPasswordChg.java]/send/Address : " + staff.getMailAddress(1));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[RequestPasswordChg.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} catch (MessagingException e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[RequestPasswordChg.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				}

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();
				mapMsg.put("msg", "登録メールアドレスにが変更メールが送信されました！");
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
			logger.log(Level.WARNING, "[RequestPasswordChg.java]" + e.toString(), e.getMessage());
			errorCheck = 1;
		} finally {
			handler.close();
			if (errorCheck == 1) {
				response.sendRedirect("password_error.jsp");
			}
		}
	}
}
