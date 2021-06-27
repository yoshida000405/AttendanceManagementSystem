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

import model.dao.BoardDAO;
import model.entity.Board;
import model.properties.Properties;

/**
 * Servlet implementation class UpdateBoard
 * 対象掲示板投稿を更新するクラス。
 */
@WebServlet("/UpdateBoard")
public class UpdateBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateBoard() {
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
	 * データベースに接続して対象掲示板投稿を更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp="board_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(UpdateBoard.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			try {

				// フォーマッターを作成してハンドラーに登録
				Formatter formatter =  new SimpleFormatter();
				handler.setFormatter(formatter);

				String userId = (String)session.getAttribute("loginUserId");
				int updateId = Integer.parseInt(userId);

				BoardDAO boardDAO = BoardDAO.getInstance();
				Board board = new Board();

				//boardインスタンスに更新するデータをセット
				board.setSubject(1, request.getParameter("subject"));
				board.setContent(1, request.getParameter("content"));
				int boardId = Integer.parseInt(request.getParameter("nowId"));

				try {
					// 接続
					boardDAO.dbConnect(logger);
					// ステートメント作成
					boardDAO.createSt();

					boardDAO.updateBoard(updateId, boardId, board);
					logger.log(Level.INFO, "[UpdateBoard.java]/BoardId : "+boardId);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[UpdateBoard.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					boardDAO.dbDiscon();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[UpdateBoard.java]"+e.toString(), e.getMessage());
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