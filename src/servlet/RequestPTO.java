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

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.PTODAO;
import model.entity.PTO;
import model.properties.Properties;

/**
 * Servlet implementation class RequestPTO
 * 有給取得申請を行うクラス。
 */
@WebServlet("/RequestPTO")
public class RequestPTO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestPTO() {
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
	 * データベースに接続して対象有給申請のレコードを作成する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="pto_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(RequestPTO.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			//JSONマップ
			Map<String, String> mapMsg = new HashMap<String, String>();

			try {

				String userId = (String)session.getAttribute("loginUserId");
				int updateId = Integer.parseInt(userId);

				if(request.getParameter("id")!=null) {
					updateId = Integer.parseInt(request.getParameter("id"));
				}

				PTODAO ptoDAO = PTODAO.getInstance();
				PTO pto = new PTO();

				// 12月時点で1月を対象とした申請は翌年になる
				// 1月時点で12月を対象とした申請は昨年になる
				LocalDate date = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				pto.setRequestYear(1, date.getYear());
				pto.setRequestMonth(1, date.getMonthValue());
				pto.setRequestDay(1, date.getDayOfMonth());
				pto.setReason(1, request.getParameter("reason"));
				pto.setTargetMonth(1, Integer.parseInt(request.getParameter("month")));
				pto.setTargetDay(1, Integer.parseInt(request.getParameter("day")));
				pto.setDivision(1, Integer.parseInt(request.getParameter("division")));
				if(date.getMonthValue()==12&&pto.getTargetMonth(1)==1) {
					pto.setTargetYear(1, date.getYear()+1);
				}else if(date.getMonthValue()==1&&pto.getTargetMonth(1)==12){
					pto.setTargetYear(1, date.getYear()-1);
				}else {
					pto.setTargetYear(1, date.getYear());
				}

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();
					//同日の申請済み有給を検索
					if(ptoDAO.searchPTOAlready(updateId, pto)) {
						mapMsg.put("check", "error");
						logger.log(Level.INFO, "[RequestPTO.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Year : "+pto.getTargetYear(1)+"/Month : "+pto.getTargetMonth(1)+"/Day : "+pto.getTargetDay(1)+"/Already");
					}else {
						// 有給申請
						ptoDAO.insertPTO(updateId, pto);
						logger.log(Level.INFO, "[RequestPTO.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Year : "+pto.getTargetYear(1)+"/Month : "+pto.getTargetMonth(1)+"/Day : "+pto.getTargetDay(1));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[RequestPTO.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					ptoDAO.dbDiscon();
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
				logger.log(Level.WARNING, "[RequestPTO.java]"+e.toString(), e.getMessage());
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
