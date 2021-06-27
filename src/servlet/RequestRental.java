package servlet;

import java.io.IOException;
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

import model.dao.EquipmentDAO;
import model.dao.EventDAO;
import model.entity.Equipment;
import model.entity.Event;
import model.properties.Properties;

/**
 * Servlet implementation class RequestRental
 * 機材レンタル申請を行うクラス。
 */
@WebServlet("/RequestRental")
public class RequestRental extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestRental() {
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
		String jsp="rental_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(RequestRental.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				String userId = (String)session.getAttribute("loginUserId");
				int updateId = Integer.parseInt(userId);

				EquipmentDAO equipmentDAO = EquipmentDAO.getInstance();
				Equipment equipment = new Equipment();

				try {
					// 接続
					equipmentDAO.dbConnect(logger);
					// ステートメント作成
					equipmentDAO.createSt();
					// 全機材の情報取得
					equipmentDAO.searchAllEquipment(equipment);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[RequestRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					equipmentDAO.dbDiscon();
				}


				EventDAO eventDAO = EventDAO.getInstance();
				Event event = new Event();

				int year=Integer.parseInt(request.getParameter("year"));
				String startMonth="";
				String startDay="";
				String finishMonth="";
				String finishDay="";

				// 新規予約時の場合
				if(request.getParameter("type").contentEquals("1")) {
					event.setEquipmentId(1, Integer.parseInt(request.getParameter("equipmentId")));

					if(Integer.parseInt(request.getParameter("startMonth"))<10) {
						startMonth="0"+Integer.parseInt(request.getParameter("startMonth"));
					}else {
						startMonth=""+Integer.parseInt(request.getParameter("startMonth"));
					}
					if(Integer.parseInt(request.getParameter("startDay"))<10) {
						startDay="0"+Integer.parseInt(request.getParameter("startDay"));
					}else {
						startDay=""+Integer.parseInt(request.getParameter("startDay"));
					}
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
					event.setTitle(1, equipment.getName(Integer.parseInt(request.getParameter("equipmentId"))));
					event.setStart(1, year+"-"+startMonth+"-"+startDay+" 00:00:00");
					event.setFinish(1, year+"-"+finishMonth+"-"+finishDay+" 23:59:59");
				}

				try {
					// 接続
					eventDAO.dbConnect(logger);
					// ステートメント作成
					eventDAO.createSt();
					// 新規予約作成
					eventDAO.insertEvent(updateId, event, 1);
					logger.log(Level.INFO, "[RequestRental.java]/insertPTO/loginUser : "+session.getAttribute("loginUserId").toString()+"/eventId : "+event.getEventId(1));
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[RequestRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					eventDAO.dbDiscon();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[RequestRental.java]"+e.toString(), e.getMessage());
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
