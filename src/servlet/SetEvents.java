package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

import com.google.gson.Gson;

import model.dao.EventDAO;
import model.dao.StaffDAO;
import model.entity.Calendar;
import model.entity.Event;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SetEvents
 * カレンダーにイベント情報を送信するクラス。
 */
@WebServlet("/SetEvents")
public class SetEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetEvents() {
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

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="rental_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SetEvents.class.getName());
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

				String start = request.getParameter("start").substring(0, 10);
				String finish = request.getParameter("end").substring(0, 10);

				EventDAO eventDAO = EventDAO.getInstance();
				Event event = new Event();
				try {
					// 接続
					eventDAO.dbConnect(logger);
					// ステートメント作成
					eventDAO.createSt();
					// 対象月のレンタル情報を取得
					eventDAO.searchEvent(start, finish, event);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SetEvents.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					eventDAO.dbDiscon();
				}

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				List<Calendar> list = new ArrayList<Calendar>();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();

					for(int i=1; i<=event.getNumbersRecords(); i++) {
						// レンタルしているユーザー情報を取得
						staffDAO.searchUser(event.getStaffId(i), staff);
						list.add(new Calendar(event.getEventId(i),event.getTitle(i)+":"+staff.getStaffName(1),event.getStart(i),event.getFinish(i)));
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SetEvents.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write(new Gson().toJson(list));
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[SetEvents.java]"+e.toString(), e.getMessage());
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