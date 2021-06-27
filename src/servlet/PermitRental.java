package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.EquipmentDAO;
import model.dao.EventDAO;
import model.entity.Event;
import model.properties.Properties;

/**
 * Servlet implementation class PermitRental
 * 機材レンタル予約を貸し出し中にするクラス。
 */
@WebServlet("/PermitRental")
public class PermitRental extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PermitRental() {
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
	 * データベースに接続して対象有給申請のフラッグを更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="rental_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(PermitRental.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				String userId = (String)session.getAttribute("loginUserId");
				int updateId = Integer.parseInt(userId);

				EventDAO eventDAO = EventDAO.getInstance();

				Event event = new Event();

				int rentalId = Integer.parseInt(request.getParameter("id"));
				int type = Integer.parseInt(request.getParameter("type"));

				LocalDate ld1 = LocalDate.now(ZoneId.of("Asia/Tokyo"));

				boolean permitEventChkFlag = false;
				try {
					// 接続
					eventDAO.dbConnect(logger);
					// ステートメント作成
					eventDAO.createSt();
					// 対象レンタル情報を取得
					permitEventChkFlag = eventDAO.searchEvent(rentalId, event);
					// 予約申請日をセット
					event.setStart(1, ld1.getYear()+"-"+ld1.getMonthValue()+"-"+ld1.getDayOfMonth()+" 00:00:00");
					// レンタル開始でなく継続の場合
					if(type==2) {
						updateId = event.getStaffId(1);
						// 次の予約があるかを確認
						permitEventChkFlag = eventDAO.searchNextEvent(event.getEquipmentId(1), event);
						logger.log(Level.INFO, "[PermitRental.java]/RentalId : "+rentalId+"/searchNextEvent : "+permitEventChkFlag);

						String finishMonth="";
						String finishDay="";
						LocalDate ld2;
						//予約がない場合は1ヶ月間追加
						if(!permitEventChkFlag) {
							if(Integer.parseInt(request.getParameter("finishMonth"))<10) {
								finishMonth="0"+Integer.parseInt(request.getParameter("finishMonth"));
							}else {
								finishMonth=""+Integer.parseInt(request.getParameter("finishMonth"));
							}
							if(Integer.parseInt(request.getParameter("finishDay"))<10) {
								finishDay="0"+Integer.parseInt(request.getParameter("finishDay"));
							}else {
								finishDay=""+Integer.parseInt(request.getParameter("finishDay"));
							}
							if(ld1.getMonthValue()==12&&Integer.parseInt(request.getParameter("finishMonth"))==1) {
								ld2 = LocalDate.parse((ld1.getYear()+1)+"-"+finishMonth+"-"+finishDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							}else {
								ld2 = LocalDate.parse(ld1.getYear()+"-"+finishMonth+"-"+finishDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							}
							if(ld2.isBefore(ld1)) {
								ld2 = ld1.plusMonths(1);
								ld2 = ld2.minusDays(1);
							}else if(ld1.plusMonths(1).isBefore(ld2)) {
								ld2 = ld1.plusMonths(1);
								ld2 = ld2.minusDays(1);
							}
						}else {
							ld2 = LocalDate.parse(event.getStart(1).substring(0,10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							ld2 = ld2.minusDays(1);
						}

						Period p = Period.between(ld1, ld2);
						// 1週間以内に予約がある場合は申請却下
						if(permitEventChkFlag&&p.getMonths()<1&&p.getDays()<7) {
							mapMsg.put("next", "0");
						}else {
							event.setStart(1, ld1.getYear()+"-"+ld1.getMonthValue()+"-"+ld1.getDayOfMonth()+" 00:00:00");
							event.setFinish(1,  ld2.getYear()+"-"+ld2.getMonthValue()+"-"+ld2.getDayOfMonth()+" 23:59:59");
							eventDAO.permitEvent(updateId, rentalId, event, type);
							mapMsg.put("next", "1");
						}
						logger.log(Level.INFO, "[PermitRental.java]/RentalId : "+rentalId+"next : "+mapMsg.get("next"));
					}else {
						eventDAO.permitEvent(updateId, rentalId, event, type);
					}
					logger.log(Level.INFO, "[PermitRental.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/RentalId : "+rentalId+"/EquipmentId : "+event.getEquipmentId(1));
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[PermitRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					eventDAO.dbDiscon();
				}

				EquipmentDAO equipmentDAO = EquipmentDAO.getInstance();

				// レンタル開始の場合
				if(type==1) {
					try {
						// 接続
						equipmentDAO.dbConnect(logger);
						// ステートメント作成
						equipmentDAO.createSt();
						// 機材の貸し出し状態をレンタル中にする
						equipmentDAO.updateEquipment(updateId, event.getEquipmentId(1), 1);

						logger.log(Level.INFO, "[PermitRental.java]/EquipmentId : "+event.getEquipmentId(1));

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[PermitRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						equipmentDAO.dbDiscon();
					}
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
				logger.log(Level.WARNING, "[CancelRental.java]"+e.toString(), e.getMessage());
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
