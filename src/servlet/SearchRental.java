package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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

import  com.fasterxml.jackson.databind.ObjectMapper;

import model.dao.EquipmentDAO;
import model.dao.EventDAO;
import model.dao.StaffDAO;
import model.entity.Equipment;
import model.entity.Event;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchRental
 * 機材の現在状況を取得するクラス。
 */
@WebServlet("/SearchRental")
public class SearchRental extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRental() {
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
	 * データベースに接続して対象月のユーザーの有給取得申請状況を取得する。
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
			Logger logger = Logger.getLogger(SearchRental.class.getName());
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

				String userId = (String)session.getAttribute("loginUserId");
				int staffId = Integer.parseInt(userId);

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				if(request.getParameter("type").contentEquals("1")) {
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();

						staffDAO.searchAllUser(staff);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						staffDAO.dbDiscon();
					}
				}

				EquipmentDAO equipmentDAO = EquipmentDAO.getInstance();
				Equipment equipment = new Equipment();

				try {
					// 接続
					equipmentDAO.dbConnect(logger);
					// ステートメント作成
					equipmentDAO.createSt();

					equipmentDAO.searchAllEquipment(equipment);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					equipmentDAO.dbDiscon();
				}

				EventDAO eventDAO = EventDAO.getInstance();
				Event event = new Event();
				if(request.getParameter("type").contentEquals("1")) {
					try {
						// 接続
						eventDAO.dbConnect(logger);
						// ステートメント作成
						eventDAO.createSt();

						eventDAO.searchAllEvent(event);
						for(int i=1;i<=event.getNumbersRecords();i++) {
							mapMsg.put("rentalId"+i, ""+event.getEventId(i));
							mapMsg.put("staffName"+i, staff.getStaffNameID(event.getStaffId(i)));
							mapMsg.put("staffId"+i, ""+event.getStaffId(i));
							mapMsg.put("start"+i, event.getStart(i));
							mapMsg.put("finish"+i, event.getFinish(i));
							mapMsg.put("rentalFlag"+i, ""+event.getFlag(i));
							mapMsg.put("rentalEquipmentId"+i, ""+event.getEquipmentId(i));
						}
						for(int i=1;i<=equipment.getNumbersRecords();i++) {
							mapMsg.put("equipmentId"+i, ""+equipment.getEquipmentId(i));
							mapMsg.put("equipment"+i, equipment.getName(i));
							mapMsg.put("flag"+i, ""+equipment.getFlag(i));
							if(equipment.getFlag(i)==1) {
								mapMsg.put("playStaff"+i, staff.getStaffNameID(equipment.getStaffId(i)));
							}
							if(equipment.getDivision(i)==1||equipment.getDivision(i)==3) {
								mapMsg.put("cpu"+i, equipment.getCPU(i));
								mapMsg.put("memory"+i, equipment.getMemory(i));
								mapMsg.put("volume"+i, equipment.getVolume(i));
							}
							mapMsg.put("division"+i, ""+equipment.getDivision(i));
							mapMsg.put("purchase"+i, equipment.getPurchase(i));
							mapMsg.put("reservationNumber"+i, ""+event.getWait(equipment.getEquipmentId(i)));
						}
						mapMsg.put("numbersRental", ""+event.getNumbersRecords());

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						eventDAO.dbDiscon();
					}
					logger.log(Level.INFO, "[SearchRental.java]/Type : 1");
				}else {
					for(int i=1;i<=equipment.getNumbersRecords();i++) {
						mapMsg.put("equipmentId"+i, ""+equipment.getEquipmentId(i));
					}
					int index=0;
					try {
						// 接続
						eventDAO.dbConnect(logger);
						// ステートメント作成
						eventDAO.createSt();
						// 対象ユーザーの現在の予約情報を取得
						eventDAO.searchUserEvent(staffId, event);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						eventDAO.dbDiscon();
					}

					for(int i=1;i<=event.getNumbersRecords();i++) {
						index++;
						mapMsg.put("notEquipmentId"+index, ""+event.getEquipmentId(i));
					}
					String month;
					String day;
					if(Integer.parseInt(request.getParameter("month"))<10){
						month = "0"+request.getParameter("month");
					}else {
						month = ""+request.getParameter("month");
					}
					if(Integer.parseInt(request.getParameter("day"))<10){
						day = "0"+request.getParameter("day");
					}else {
						day = ""+request.getParameter("day");
					}
					String target = request.getParameter("year")+"-"+month+"-"+day+" 00:00:00";

					try {
						// 接続
						eventDAO.dbConnect(logger);
						// ステートメント作成
						eventDAO.createSt();
						// 予約不可能な機材情報を取得
						eventDAO.searchNotEvent(target, event);
						for(int i=1;i<=event.getNumbersRecords();i++) {
							index++;
							mapMsg.put("notEquipmentId"+index, ""+event.getEquipmentId(i));
						}
						int id=1;
						if(request.getParameter("type").contentEquals("2")) {
							for(int j=1;j<=index;j++) {
								for(int i=1;i<=equipment.getNumbersRecords();i++) {
									if(!mapMsg.get("notEquipmentId"+j).contentEquals(""+equipment.getEquipmentId(i))) {
										id=equipment.getEquipmentId(i);
									}
								}
							}
							logger.log(Level.INFO, "[SearchRental.java]/Type : 2/Target : "+target);
						}else {
							id=Integer.parseInt(request.getParameter("id"));
							logger.log(Level.INFO, "[SearchRental.java]/Type : "+request.getParameter("type")+"/ID : "+request.getParameter("id")+"/Target : "+target);
						}
						// 対象日に予約のない機材を確認する
						if(!eventDAO.searchOkEvent(target,event,id)) {
							mapMsg.put("okEquipmentId", ""+id);
							mapMsg.put("monthSelectE", request.getParameter("month"));
							mapMsg.put("daySelectE", request.getParameter("day"));
							mapMsg.put("notEquipment", ""+index);
							mapMsg.put("type", "1");
						}else {
							LocalDateTime dt = LocalDateTime.parse(event.getStart(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
							dt = dt.plusDays(1);
							mapMsg.put("okEquipmentId", ""+id);
							mapMsg.put("monthSelectE", ""+dt.getMonthValue());
							mapMsg.put("daySelectE", ""+dt.getDayOfMonth());
							mapMsg.put("notEquipment", ""+index);
							mapMsg.put("type", "2");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						eventDAO.dbDiscon();
					}
				}
				mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));
				mapMsg.put("userId", ""+session.getAttribute("loginUserId"));
				mapMsg.put("numbersEquipment", ""+equipment.getNumbersRecords());

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
				logger.log(Level.WARNING, "[SearchRental.java]"+e.toString(), e.getMessage());
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