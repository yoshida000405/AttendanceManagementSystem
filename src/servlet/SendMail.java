package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import model.dao.StaffDAO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SendMail
 * 対象ユーザーにメールを送信するクラス。
 */
@WebServlet("/SendMail")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMail() {
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
	 * 対象ユーザーにメールを送信する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="mail_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SendMail.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				// 管理者権限による編集の対象外なのでセッション情報を削除
				if(session.getAttribute("type")!=null) {
					session.removeAttribute("type");
				}

				String subject = request.getParameter("subject");
				String content = request.getParameter("content");
				int to = Integer.parseInt(request.getParameter("member"));

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();
				String[] address = new String[100];
				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 送信者のメールアドレス情報を取得
					if(to>6) {
						staffDAO.searchUser((to-6), staff);
					}else {
						staffDAO.searchGroup(to, staff);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SendMail.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				for(int i=0; i<staff.getNumbersRecords(); i++) {
					address[i] = staff.getMailAddress(i+1);
				}
				SendMailController con = new SendMailController();
				try {
					con.send(subject, content, address, staff, 0, logger);
					logger.log(Level.INFO, "[SendMail.java]/address : "+address);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SendMail.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} catch (MessagingException e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SendMail.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[SendMail.java]"+e.toString(), e.getMessage());
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