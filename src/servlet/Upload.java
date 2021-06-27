package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.properties.Properties;

/**
 * Servlet implementation class Upload
 * ファイルをアップロードするクラス。
 */
@WebServlet("/Upload")
@MultipartConfig
public class Upload extends HttpServlet {
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
	 * セッション情報に対象のユーザーIDと対象月をセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="upload_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {
			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(Upload.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				String userId = (String)session.getAttribute("id");
				String month = request.getParameter("monthText");
				// 保存先のディレクターを取得
				Part part = request.getPart("file");
				String saveDir = Properties.SAVE_DIR_PATH;
				String fileName = "";
				if(LocalDate.now().getMonthValue()==1&&month.contentEquals("12")) {
					fileName = "/workschedule"+(LocalDate.now().getYear()-1)+month+".pdf";
				}else {
					fileName = "/workschedule"+LocalDate.now().getYear()+month+".pdf";
				}

				Path filePath = Paths.get(saveDir +userId+ fileName);
				// アップしたファイルを取得して、保存
				InputStream in = part.getInputStream();
				Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
				logger.log(Level.INFO, "[Upload.java]/FileName : "+fileName);

				session.setAttribute("month", month);
				RequestDispatcher rd = request.getRequestDispatcher("attendance.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[Upload.java]"+e.toString(), e.getMessage());
				errorCheck=1;
			} finally {
				handler.close();
				if(errorCheck==1||errorCheck==2) {
					response.sendRedirect(jsp);
				}
			}
		}
	}
}
