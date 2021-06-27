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

import controller.DateTimeController;
import model.dao.DemandDAO;
import model.dao.StaffDAO;
import model.entity.Demand;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchDemand
 * 対象月のユーザーの請求書を取得するクラス。
 */
@WebServlet("/SearchDemand")
public class SearchDemand extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchDemand() {
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
	 * データベースに接続して対象月のユーザーの請求書を取得する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="demand_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SearchDemand.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = (String)session.getAttribute("loginUserId");
				int demandId = Integer.parseInt(userId);
				int month = Integer.parseInt(request.getParameter("month"));
				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				// 管理者権限の場合、3日まで前の月の勤務表を表示
				// 一般権限の場合、2日まで前の月の勤務表を表示
				if(month==13) {
					if(session.getAttribute("loginUserAuthority").toString().contentEquals("2")) {
						if(ld.getDayOfMonth()<4) {
							month = ld.minusMonths(1).getMonthValue();
						}else {
							month = ld.getMonthValue();
						}
					}else {
						if(ld.getDayOfMonth()<3) {
							month = ld.minusMonths(1).getMonthValue();
						}else {
							month = ld.getMonthValue();
						}
					}
				}

				if(session.getAttribute("type")!=null) {
					// 請求書入力画面以外の遷移の場合、セッション情報を削除
					if(session.getAttribute("type")!="2") {
						session.removeAttribute("id");
						session.removeAttribute("month");
						session.removeAttribute("type");
					}else {
						// 管理者権限で対象ユーザーの編集の場合、IDと対象月をセット
						if(session.getAttribute("month")!=null&&session.getAttribute("id")!=null) {
							month = Integer.parseInt((String)(session.getAttribute("month")));
							demandId = Integer.parseInt((String)(session.getAttribute("id")));
							session.removeAttribute("month");
						}else if(session.getAttribute("id")!=null) {
							demandId = Integer.parseInt((String)(session.getAttribute("id")));
						}
					}
				}

				// 1月時点で12月を対象とした場合は昨年になる
				int year = ld.getYear();
				if(ld.getMonthValue()==1&&month==12){
					year--;
				}

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 対象ユーザーの情報を取得
					staffDAO.searchUser(demandId, staff);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchDemand.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				DemandDAO demandDAO = DemandDAO.getInstance();
				Demand demand = new Demand();

				try {
					// 接続
					demandDAO.dbConnect(logger);
					// ステートメント作成
					demandDAO.createSt();
					// 対象月の請求書を取得
					demandDAO.searchDemand(demandId, month,year,demand);
					logger.log(Level.INFO, "[SearchDemand.java]/UserID : "+demandId+"/Year : "+year+"/Month : "+month);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchDemand.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					demandDAO.dbDiscon();
				}
				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();
				//取得した月の日数
				int days=DateTimeController.getMonthDays(year, month);
				if(demand.getNumbersRecords()>0) {
					//追加
					for(int i=1;i<=demand.getNumbersRecords();i++) {
						mapMsg.put("targetDay"+i, ""+demand.getTargetDay(i));
						mapMsg.put("money"+i, ""+demand.getAplicationAmount(i));
						mapMsg.put("division"+i, ""+demand.getDivision(i));
						mapMsg.put("to"+i, demand.getTo(i));
						mapMsg.put("description"+i, demand.getDescription(i));
						mapMsg.put("remarks"+i, demand.getRemarks(i));
						mapMsg.put("flag"+i, ""+demand.getFlag(i));
						mapMsg.put("demandId"+i, ""+demand.getRecordId(i));
						mapMsg.put("multi"+i, ""+demand.getMulti(i));
						mapMsg.put("recipt"+i, ""+demand.getRecipt(i));
					}
				}
				if(days!=29) {
					mapMsg.put("month", ""+month);
				}else {
					mapMsg.put("month", "22");
				}
				mapMsg.put("editUser", staff.getStaffName(1));
				mapMsg.put("sum1", ""+demand.getTransportation());
				mapMsg.put("sum2", ""+demand.getExpendables());
				mapMsg.put("sum3", ""+demand.getMeeting());
				mapMsg.put("sum4", ""+demand.getCommunications());
				mapMsg.put("sum5", ""+demand.getCommission());
				mapMsg.put("sum6", ""+demand.getSocial());
				mapMsg.put("sum7", ""+demand.getOther());
				mapMsg.put("sum8", ""+demand.getSum());
				mapMsg.put("numbersAplication", ""+demand.getNumbersRecords());
				mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));
				mapMsg.put("update", demand.getLastUpdate());
				mapMsg.put("memo", demand.getMemo(1));

				if(staff.getDemandFlag(1)==1) {
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
				logger.log(Level.WARNING, "[SearchDemand.java]"+e.toString(), e.getMessage());
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
