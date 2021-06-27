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
 * Servlet implementation class CancelRental
 * レンタル申請の取消、レンタル機材の返却を行うクラス。
 */
@WebServlet("/CancelRental")
public class CancelRental extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelRental() {
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
	 * データベースに接続して対象レンタルのフラッグと対象機材のフラッグを更新する。
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
			Logger logger = Logger.getLogger(CancelRental.class.getName());
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
				int rentalId = Integer.parseInt(request.getParameter("id"));
				int type = Integer.parseInt(request.getParameter("type"));

				EventDAO eventDAO = EventDAO.getInstance();
				Event event = new Event();

				EquipmentDAO equipmentDAO = EquipmentDAO.getInstance();
				Equipment equipment = new Equipment();

				try {
					// 接続
					equipmentDAO.dbConnect(logger);
					// ステートメント作成
					equipmentDAO.createSt();
					// 全機材情報を取得
					equipmentDAO.searchAllEquipment(equipment);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[CancelRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					equipmentDAO.dbDiscon();
				}

				try {
					// 接続
					eventDAO.dbConnect(logger);
					// ステートメント作成
					eventDAO.createSt();
					// レンタル情報から貸し出し機材情報を抽出
					eventDAO.searchEvent(rentalId, event);
					// レンタル取消
					eventDAO.cancelEvent(updateId, rentalId, type);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[CancelRental.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					eventDAO.dbDiscon();
				}
				logger.log(Level.INFO, "[CancelRental.java]/LoginUser : "+session.getAttribute("loginUserId").toString()+"/Type : "+type+"/Start : "+event.getStart(1)+"/Finish : "+event.getFinish(1)+"/Equipment : "+event.getEquipmentId(1));
				// 貸し出し済みのレンタルの場合
				if(type==2) {
					try {
						// 接続
						equipmentDAO.dbConnect(logger);
						// ステートメント作成
						equipmentDAO.createSt();
						// 機材を貸し出し可能に変更
						equipmentDAO.updateEquipment(0, event.getEquipmentId(1), 0);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[CancelRental.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						equipmentDAO.dbDiscon();
					}
				}
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
