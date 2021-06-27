package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
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

import  com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.SkillDAO;
import model.dao.StaffDAO;
import model.entity.Skill;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchSkill
 * 対象月のユーザーのスキルシートを取得するクラス。
 */
@WebServlet("/SearchSkill")
public class SearchSkill extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchSkill() {
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
	 * データベースに接続して対象月のユーザーのスキルシートを取得する。
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
			Logger logger = Logger.getLogger(SearchSkill.class.getName());
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
				int month = Integer.parseInt(request.getParameter("month"));
				if(session.getAttribute("type")!=null) {
					// スキルシート入力画面以外の遷移の場合、セッション情報を削除
					if(session.getAttribute("type")!="3") {
						session.removeAttribute("id");
						session.removeAttribute("month");
						session.removeAttribute("type");
					}else {
						// 管理者権限で対象ユーザーの編集の場合、IDと対象月をセット
						if(session.getAttribute("month")!=null&&session.getAttribute("id")!=null) {
							month = Integer.parseInt((String)(session.getAttribute("month")));
							skillId = Integer.parseInt((String)(session.getAttribute("id")));
							session.removeAttribute("month");
						}else if(session.getAttribute("id")!=null) {
							skillId = Integer.parseInt((String)(session.getAttribute("id")));
						}
					}
				}

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 対象ユーザーの情報を取得
					staffDAO.searchUser(skillId, staff);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchSkill.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				SkillDAO skillDAO = SkillDAO.getInstance();
				Skill skill = new Skill();

				try {
					// 12月時点で1月を対象とした場合は翌年になる
					// 1月時点で12月を対象とした場合は昨年になる
					LocalDate date = LocalDate.now(ZoneId.of("Asia/Tokyo"));
					int year = date.getYear();
					if(date.getMonthValue()==12&&month==1) {
						year++;
					}else if(date.getMonthValue()==1&&month==12){
						year--;
					}

					// 接続
					skillDAO.dbConnect(logger);
					// ステートメント作成
					skillDAO.createSt();
					// 対象月のスキルシート情報を取得(レコードがない場合は作成)(登録用に作成)
					skillDAO.searchSkill(skillId, month,year,skill);

					month--;
					if(month==0) {
						month=12;
						year--;
					}
					// 対象月の前の月のスキルシート情報を取得(レコードがない場合は作成)(表示用)
					skillDAO.searchSkill(skillId, month,year,skill);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchSkill.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					skillDAO.dbDiscon();
				}

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				mapMsg.put("projectName", skill.getProjectName(1));
				mapMsg.put("bussinessOverview", skill.getBussinessOverview(1));
				mapMsg.put("role", ""+skill.getRole(1));
				mapMsg.put("scale", ""+skill.getScale(1));
				mapMsg.put("serverOS", skill.getServer_OS(1));
				mapMsg.put("db", skill.getDB(1));
				mapMsg.put("tool", skill.getTool(1));
				mapMsg.put("useLanguage", skill.getUseLanguage(1));
				mapMsg.put("other", skill.getOther(1));
				mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));
				mapMsg.put("editUser", staff.getStaffName(1));
				mapMsg.put("month", ""+month);

				if(staff.getSkillFlag(1)==1) {
					mapMsg.put("submitFlag", "提出");
				}else {
					mapMsg.put("submitFlag", "未提出");
				}
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
				logger.log(Level.WARNING, "[SearchSkill.java]"+e.toString(), e.getMessage());
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