package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.ExcelController;
import model.dao.AttendanceDAO;
import model.dao.DemandDAO;
import model.dao.HolidayDAO;
import model.dao.StaffDAO;
import model.entity.Attendance;
import model.entity.Demand;
import model.entity.Holiday;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Servlet implementation class OutputExcel
 * Excelにファイル出力するクラス。
 */
@WebServlet("/OutputExcel")
public class OutputExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対してユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("home.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * セッション情報から対象ユーザーIDと対象月を削除する。
	 * フラッグの判定により自動処理の設定を行う。
	 * 勤務表や請求書、スキルシート内容をまとめCSV出力する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String jsp="error.jsp";
		int errorCheck=0;
		if(session.getAttribute("loginUserId")==null) {
			jsp="session_out.jsp";
			errorCheck=2;
		}else {

			// ロガーを取得してログレベルをINFOに設定
			Logger logger = Logger.getLogger(OutputExcel.class.getName());
			logger.setLevel(Level.INFO);

			// ハンドラーを作成してロガーに登録
			Handler handler = new FileHandler(Properties.LOG_PATH+session.getAttribute("loginUserId")+"_webApp.log",true);
			logger.addHandler(handler);

			// フォーマッターを作成してハンドラーに登録
			Formatter formatter =  new SimpleFormatter();
			handler.setFormatter(formatter);

			try {
				int year = LocalDateTime.now().getYear();
				String all = request.getParameter("all");

				//JSONマップ
				Map<String, String> mapMsg = new HashMap<String, String>();

				int type = Integer.parseInt(request.getParameter("type"));
				int month=0;
				String monthText="";
				// 有給台帳以外の場合
				if(type!=3) {
					month = Integer.parseInt(request.getParameter("month"));
					if(month<10) {
						monthText = "0"+month;
					}else {
						monthText = ""+month;
					}
				}

				if(LocalDate.now().getMonthValue()==1&&month==12){
					year--;
				}

				StaffDAO staffDAO = StaffDAO.getInstance();
				Staff staff = new Staff();

				logger.log(Level.INFO, "[OutputExcel.java]/ID"+request.getParameter("id"));

				try {
					// 接続
					staffDAO.dbConnect(logger);
					// ステートメント作成
					staffDAO.createSt();
					// 全員分または個人のユーザー情報を取得
					if(all.contentEquals("yes")) {
						staffDAO.searchAllUser(staff);
					}else {
						staffDAO.searchUser(Integer.parseInt(request.getParameter("id")), staff);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
					errorCheck=1;
				} finally {
					staffDAO.dbDiscon();
				}
				// 勤務表のExcel出力
				if(type==1) {
					HolidayDAO holidayDAO = HolidayDAO.getInstance();

					Holiday holiday = new Holiday();

					if(month!=6&&month!=10&&month!=12) {
						try {
							// 接続
							holidayDAO.dbConnect(logger);
							// ステートメント作成
							holidayDAO.createSt();

							holidayDAO.setHoliday(year, month, holiday);

						} catch (Exception e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
							errorCheck=1;
						} finally {
							holidayDAO.dbDiscon();
						}
					}

					AttendanceDAO attendanceDAO = AttendanceDAO.getInstance();
					Attendance attendance = new Attendance();

					try {
						// 接続
						attendanceDAO.dbConnect(logger);
						// ステートメント作成
						attendanceDAO.createSt();
						// 全員分のファイルをzip形式で出力
						if(all.contentEquals("yes")) {
							File[] files = new File[staff.getNumbersRecords()];
							for(int i=1; i<=staff.getNumbersRecords(); i++) {
								attendance = new Attendance();
								attendanceDAO.searchAttendance(staff.getStaffId(i), month, year, attendance ,holiday);
								ExcelController excel = new ExcelController();
								excel.output(year, month, attendance, staff, i, logger);
								files[i-1]= new File(Properties.SAVE_DIR_PATH+staff.getStaffId(i)+"/rigare_work_"+staff.getLastNameEnglish(i)+staff.getFirstNameEnglish(i)+"_"+year+"."+month+".xls");
							}
							ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(Properties.SAVE_DIR_PATH+"attendance/"+year+monthText+"_attendance.zip"))));
							try {
								byte[] buf = new byte[1024];
								InputStream is = null;
								try {
									for (File file : files) {
										ZipEntry entry = new ZipEntry(file.getName());
										logger.log(Level.INFO, "[OutputExcel.java]/output/file.getName() : "+file.getName());
										zos.putNextEntry(entry);
										is = new BufferedInputStream(new FileInputStream(file));
										int len = 0;
										while ((len = is.read(buf)) != -1) {
											zos.write(buf, 0, len);
										}
									}
								} finally {
									IOUtils.closeQuietly(is);
								}
							} catch (IOException e) {
								e.printStackTrace();
								logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
								errorCheck=1;
							} finally {
								IOUtils.closeQuietly(zos);
							}
							mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+"attendance/"+year+monthText+"_attendance.zip");
							logger.log(Level.INFO, "[OutputExcel.java]/output/fileName : "+"AttendanceRigare/resources/attendance/"+year+monthText+"_attendance.zip");
						}else {
							attendanceDAO.searchAttendance(staff.getStaffId(1), month, year, attendance ,holiday);
							ExcelController excel = new ExcelController();
							excel.output(year, month, attendance, staff, 1, logger);
							logger.log(Level.INFO, "[OutputExcel.java]/output/loginUser : "+session.getAttribute("loginUserId").toString()+"/fileUser : "+staff.getStaffId(1)+"/fileType : work");
							mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+staff.getStaffId(1)+"/rigare_work_"+staff.getLastNameEnglish(1)+staff.getFirstNameEnglish(1)+"_"+year+"."+month+".xls");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						attendanceDAO.dbDiscon();
					}
				}

				// 請求書のExcel出力
				if(type==2) {
					DemandDAO demandDAO = DemandDAO.getInstance();
					Demand demand = new Demand();

					try {
						// 接続
						demandDAO.dbConnect(logger);
						// ステートメント作成
						demandDAO.createSt();
						// 全員分のファイルをzip形式で出力
						if(all.contentEquals("yes")) {
							File[] files = new File[staff.getNumbersRecords()];
							for(int i=1; i<=staff.getNumbersRecords(); i++) {
								demand = new Demand();
								demandDAO.searchDemand(staff.getStaffId(i), month, year, demand);
								ExcelController excel = new ExcelController();
								excel.output(year, month, demand, staff, i, logger);
								files[i-1]= new File(Properties.SAVE_DIR_PATH+staff.getStaffId(i)+"/rigare_bill_"+staff.getLastNameEnglish(i)+staff.getFirstNameEnglish(i)+"_"+year+"."+month+".xls");
							}
							ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(Properties.SAVE_DIR_PATH+"demand/"+year+monthText+"_demand.zip"))));
							try {
								byte[] buf = new byte[1024];
								InputStream is = null;
								try {
									for (File file : files) {
										ZipEntry entry = new ZipEntry(file.getName());
										logger.log(Level.INFO, "[OutputExcel.java]/output/file.getName() : "+file.getName());
										zos.putNextEntry(entry);
										is = new BufferedInputStream(new FileInputStream(file));
										int len = 0;
										while ((len = is.read(buf)) != -1) {
											zos.write(buf, 0, len);
										}
									}
								} finally {
									IOUtils.closeQuietly(is);
								}
							} catch (IOException e) {
								e.printStackTrace();
								logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
								errorCheck=1;
							} finally {
								IOUtils.closeQuietly(zos);
							}
							mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+"demand/"+year+monthText+"_demand.zip");
							logger.log(Level.INFO, "[OutputExcel.java]/output/fileName : "+"AttendanceRigare/resources/demand/"+year+monthText+"_demand.zip");
						}else {
							demandDAO.searchDemand(staff.getStaffId(1), month, year, demand);
							ExcelController excel = new ExcelController();
							excel.output(year, month, demand, staff, 1, logger);
							logger.log(Level.INFO, "[OutputExcel.java]/output/loginUser : "+session.getAttribute("loginUserId").toString()+"/fileUser : "+staff.getStaffId(1)+"/fileType : bill");
							mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+staff.getStaffId(1)+"/rigare_bill_"+staff.getLastNameEnglish(1)+staff.getFirstNameEnglish(1)+"_"+year+"."+month+".xls");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
						errorCheck=1;
					} finally {
						demandDAO.dbDiscon();
					}
				}

				// 有給台帳のExcel出力
				if(type==3) {
					year = Integer.parseInt(request.getParameter("year"));
					// 全員分のファイルをzip形式で出力
					if(all.contentEquals("yes")) {
						File[] files = new File[staff.getNumbersRecords()];
						for(int i=1; i<=staff.getNumbersRecords(); i++) {
							ExcelController excel = new ExcelController();
							excel.output(year, month, staff, i, logger);
							files[i-1]= new File(Properties.SAVE_DIR_PATH+staff.getStaffId(i)+"/rigare_pto_"+staff.getLastNameEnglish(i)+staff.getFirstNameEnglish(i)+"_"+year+".xls");
						}
						ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(new File(Properties.SAVE_DIR_PATH+"demand/"+year+"_pto.zip"))));
						try {
							byte[] buf = new byte[1024];
							InputStream is = null;
							try {
								for (File file : files) {
									ZipEntry entry = new ZipEntry(file.getName());
									logger.log(Level.INFO, "[OutputExcel.java]/output/file.getName() : "+file.getName());
									zos.putNextEntry(entry);
									is = new BufferedInputStream(new FileInputStream(file));
									int len = 0;
									while ((len = is.read(buf)) != -1) {
										zos.write(buf, 0, len);
									}
								}
							} finally {
								IOUtils.closeQuietly(is);
							}
						} catch (IOException e) {
							e.printStackTrace();
							logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
							errorCheck=1;
						} finally {
							IOUtils.closeQuietly(zos);
						}
						mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+"pto/"+year+monthText+"_demand.zip");
						logger.log(Level.INFO, "[OutputExcel.java]/output/fileName : "+"AttendanceRigare/resources/pto/"+year+"_pto.zip");
					}else {
						ExcelController excel = new ExcelController();
						excel.output(year, month, staff, 1, logger);
						logger.log(Level.INFO, "[OutputExcel.java]/output/loginUser : "+session.getAttribute("loginUserId").toString()+"/fileUser : "+staff.getStaffId(1)+"/fileType : pto");
						mapMsg.put("url", Properties.SAVE_DIR_URL_PATH+staff.getStaffId(1)+"/rigare_pto_"+staff.getLastNameEnglish(1)+staff.getFirstNameEnglish(1)+"_"+year+".xls");
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
				logger.log(Level.WARNING, "[OutputExcel.java]"+e.toString(), e.getMessage());
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