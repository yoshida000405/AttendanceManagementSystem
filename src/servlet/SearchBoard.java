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

import model.dao.BoardDAO;
import model.entity.Board;
import model.properties.Properties;

/**
 * Servlet implementation class SearchBoard
 * 対象の掲示板内容を取得するクラス。
 */
@WebServlet("/SearchBoard")
public class SearchBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBoard() {
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
	 * データベースに接続して対象の掲示板内容を取得する。
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
			Logger logger = Logger.getLogger(SearchBoard.class.getName());
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

				int type = Integer.parseInt(request.getParameter("type"));
				int boardId = 0;

				if(type<5) {
					BoardDAO boardDAO = BoardDAO.getInstance();
					Board board = new Board();

					boardId = Integer.parseInt(request.getParameter("nowId"));
					board.setUpdDate(1, request.getParameter("updDate"));

					// 一覧ページから各ページへの遷移の場合
					if(session.getAttribute("BoardId")!=null) {
						boardId = Integer.parseInt((String)(session.getAttribute("BoardId")));
						session.removeAttribute("BoardId");
					}
					try {
						// 接続
						boardDAO.dbConnect(logger);
						// ステートメント作成
						boardDAO.createSt();
						// type:3 掲示板一覧の検索
						if(type!=3) {
							// 掲示板投稿の検索
							boardDAO.searchBoard(type, boardId, board);
							logger.log(Level.INFO, "[SearchBoard.java]/BoardId : "+boardId);
						}else {
							if(boardId==1) {
								// 掲示板一覧の前ページ検索
								boardDAO.searchAllBoard(board, request.getParameter("firstUpdDate"), boardId);
								logger.log(Level.INFO, "[SearchBoard.java]/BoardAll : Preview");
							}else if(boardId==2){
								// 掲示板一覧の次ページ検索
								boardDAO.searchAllBoard(board, request.getParameter("lastUpdDate"), boardId);
								logger.log(Level.INFO, "[SearchBoard.java]/BoardAll : Next");
							}else {
								// 掲示板一覧のTOPページ検索
								boardDAO.searchAllBoard(board, null, boardId);
								logger.log(Level.INFO, "[SearchBoard.java]/BoardAll : Top");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchBoard.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						boardDAO.dbDiscon();
					}
					//JSONマップ
					Map<String, String> mapMsg = new HashMap<String, String>();

					if(type!=3) {
						mapMsg.put("subject1", board.getSubject(1));
						mapMsg.put("subject2", board.getSubject(2));
						mapMsg.put("subject3", board.getSubject(3));
						mapMsg.put("content1", board.getContent(1));
						mapMsg.put("id1", ""+board.getBoardId(1));
						mapMsg.put("id2", ""+board.getBoardId(2));
						mapMsg.put("id3", ""+board.getBoardId(3));
						mapMsg.put("updDate", ""+board.getUpdDate(1));
					}else {
						if(boardId==1) {
							for(int i=1; i<=board.getNumbersRecords(); i++) {
								mapMsg.put("subject"+i, board.getSubject(board.getNumbersRecords()-(i-1)));
								mapMsg.put("content"+i, board.getContent(board.getNumbersRecords()-(i-1)));
								mapMsg.put("id"+i, ""+board.getBoardId(board.getNumbersRecords()-(i-1)));
								mapMsg.put("date"+i, ""+board.getUpdDate(board.getNumbersRecords()-(i-1)));
								if(i==1) {
									mapMsg.put("firstUpdDate", ""+board.getUpdDate(board.getNumbersRecords()-(i-1)));
								}
								if(i==board.getNumbersRecords()) {
									mapMsg.put("lastUpdDate", ""+board.getUpdDate(board.getNumbersRecords()-(i-1)));
								}
							}
						}else {
							for(int i=1; i<=board.getNumbersRecords(); i++) {
								mapMsg.put("subject"+i, board.getSubject(i));
								mapMsg.put("content"+i, board.getContent(i));
								mapMsg.put("id"+i, ""+board.getBoardId(i));
								mapMsg.put("date"+i, ""+board.getUpdDate(i));
								if(i==1) {
									mapMsg.put("firstUpdDate", ""+board.getUpdDate(i));
								}
								if(i==board.getNumbersRecords()) {
									mapMsg.put("lastUpdDate", ""+board.getUpdDate(i));
								}
							}
						}
						mapMsg.put("numbersRecord", ""+board.getNumbersRecords());
					}
					mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));
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
				}else {
					// 一覧ページから各ページを選択した場合
					if(request.getParameter("nowId")!=null) {
						session.setAttribute("BoardId", request.getParameter("nowId"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[SearchBoard.java]"+e.toString(), e.getMessage());
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
