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

import model.dao.SkillDAO;
import model.entity.Skill;
import model.properties.Properties;

/**
 * Servlet implementation class UpdateSkill
 * 対象月のユーザーのスキルシートを更新するクラス。
 */
@WebServlet("/UpdateSkill")
public class UpdateSkill extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateSkill() {
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
	 * データベースに接続して対象月のユーザーのスキルシートを更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="skill_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {
			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(UpdateSkill.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = (String)session.getAttribute("loginUserId");
				int skillId = Integer.parseInt(userId);
				int updateId = skillId;
				int month = Integer.parseInt(request.getParameter("month"));
				int year = LocalDateTime.now().getYear();
				if(session.getAttribute("id")!=null) {
					skillId = Integer.parseInt((String)(session.getAttribute("id")));
				}

				SkillDAO skillDAO = SkillDAO.getInstance();
				Skill skill = new Skill();

				//リセットされている場合は新規プロジェクトとし登録
				if(request.getParameter("reset").contentEquals("1")) {
					logger.log(Level.INFO, "[UpdateSkill.java]/UserId : "+skillId+"/Year : "+year+"/Month : "+month+"/Reset : Yes");
					skill.setProjectName(1, request.getParameter("projectName"));
					skill.setBussinessOverview(1, request.getParameter("bussinessOverview"));
					if(!request.getParameter("role").contentEquals("0")) {
						skill.setRole(1, Integer.parseInt(request.getParameter("role")));
					}
					if(!request.getParameter("scale").contentEquals("0")) {
						skill.setScale(1, Integer.parseInt(request.getParameter("scale")));
					}
					skill.setServer_OS(1, request.getParameter("serverOS"));
					skill.setDB(1, request.getParameter("db"));
					skill.setTool(1, request.getParameter("tool"));
					skill.setUseLanguage(1, request.getParameter("useLanguage"));
					skill.setOther(1, request.getParameter("other"));
				}else {
					logger.log(Level.INFO, "[UpdateSkill.java]/UserId : "+skillId+"/Year : "+year+"/Month : "+month+"/Reset : No");
					skill.setProjectName(1, request.getParameter("projectName"));
					if(request.getParameter("bussinessOverviewEdit")!=null) {
						skill.setBussinessOverview(1, request.getParameter("bussinessOverview")+System.lineSeparator()+request.getParameter("bussinessOverviewEdit"));
					}else {
						skill.setBussinessOverview(1, request.getParameter("bussinessOverview"));
					}
					if(!request.getParameter("role").contentEquals("0")) {
						skill.setRole(1, Integer.parseInt(request.getParameter("role")));
					}
					if(!request.getParameter("scale").contentEquals("0")) {
						skill.setScale(1, Integer.parseInt(request.getParameter("scale")));
					}
					if(!request.getParameter("serverOSEdit").toString().isEmpty()) {
						skill.setServer_OS(1, request.getParameter("serverOS")+" "+request.getParameter("serverOSEdit"));
					}else {
						skill.setServer_OS(1, request.getParameter("serverOS"));
					}
					if(!request.getParameter("dbEdit").toString().isEmpty()) {
						skill.setDB(1, request.getParameter("db")+" "+request.getParameter("dbEdit"));
					}else {
						skill.setDB(1, request.getParameter("db"));
					}
					if(!request.getParameter("toolEdit").toString().isEmpty()) {
						skill.setTool(1, request.getParameter("tool")+" "+request.getParameter("toolEdit"));
					}else {
						skill.setTool(1, request.getParameter("tool"));
					}
					if(!request.getParameter("useLanguageEdit").toString().isEmpty()) {
						skill.setUseLanguage(1, request.getParameter("useLanguage")+" "+request.getParameter("useLanguageEdit"));
					}else {
						skill.setUseLanguage(1, request.getParameter("useLanguage"));
					}
					if(!request.getParameter("otherEdit").toString().isEmpty()) {
						skill.setOther(1, request.getParameter("other")+" "+request.getParameter("otherEdit"));
					}else {
						skill.setOther(1, request.getParameter("other"));
					}
				}

				try {
					// 接続
					skillDAO.dbConnect(logger);
					// ステートメント作成
					skillDAO.createSt();

					skillDAO.updateSkill(updateId, skillId, year, month, skill);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[UpdateSkill.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					skillDAO.dbDiscon();
				}

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();
				mapMsg.put("submitFlag", "提出");
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
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[UpdateSkill.java]"+e.toString(), e.getMessage());
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