package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.DateTimeController;
import model.dao.AttendanceDAO;
import model.dao.DemandDAO;
import model.dao.HolidayDAO;
import model.dao.MonthDAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.Demand;
import model.entity.Holiday;
import model.entity.Managed;
import model.entity.Month;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class SearchManaged
 * 勤務表や請求書を一覧取得するクラス。
 */
@WebServlet("/SearchManaged")
public class SearchManaged extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchManaged() {
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
	 * データベースに接続して随時データを取得する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String jsp = "managed_error.jsp";
		int errorCheck = 0;
		if (session.getAttribute("loginUserId") == null) {
			jsp = "session_out.jsp";
			errorCheck = 2;
		} else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(SearchManaged.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH + session.getAttribute("loginUserId") + "_webApp.log",
					true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);

			try {

				int year = LocalDateTime.now().getYear();
				int month = Integer.parseInt(request.getParameter("month"));
				int type = Integer.parseInt(request.getParameter("type"));
				int member = Integer.parseInt(request.getParameter("member"));
				LocalDate ld = LocalDate.now(ZoneId.of("Asia/Tokyo"));
				// 3日まで前の月の情報を表示
				if (month == 14) {
					if (ld.getDayOfMonth() < 4) {
						month = ld.minusMonths(1).getMonthValue();
					} else {
						month = ld.getMonthValue();
					}
				}
				// 1月時点で12月を対象とした場合は昨年になる
				if (ld.getMonthValue() == 1 && month == 12) {
					year--;
				}
				// 管理者権限による編集セッション情報を削除
				if (session.getAttribute("type") != null) {
					session.removeAttribute("id");
					session.removeAttribute("month");
					session.removeAttribute("type");
				}
				logger.log(Level.INFO, "[SearchManaged.java]/Type : " + type + "/Member : " + member + "/Year : " + year
						+ "/Month : " + month);
				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 全ユーザー情報を取得
					staffDAO.searchAllUser(staff);
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
					errorCheck = 1;
				} finally {
					staffDAO.dbDiscon();
				}

				if (staff.getNumbersRecords() > 0) {
					//追加
					for (int i = 1; i <= staff.getNumbersRecords(); i++) {
						mapMsg.put("member" + i, "" + staff.getStaffName(i));
						mapMsg.put("memberId" + i, "" + staff.getStaffId(i));
					}
				}
				mapMsg.put("numbersMembers", "" + staff.getNumbersRecords());
				mapMsg.put("authority", "" + session.getAttribute("loginUserAuthority"));

				HolidayDAO holidayDAO = HolidayDAO.getInstance();
				Holiday holiday = new Holiday();

				if (month != 6 && month != 10 && month != 12 && month != 13) {
					try {
						// 接続
						holidayDAO.dbConnect(logger);
						// ステートメント作成
						holidayDAO.createSt();
						// 対象月の祝日情報を取得
						holidayDAO.setHoliday(year, month, holiday);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						holidayDAO.dbDiscon();
					}
				}

				// 個人を対象とする場合
				if (member > 6) {
					staff = new Staff();
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// 6つのグループ分の数を引きユーザーIDとする
						member -= 6;
						// 対象ユーザー情報を取得
						staffDAO.searchUser(member, staff);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						staffDAO.dbDiscon();
					}
					// 勤務表情報を取得
					if (type == 1) {
						AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
						Attendance attendance = new Attendance();
						DemandDAO demandDAO = DemandDAO.getInstance();
						Demand demand = new Demand();
						Managed managed = new Managed();
						try {
							// 接続
							attendanceDAO.dbConnect(logger);
							// ステートメント作成
							attendanceDAO.createSt();
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();
							// 対象月の情報を取得
							if (month != 13) {
								// 直接入力かを判定
								attendanceDAO.searchAttendance(member, month, year, attendance);
								int days = DateTimeController.getMonthDays(year, month);
								if (attendance.getDivision(1) == 10) {
									managed.setOperatingDays(1, Double.parseDouble(attendance.getFinishTime(1)));
									managed.setOperatingTime(1, Double.parseDouble(attendance.getStartTime(1)));
									managed.setSubstitute(1, Integer.parseInt(attendance.getRemarks(1)));
									managed.setPaid(1, Double.parseDouble(attendance.getMemo(1)));
									managed.setAbsence(1, Integer.parseInt(attendance.getBreakTime(1)));
									managed.setHoliday(1, (int) (days - managed.getOperatingDays(1)
											- managed.getSubstitute(1) - managed.getPaid(1) - managed.getAbsence(1)));
								} else {
									attendanceDAO.searchAttendance(member, month, year, attendance, holiday);
									managed.setOperatingDays(1, attendance.getOperatingDays());
									managed.setOperatingTime(1, attendance.getOperatingTime());
									managed.setHoliday(1, attendance.getHoliday());
									managed.setSubstitute(1, attendance.getSubstitute());
									managed.setPaid(1, attendance.getPaid());
									managed.setAbsence(1, attendance.getAbsence());
									managed.setOverNight(1, attendance.getOverNight());
								}
								attendanceDAO.searchInAttendance(member, month, year, attendance);
								managed.setOperatingTime(1, managed.getOperatingTime(1) + attendance.getInTime());
								// 対象ユーザーの経費の情報を計算
								demand = new Demand();
								demandDAO.searchDemand(member, month, year, demand);
								managed.setSum(1, demand.getSum());
							} else {
								// 年間の情報を取得
								for (int i = 1; i <= 12; i++) {
									attendance = new Attendance();
									holiday = new Holiday();
									if (i != 6 && i != 10 && i != 12) {
										try {
											// 接続
											holidayDAO.dbConnect(logger);
											// ステートメント作成
											holidayDAO.createSt();
											// 対象月の祝日情報を取得
											holidayDAO.setHoliday(year, i, holiday);
										} catch (Exception e) {
											e.printStackTrace();
											logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(),
													e.getMessage());
											errorCheck = 1;
										} finally {
											holidayDAO.dbDiscon();
										}
									}
									attendanceDAO.searchAttendance(member, i, year, attendance);
									int days = DateTimeController.getMonthDays(year, i);
									// 直接入力かを判定
									if (attendance.getDivision(1) == 10) {
										managed.setOperatingDays(i, Double.parseDouble(attendance.getFinishTime(1)));
										managed.setOperatingTime(i, Double.parseDouble(attendance.getStartTime(1)));
										managed.setSubstitute(i, Integer.parseInt(attendance.getRemarks(1)));
										managed.setPaid(i, Double.parseDouble(attendance.getMemo(1)));
										managed.setAbsence(i, Integer.parseInt(attendance.getBreakTime(1)));
										managed.setHoliday(i,
												(int) (days - managed.getOperatingDays(i) - managed.getSubstitute(i)
														- managed.getPaid(i) - managed.getAbsence(i)));
									} else {
										attendanceDAO.searchAttendance(member, i, year, attendance, holiday);
										managed.setOperatingDays(i, attendance.getOperatingDays());
										managed.setOperatingTime(i, attendance.getOperatingTime());
										managed.setHoliday(i, attendance.getHoliday());
										managed.setSubstitute(i, attendance.getSubstitute());
										managed.setPaid(i, attendance.getPaid());
										managed.setAbsence(i, attendance.getAbsence());
										managed.setOverNight(i, attendance.getOverNight());
									}
									attendanceDAO.searchInAttendance(member, i, year, attendance);
									managed.setOperatingTime(i, managed.getOperatingTime(i) + attendance.getInTime());
									// 対象ユーザーの経費の情報を計算
									demand = new Demand();
									demandDAO.searchDemand(member, i, year, demand);
									managed.setSum(i, demand.getSum());
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							attendanceDAO.dbDiscon();
						}
						// 対象月の情報をセット
						if (month != 13) {
							mapMsg.put("column1_1", "" + month);
							mapMsg.put("column2_1", "" + managed.getOperatingTime(1));
							mapMsg.put("column3_1", "" + managed.getOperatingDays(1));
							mapMsg.put("column4_1", "" + managed.getAbsence(1));
							mapMsg.put("column5_1", "" + (managed.getOperatingTime(1)
									- (managed.getOperatingDays(1) + managed.getAbsence(1) + managed.getOverNight(1))
											* 8));
							mapMsg.put("column6_1", "" + managed.getPaid(1));
							mapMsg.put("column7_1", "" + staff.getPosition(1));
							// 勤怠報告書のデータがあるかを確認
							if (new File(Properties.SAVE_DIR_PATH + member + "/workschedule" + year + month
									+ ".pdf").exists()) {
								mapMsg.put("column8_1", "exist");
							} else {
								mapMsg.put("column8_1", "none");
							}
							mapMsg.put("column9_1", "" + managed.getSum(1));
							mapMsg.put("column10_1", "" + month);
							mapMsg.put("numbersAplication", "1");
						} else {
							// 年間の情報をセット
							for (int i = 1; i <= 12; i++) {
								mapMsg.put("column1_" + i, "" + i);
								mapMsg.put("column2_" + i, "" + managed.getOperatingTime(i));
								mapMsg.put("column3_" + i, "" + managed.getOperatingDays(i));
								mapMsg.put("column4_" + i, "" + managed.getAbsence(i));
								mapMsg.put("column5_" + i, "" + (managed.getOperatingTime(i)
										- (managed.getOperatingDays(i) + managed.getAbsence(i)
												+ managed.getOverNight(i)) * 8));
								mapMsg.put("column6_" + i, "" + managed.getPaid(i));
								mapMsg.put("column7_" + i, "" + staff.getPosition(i));
								// 勤怠報告書のデータがあるかを確認
								if (new File(Properties.SAVE_DIR_PATH + member + "/workschedule" + year + i
										+ ".pdf").exists()) {
									mapMsg.put("column8_" + i, "exist");
								} else {
									mapMsg.put("column8_" + i, "none");
								}
								mapMsg.put("column9_" + i, "" + managed.getSum(i));
								mapMsg.put("column10_" + i, "" + i);
							}
							mapMsg.put("numbersAplication", "12");
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

						// 請求書情報を取得
					} else if (type == 2) {
						DemandDAO demandDAO = DemandDAO.getInstance();
						Demand demand = new Demand();
						MonthDAO monthDAO = MonthDAO.getInstance();
						Month monthInfo = new Month();
						Managed managed = new Managed();

						try {
							// 接続
							monthDAO.dbConnect(logger);
							// ステートメント作成
							monthDAO.createSt();
							// 対象月のチェック状況を取得
							monthDAO.searchDemandCheck(month, monthInfo);
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}

						try {
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();
							// 対象月の情報を取得
							if (month != 13) {
								demandDAO.searchDemand(member, month, year, demand);
								managed.setTransportation(1, demand.getTransportation());
								managed.setExpendables(1, demand.getExpendables());
								managed.setMeeting(1, demand.getMeeting());
								managed.setCommunications(1, demand.getCommunications());
								managed.setCommission(1, demand.getCommission());
								managed.setSocial(1, demand.getSocial());
								managed.setOther(1, demand.getOther());
								managed.setSum(1, demand.getSum());
							} else {
								// 年間の情報を取得
								for (int i = 1; i <= 12; i++) {
									;
									demand = new Demand();
									demandDAO.searchDemand(member, i, year, demand);
									managed.setTransportation(i, demand.getTransportation());
									managed.setExpendables(i, demand.getExpendables());
									managed.setMeeting(i, demand.getMeeting());
									managed.setCommunications(i, demand.getCommunications());
									managed.setCommission(i, demand.getCommission());
									managed.setSocial(i, demand.getSocial());
									managed.setOther(i, demand.getOther());
									managed.setSum(i, demand.getSum());
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}
						// 対象月の情報をセット
						if (month != 13) {
							mapMsg.put("check", "" + monthInfo.getCheck(1));
							mapMsg.put("column1_1", "" + month);
							mapMsg.put("column2_1", "" + managed.getSum(1));
							mapMsg.put("column3_1", "" + managed.getTransportation(1));
							mapMsg.put("column4_1", "" + managed.getExpendables(1));
							mapMsg.put("column5_1", "" + managed.getMeeting(1));
							mapMsg.put("column6_1", "" + managed.getCommunications(1));
							mapMsg.put("column7_1", "" + managed.getCommission(1));
							mapMsg.put("column8_1", "" + managed.getSocial(1));
							mapMsg.put("column9_1", "" + managed.getOther(1));
							mapMsg.put("column10_1", "" + month);
							mapMsg.put("numbersAplication", "1");
						} else {
							// 年間の情報をセット
							for (int i = 1; i <= 12; i++) {
								mapMsg.put("column1_" + i, "" + i);
								mapMsg.put("column2_" + i, "" + managed.getSum(i));
								mapMsg.put("column3_" + i, "" + managed.getTransportation(i));
								mapMsg.put("column4_" + i, "" + managed.getExpendables(i));
								mapMsg.put("column5_" + i, "" + managed.getMeeting(i));
								mapMsg.put("column6_" + i, "" + managed.getCommunications(i));
								mapMsg.put("column7_" + i, "" + managed.getCommission(i));
								mapMsg.put("column8_" + i, "" + managed.getSocial(i));
								mapMsg.put("column9_" + i, "" + managed.getOther(i));
								mapMsg.put("column10_" + i, "" + i);
							}
							mapMsg.put("numbersAplication", "12");
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

						// type:3 スキルシート情報 type:4 社員情報
					} else if (type == 3 || type == 4) {
						// 対象月の情報をセット
						if (month != 13) {
							if (type == 3) {
								mapMsg.put("column1_1", "" + month);
							} else {
								mapMsg.put("column1_1", "" + staff.getStaffName(1));
							}
							mapMsg.put("column9_1", "" + staff.getPosition(1));
							mapMsg.put("column10_1", "" + staff.getStaffId(1));
							mapMsg.put("numbersAplication", "1");
						} else {
							// 年間の情報をセット
							for (int i = 1; i <= 12; i++) {
								mapMsg.put("column1_" + i, "" + i);
								mapMsg.put("column9_" + i, "" + staff.getPosition(i));
								mapMsg.put("column10_" + i, "" + staff.getStaffId(1));
							}
							mapMsg.put("numbersAplication", "12");
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

					//グループを対象とする場合
				} else {
					staff = new Staff();
					try {
						// 接続
						staffDAO.dbConnect(logger);
						// ステートメント作成
						staffDAO.createSt();
						// グループ情報を取得
						staffDAO.searchGroup(member, staff);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
						errorCheck = 1;
					} finally {
						staffDAO.dbDiscon();
					}
					// 勤務表情報を取得
					if (type == 1) {
						AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
						Managed managed = new Managed();

						DemandDAO demandDAO = DemandDAO.getInstance();
						Demand demand = new Demand();

						try {
							// 接続
							attendanceDAO.dbConnect(logger);
							// ステートメント作成
							attendanceDAO.createSt();
							// 各ユーザーの稼働時間等の情報を計算
							for (int i = 1; i <= staff.getNumbersRecords(); i++) {
								Attendance attendance = new Attendance();
								attendanceDAO.searchAttendance(staff.getStaffId(i), month, year, attendance);
								managed.setName(i, staff.getStaffName(i));
								managed.setId(i, staff.getStaffId(i));
								int days = DateTimeController.getMonthDays(year, month);
								if (attendance.getDivision(1) == 10) {
									managed.setOperatingDays(i, Double.parseDouble(attendance.getFinishTime(1)));
									managed.setOperatingTime(i, Double.parseDouble(attendance.getStartTime(1)));
									managed.setSubstitute(i, Integer.parseInt(attendance.getRemarks(1)));
									managed.setPaid(i, Double.parseDouble(attendance.getMemo(1)));
									managed.setAbsence(i, Integer.parseInt(attendance.getBreakTime(1)));
									managed.setHoliday(i, (int) (days - managed.getOperatingDays(i)
											- managed.getSubstitute(i) - managed.getPaid(i) - managed.getAbsence(i)));
								} else {
									attendanceDAO.searchAttendance(staff.getStaffId(i), month, year, attendance,
											holiday);
									managed.setOperatingDays(i, attendance.getOperatingDays());
									managed.setOperatingTime(i, attendance.getOperatingTime());
									managed.setHoliday(i, attendance.getHoliday());
									managed.setSubstitute(i, attendance.getSubstitute());
									managed.setPaid(i, attendance.getPaid());
									managed.setAbsence(i, attendance.getAbsence());
									managed.setOverNight(i, attendance.getOverNight());
								}
								managed.setPosition(i, staff.getPosition(i));
								attendanceDAO.searchInAttendance(staff.getStaffId(i), month, year, attendance);
								managed.setOperatingTime(i, managed.getOperatingTime(i) + attendance.getInTime());
							}
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();
							// 各ユーザーの経費の情報を計算
							for (int i = 1; i <= staff.getNumbersRecords(); i++) {
								demand = new Demand();
								demandDAO.searchDemand(staff.getStaffId(i), month, year, demand);
								managed.setSum(i, demand.getSum());
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							attendanceDAO.dbDiscon();
						}
						// 各ユーザーの稼働時間等の情報をセット
						for (int i = 1; i <= staff.getNumbersRecords(); i++) {
							mapMsg.put("column1_" + i, "" + managed.getName(i));
							mapMsg.put("column2_" + i, "" + managed.getOperatingTime(i));
							mapMsg.put("column3_" + i, "" + managed.getOperatingDays(i));
							mapMsg.put("column4_" + i, "" + managed.getAbsence(i));
							mapMsg.put("column5_" + i, "" + (managed.getOperatingTime(i)
									- (managed.getOperatingDays(i) + managed.getAbsence(i) + managed.getOverNight(i))
											* 8));
							mapMsg.put("column6_" + i, "" + managed.getPaid(i));
							mapMsg.put("column7_" + i, "" + managed.getPosition(i));
							if (new File(Properties.SAVE_DIR_PATH + staff.getStaffId(i) + "/workschedule"
									+ year + month + ".pdf").exists()) {
								mapMsg.put("column8_" + i, "exist");
							} else {
								mapMsg.put("column8_" + i, "none");
							}
							mapMsg.put("column9_" + i, "" + managed.getSum(i));
							mapMsg.put("column10_" + i, "" + managed.getId(i));
						}
						mapMsg.put("numbersAplication", "" + staff.getNumbersRecords());
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

						// 請求書情報を取得
					} else if (type == 2) {
						DemandDAO demandDAO = DemandDAO.getInstance();
						Demand demand = new Demand();
						MonthDAO monthDAO = MonthDAO.getInstance();
						Month monthInfo = new Month();
						Managed managed = new Managed();
						try {
							// 接続
							monthDAO.dbConnect(logger);
							// ステートメント作成
							monthDAO.createSt();
							// 対象月のチェック状況を取得
							monthDAO.searchDemandCheck(month, monthInfo);
							// 対象月のチェック状況をセット
							mapMsg.put("check", "" + monthInfo.getCheck(1));
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}

						try {
							// 接続
							demandDAO.dbConnect(logger);
							// ステートメント作成
							demandDAO.createSt();
							// 各ユーザーの経費の情報を計算
							for (int i = 1; i <= staff.getNumbersRecords(); i++) {
								demand = new Demand();
								demandDAO.searchDemand(staff.getStaffId(i), month, year, demand);
								managed.setName(i, staff.getStaffName(i));
								managed.setId(i, staff.getStaffId(i));
								managed.setTransportation(i, demand.getTransportation());
								managed.setExpendables(i, demand.getExpendables());
								managed.setMeeting(i, demand.getMeeting());
								managed.setCommunications(i, demand.getCommunications());
								managed.setCommission(i, demand.getCommission());
								managed.setSocial(i, demand.getSocial());
								managed.setOther(i, demand.getOther());
								managed.setSum(i, demand.getSum());
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
							errorCheck = 1;
						} finally {
							demandDAO.dbDiscon();
						}
						// 各ユーザーの経費の情報をセット
						for (int i = 1; i <= staff.getNumbersRecords(); i++) {
							mapMsg.put("column1_" + i, "" + managed.getName(i));
							mapMsg.put("column2_" + i, "" + managed.getSum(i));
							mapMsg.put("column3_" + i, "" + managed.getTransportation(i));
							mapMsg.put("column4_" + i, "" + managed.getExpendables(i));
							mapMsg.put("column5_" + i, "" + managed.getMeeting(i));
							mapMsg.put("column6_" + i, "" + managed.getCommunications(i));
							mapMsg.put("column7_" + i, "" + managed.getCommission(i));
							mapMsg.put("column8_" + i, "" + managed.getSocial(i));
							mapMsg.put("column9_" + i, "" + managed.getOther(i));
							mapMsg.put("column10_" + i, "" + managed.getId(i));
						}
						mapMsg.put("numbersAplication", "" + staff.getNumbersRecords());
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

						// type:3 スキルシート情報 type:4 社員情報
					} else if (type == 3 || type == 4) {
						for (int i = 1; i <= staff.getNumbersRecords(); i++) {
							mapMsg.put("column1_" + i, "" + staff.getStaffName(i));
							mapMsg.put("column9_" + i, "" + staff.getPosition(i));
							mapMsg.put("column10_" + i, "" + staff.getStaffId(i));
						}
						mapMsg.put("numbersAplication", "" + staff.getNumbersRecords());
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
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[SearchManaged.java]" + e.toString(), e.getMessage());
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