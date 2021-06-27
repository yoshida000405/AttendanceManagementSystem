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

import  com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.StaffDAO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchUserInfo
 * 対象ユーザーの登録情報を取得するクラス。
 */
@WebServlet("/SearchUserInfo")
public class SearchUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchUserInfo() {
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
	 * データベースに接続して対象ユーザーの登録情報を取得する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="editInfo_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SearchUserInfo.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = request.getParameter("id");
				int staffId = Integer.parseInt(userId);
				int type = Integer.parseInt(request.getParameter("type"));
				if(session.getAttribute("type")!=null) {
					// 登録者情報編集画面以外の遷移の場合、セッション情報を削除
					if(session.getAttribute("type")!="4") {
						session.removeAttribute("id");
						session.removeAttribute("type");
					}else {
						// 管理者権限で対象ユーザーの編集の場合、IDをセット
						if(session.getAttribute("id")!=null) {
							if(type==0) {
								staffId = Integer.parseInt((String)(session.getAttribute("id")));
							}
						}
					}
				}

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

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
					logger.log(Level.WARNING, "[SearchManaged.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				if(staff.getNumbersRecords()>0) {
					//追加
					for(int i=1;i<=staff.getNumbersRecords();i++) {
						mapMsg.put("member"+i, ""+staff.getStaffName(i));
						mapMsg.put("memberId"+i, ""+staff.getStaffId(i));
					}
				}
				mapMsg.put("numbersMembers", ""+staff.getNumbersRecords());
				mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));

				staff = new Staff();
				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 対象ユーザーの情報を取得
					staffDAO.searchUser(staffId, staff);
					logger.log(Level.INFO, "[SearchUserInfo.java]/UserId : "+staffId);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchUserInfo.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				mapMsg.put("firstName", staff.getFirstName(1));
				mapMsg.put("firstNameKana", staff.getFirstNameKana(1));
				mapMsg.put("firstNameEnglish", staff.getFirstNameEnglish(1));
				mapMsg.put("lastName", staff.getLastName(1));
				mapMsg.put("lastNameKana", staff.getLastNameKana(1));
				mapMsg.put("lastNameEnglish", staff.getLastNameEnglish(1));
				mapMsg.put("gender", ""+staff.getGender(1));
				mapMsg.put("group", staff.getGroup(1));
				mapMsg.put("mailAddress", staff.getMailAddress(1));
				mapMsg.put("authority", ""+staff.getAuthority());
				mapMsg.put("hireDate", staff.getHireDate(1));
				mapMsg.put("id", ""+staffId);
				mapMsg.put("editUser", ""+staff.getStaffId(1));
				mapMsg.put("initial", ""+staff.getStaffNameInitial(1));
				mapMsg.put("station", ""+staff.getStation(1));

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
				logger.log(Level.WARNING, "[SearchUserInfo.java]"+e.toString(), e.getMessage());
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