package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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

import controller.CSVController;
import model.dao.SkillDAO;
import model.dao.StaffDAO;
import model.entity.Skill;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class OutputCSV
 * CSVを出力するクラス。
 */
@WebServlet("/OutputCSV")
public class OutputCSV extends HttpServlet {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserId") == null) {
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
	 * セッション情報から対象ユーザーIDと対象月を削除する。
	 * フラッグの判定により自動処理の設定を行う。
	 * スキルシート内容をまとめCSV出力する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String jsp = "error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(OutputCSV.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				int month = Integer.parseInt(request.getParameter("month"));
				int year = LocalDateTime.now().getYear();
				int type = Integer.parseInt(request.getParameter("type"));

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 全ユーザーの情報を取得
					staffDAO.searchAllUser(staff);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[OutputCSV.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}

				if (type == 3) {
					SkillDAO skillDAO = SkillDAO.getInstance();
					Skill skill = new Skill();

					try {
						// 接続
						skillDAO.dbConnect(logger);
						// ステートメント作成
						skillDAO.createSt();
						// 対象月の全てのスキルシート内容を取得
						skillDAO.searchAllSkill(month, year, skill, staff);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[OutputCSV.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						skillDAO.dbDiscon();
					}

					// 個人フォルダにデータを出力
					CSVController csv = new CSVController();
					csv.output(year, month, skill, staff, logger);
					logger.log(Level.INFO, "[OutputCSV.java]/output/loginUser : "
							+ session.getAttribute("loginUserId").toString() + "fileType : skill");
					//JSONマップ
					Map<String, String> mapMsg = new HashMap<String, String>();
					mapMsg.put("url", Properties.SAVE_DIR_URL_PATH + "rigare_skill_" + year + "." + month + ".csv");
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
				logger.log(Level.WARNING, "[OutputCSV.java]" + e.toString(), e.getMessage());
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