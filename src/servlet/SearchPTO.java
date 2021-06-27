package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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

import  com.fasterxml.jackson.databind.ObjectMapper;

import controller.PTOController;
import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.entity.PTO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchPTO
 * 対象月のユーザーの有給取得申請状況を取得するクラス。
 */
@WebServlet("/SearchPTO")
public class SearchPTO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchPTO() {
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
		String jsp="pto_error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SearchPTO.class.getName());
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
				int ptoId = Integer.parseInt(userId);
				int year = Integer.parseInt(request.getParameter("year"));
				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				int yearNow = ld.getYear();

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();

					if(session.getAttribute("loginUserAuthority").equals(2)) {
						// 全ユーザー情報を取得
						staffDAO.searchAllUser(staff);
						if(staff.getNumbersRecords()>0) {
							//追加
							for(int i=1;i<=staff.getNumbersRecords();i++) {
								mapMsg.put("member"+i, ""+staff.getStaffName(i));
								mapMsg.put("memberId"+i, ""+staff.getStaffId(i));
							}
						}
						mapMsg.put("numbersMembers", ""+staff.getNumbersRecords());
					}else {
						// 対象ユーザー情報を取得
						staffDAO.searchUser(ptoId, staff);
						LocalDate ld1 = LocalDate.now();
						if(ld1.getYear()!=year) {
							ld1 = ld1.minusYears(ld1.getYear()-year);
						}
						// 対象年の有給付与日を計算
						LocalDate ld2 = LocalDate.parse(year+"-"+staff.getHireDate(1).substring(5,7)+"-01",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						ld2 = ld2.plusMonths(6);
						// 有給付与日が対象年度外の場合は1年プラスする
						if(ld1.isAfter(ld2)) {
							ld2 = ld2.plusYears(1);
						}
						mapMsg.put("nextGiveDate", ""+ld2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchPTO.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}

				double remain[] = new double[staff.getNumbersRecords()];

				PTODAO ptoDAO = PTODAO.getInstance();
				PTO pto = new PTO();

				try {
					// 接続
					ptoDAO.dbConnect(logger);
					// ステートメント作成
					ptoDAO.createSt();

					int index=0;
					for(int i=1;i<=staff.getNumbersRecords();i++) {
						ptoDAO.searchPTOTarget(staff.getStaffId(i), year, pto);

						for(int j=1;j<=pto.getNumbersRecords();j++) {
							index++;
							mapMsg.put("targetMonthC"+index, ""+pto.getTargetMonth(j));
							mapMsg.put("targetDayC"+index, ""+pto.getTargetDay(j));
							mapMsg.put("divisionC"+index, ""+pto.getDivision(j));
							mapMsg.put("RecordIdC"+index, ""+pto.getPTOId(j));
						}
						// 年間取得日数(終日・午前休・午後休にかかわらず取得した日数)
						mapMsg.put("consumeNumber"+i, ""+pto.getNumbersRecords());
						// 残り有給数(11,12月に翌年分参照の場合は現在年を用いる)
						if(year==yearNow) {
							remain[i-1]=PTOController.total(staff.getHireDate(i)) - ptoDAO.searchPTOTotal(staff.getStaffId(i), year+1);
						}else {
							if(year-1==yearNow&&ld.getMonthValue()>10) {
								remain[i-1]=PTOController.total(staff.getHireDate(i)) - ptoDAO.searchPTOTotal(staff.getStaffId(i), year+1);
							}else {
								remain[i-1]=PTOController.total1(staff.getHireDate(i), year+1) - ptoDAO.searchPTOTotal(staff.getStaffId(i), year+1);
							}
						}
						//年間使用数(終日を1日、午前休・午後休を0.5日で計算)
						mapMsg.put("consume"+i, ""+pto.getNUMBER(1));
					}
					//全ユーザーの年間取得日数(終日・午前休・午後休にかかわらず取得した日数)の合計
					mapMsg.put("numbersPTOConsume", ""+index);

					if(session.getAttribute("loginUserAuthority").equals(2)) {
						ptoDAO.searchAllPTO(year, pto);
					}else {
						ptoDAO.searchPTOPersonal(ptoId, year, pto);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchPTO.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					ptoDAO.dbDiscon();
				}

				mapMsg.put("authority", ""+session.getAttribute("loginUserAuthority"));
				mapMsg.put("numbersUser", ""+staff.getNumbersRecords());
				mapMsg.put("numbersPTO", ""+pto.getNumbersRecords());

				if(pto.getNumbersRecords()>0) {
					//追加
					for(int i=1;i<=pto.getNumbersRecords();i++) {
						mapMsg.put("targetMonth"+i, ""+pto.getTargetMonth(i));
						mapMsg.put("targetDay"+i, ""+pto.getTargetDay(i));
						mapMsg.put("division"+i, ""+pto.getDivision(i));
						mapMsg.put("flag"+i, ""+pto.getFlag(i));
						mapMsg.put("reason"+i, ""+pto.getReason(i));
						mapMsg.put("RecordIdW"+i, ""+pto.getPTOId(i));
					}
				}

				if(session.getAttribute("loginUserAuthority").equals(2)) {
					for(int i=1;i<=staff.getNumbersRecords();i++) {
						mapMsg.put("id"+i, ""+staff.getStaffId(i));
						mapMsg.put("name"+i, ""+staff.getStaffName(i));
						double[] result = PTOController.info(staff, i, logger);
						mapMsg.put("remain"+i, ""+remain[i-1]);
						mapMsg.put("lost"+i, ""+result[1]);
						mapMsg.put("wait"+i, ""+pto.getWait(staff.getStaffId(i)));
					}
				}else {
					double[] result = PTOController.info(staff, 1, logger);
					mapMsg.put("remain1", ""+remain[0]);
					mapMsg.put("lost1", ""+result[1]);
					mapMsg.put("wait1", ""+pto.getWait(staff.getStaffId(1)));
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
				logger.log(Level.WARNING, "[SearchPTO.java]"+e.toString(), e.getMessage());
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