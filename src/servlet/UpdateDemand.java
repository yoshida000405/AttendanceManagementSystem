package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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

import controller.DateTimeController;
import model.dao.DemandDAO;
import model.dao.MonthDAO;
import model.dao.StaffDAO;
import model.entity.Demand;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class UpdateAttendance
 * 対象月のユーザーの請求書を更新するクラス。
 */
@WebServlet("/UpdateDemand")
public class UpdateDemand extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateDemand() {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("home.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して対象月のユーザーの請求書を更新する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//request.setCharacterEncoding("Shift_JIS");

		HttpSession session = request.getSession();
		String jsp = "demand_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(UpdateDemand.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				DemandDAO demandDAO = DemandDAO.getInstance();
				Demand demand = new Demand();

				MonthDAO monthDAO = MonthDAO.getInstance();

				String userId = (String) session.getAttribute("loginUserId");
				int demandId = Integer.parseInt(userId);
				if (session.getAttribute("id") != null) {
					demandId = Integer.parseInt((String) (session.getAttribute("id")));
				}
				// IDのリクエストがある場合は削除、ない場合は更新
				if (request.getParameter("id") == null) {
					int month = Integer.parseInt(request.getParameter("month"));
					int year = LocalDateTime.now().getYear();
					if (LocalDateTime.now().getMonthValue() == 1 && month == 12) {
						year--;
					}
					// 処理フラッグを一括更新するかで条件分岐
					if (request.getParameter("all") == null) {
						if (request.getParameter("submit").contentEquals("1")) {
							staff.setDemandFlag(1, 1);
							try {
								// 接続
								staffDAO.dbConnect(logger);
								// ステートメント作成
								staffDAO.createSt();
								// 提出状態を更新する
								staffDAO.updateFlag(demandId, Integer.parseInt(userId), 2, staff);
								logger.log(Level.INFO, "[UpdateDemand.java]/UserId : " + demandId + "/Year : " + year
										+ "/Month : " + month + "/updateFlag");
							} catch (Exception e) {
								e.printStackTrace();
								logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
							} finally {
								staffDAO.dbDiscon();
							}
							mapMsg.put("submitFlag", "提出");
						}

						//demandインスタンスに更新するデータをセット
						int multiCheck = Integer.parseInt(request.getParameter("multiCheck"));
						String numbersAplication = request.getParameter("numbersAplication");
						demand.setNumbersRecords(Integer.parseInt(numbersAplication));
						for (int i = 1; i <= Integer.parseInt(numbersAplication); i++) {
							demand.setTargetDay(i, Integer.parseInt(request.getParameter("targetDay" + i)));
							demand.setAplicationMonth(i, Integer.parseInt(request.getParameter("aplicationMonth" + i)));
							demand.setAplicationDay(i, Integer.parseInt(request.getParameter("aplicationDay" + i)));
							demand.setAplicationAmount(i,
									Integer.parseInt(request.getParameter("aplicationAmount" + i)));
							demand.setTo(i, request.getParameter("to" + i));
							demand.setDescription(i, request.getParameter("description" + i));
							demand.setDivision(i, Integer.parseInt(request.getParameter("division" + i)));
							demand.setRemarks(i, request.getParameter("remarks" + i));
							if (multiCheck == 1) {
								demand.setFlag(i, Integer.parseInt(request.getParameter("flag" + i)));
							} else if (multiCheck == 2) {
								demand.setFlag(i, 1);
							} else {
								demand.setFlag(i, 0);
							}
							demand.setRecordId(i, Integer.parseInt(request.getParameter("demandId" + i)));
							demand.setMulti(i, Integer.parseInt(request.getParameter("multi" + i)));
							demand.setRecipt(i, Integer.parseInt(request.getParameter("recipt" + i)));
						}
						demand.setMemo(1, request.getParameter("memo"));

						try {
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();

							demandDAO.updateDemand(demandId, Integer.parseInt(userId), year, month, demand);
							logger.log(Level.INFO, "[UpdateDemand.java]/UserId : " + demandId + "/Year : " + year
									+ "/Month : " + month + "/updateDemand");
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}

						try {
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();

							demandDAO.searchDemand(demandId, month, year, demand);
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}

						//取得した月の日数
						int days = DateTimeController.getMonthDays(year, month);

						if (demand.getNumbersRecords() > 0) {
							//追加
							for (int i = 1; i <= demand.getNumbersRecords(); i++) {
								mapMsg.put("targetDay" + i, "" + demand.getTargetDay(i));
								mapMsg.put("money" + i, "" + demand.getAplicationAmount(i));
								mapMsg.put("division" + i, "" + demand.getDivision(i));
								mapMsg.put("to" + i, demand.getTo(i));
								mapMsg.put("description" + i, demand.getDescription(i));
								mapMsg.put("remarks" + i, demand.getRemarks(i));
								mapMsg.put("flag" + i, "" + demand.getFlag(i));
								mapMsg.put("demandId" + i, "" + demand.getRecordId(i));
								mapMsg.put("multi" + i, "" + demand.getMulti(i));
								mapMsg.put("recipt" + i, "" + demand.getRecipt(i));
							}
						}

						if (days != 29) {
							mapMsg.put("month", "" + month);
						} else {
							mapMsg.put("month", "22");
						}

						mapMsg.put("sum1", "" + demand.getTransportation());
						mapMsg.put("sum2", "" + demand.getExpendables());
						mapMsg.put("sum3", "" + demand.getMeeting());
						mapMsg.put("sum4", "" + demand.getCommunications());
						mapMsg.put("sum5", "" + demand.getCommission());
						mapMsg.put("sum6", "" + demand.getSocial());
						mapMsg.put("sum7", "" + demand.getOther());
						mapMsg.put("sum8", "" + demand.getSum());
						mapMsg.put("numbersAplication", "" + demand.getNumbersRecords());
						mapMsg.put("authority", "" + session.getAttribute("loginUserAuthority"));
						mapMsg.put("update", demand.getLastUpdate());

						//マッパ(JSON <-> Map, List)
						ObjectMapper mapper = new ObjectMapper();
						//json文字列
						String jsonStr = mapper.writeValueAsString(mapMsg); //list, map
						//ヘッダ設定
						response.setContentType("application/json;charset=UTF-8"); //JSON形式, UTF-8
						//pwオブジェクト
						PrintWriter pw = response.getWriter();
						//出力
						pw.print(jsonStr);
						//クローズ
						pw.close();
					} else {
						try {
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();
							// 全員分の請求書を処理済みにする
							demandDAO.updateDemand(Integer.parseInt(userId), year, month);
							logger.log(Level.INFO, "[UpdateDemand.java]/AllCheck/Year : " + year + "/Month : " + month);
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}
						try {
							// 接続
							monthDAO.dbConnect(logger);
							// ステートメント作成
							monthDAO.createSt();
							// 対象月の請求書チェックを処理済みにする
							monthDAO.updateDemandCheck(month);
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}
						//マッパ(JSON <-> Map, List)
						ObjectMapper mapper = new ObjectMapper();
						//json文字列
						String jsonStr = mapper.writeValueAsString(mapMsg); //list, map
						//ヘッダ設定
						response.setContentType("application/json;charset=UTF-8"); //JSON形式, UTF-8
						//pwオブジェクト
						PrintWriter pw = response.getWriter();
						//出力
						pw.print(jsonStr);
						//クローズ
						pw.close();
					}
				} else {
					try {
						// 接続
						demandDAO.dbConnect(logger);
						// ステートメント作成
						demandDAO.createSt();
						// 管理者権限で請求情報を削除
						demandDAO.deleteDemand(Integer.parseInt(request.getParameter("id")));
						logger.log(Level.INFO, "[UpdateDemand.java]/Delete/ID : " + request.getParameter("id"));
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						demandDAO.dbDiscon();
					}
					//マッパ(JSON <-> Map, List)
					ObjectMapper mapper = new ObjectMapper();
					//json文字列
					String jsonStr = mapper.writeValueAsString(mapMsg); //list, map
					//ヘッダ設定
					response.setContentType("application/json;charset=UTF-8"); //JSON形式, UTF-8
					//pwオブジェクト
					PrintWriter pw = response.getWriter();
					//出力
					pw.print(jsonStr);
					//クローズ
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[UpdateDemand.java]" + e.toString(), e.getMessage());
				errorCheck = 1;
			} finally {
				handler.close();
				if (errorCheck == 1 || errorCheck == 2) {
					response.sendRedirect(jsp);
				}
			}
		}
	}
}