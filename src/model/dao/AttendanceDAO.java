package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

import controller.DateTimeController;
import model.entity.Attendance;
import model.entity.Holiday;

/**
 * attendanceテーブルと繋ぐDAOクラス。
 */
public class AttendanceDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static AttendanceDAO instance = new AttendanceDAO();

	/**
	 * 特定のデータベースとの接続(セッション)
	 */
	private Connection con;
	/**
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト
	 */
	private Statement st;
	/**
	 * シングルトンのため新規のインスタンスをつくらせない
	 */
	private AttendanceDAO() {
	}

	/**
	 * @return AttendanceDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static AttendanceDAO getInstance() {
		return instance;
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 特定のデータベースとの接続(セッション)を生成する。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void dbConnect(Logger logger) throws SQLException, SecurityException, IOException {
		ConnectionManager cm = ConnectionManager.getInstance();
		con = cm.connect(logger);
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクトを生成する。
	 */
	public void createSt() throws SQLException {
		st = con.createStatement();
	}

	/**
	 * 特定のデータベースとの接続(セッション)を切断する。
	 */
	public void dbDiscon() {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param attendanceId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @param holiday - 休日のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分の勤務表レコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthから勤務表レコードを取得する。
	 */
	public boolean searchAttendance(int attendanceId, int month, int year, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean searchAttendanceChkFlag = false;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Attendance where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Division='11';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのAttendanceに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAttendanceChkFlag = true;
			do {
				index++;
				attendance.setDivision(index,rs.getInt("Division"));
				attendance.setBreakTime(index,rs.getString("Break_Time"));
				attendance.setStartTime(index,rs.getString("Start_Time"));
				attendance.setFinishTime(index,rs.getString("Finish_Time"));
				attendance.setRemarks(index,rs.getString("Remarks"));
				attendance.setMemo(index,rs.getString("Memo"));
			}while(rs.next());

		}
		return searchAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @param holiday - 休日のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分の勤務表レコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthから勤務表レコードを取得する。
	 */
	public boolean searchAttendanceTotal(int attendanceId, int month, int year, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean searchAttendanceChkFlag = false;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Attendance where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "'";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのAttendanceに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAttendanceChkFlag = true;
			do {
				index++;
			}while(rs.next());
			attendance.setNumbersRecords(index);
		}
		return searchAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @param holiday - 休日のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分の勤務表レコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthから勤務表レコードを取得する。
	 */
	public boolean searchAttendance(int attendanceId, int month, int year, Attendance attendance, Holiday holiday) throws SQLException, NoSuchAlgorithmException {

		boolean searchAttendanceChkFlag = false;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Attendance where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Division<'10';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのAttendanceに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAttendanceChkFlag = true;
			do {
				index++;
				attendance.setDivision(index,rs.getInt("Division"));
				attendance.setBreakTime(index,rs.getString("Break_Time"));
				attendance.setStartTime(index,rs.getString("Start_Time"));
				attendance.setFinishTime(index,rs.getString("Finish_Time"));
				attendance.setRemarks(index,rs.getString("Remarks"));
				attendance.setMemo(index,rs.getString("Memo"));
				attendance.setWorkHours(index);
			}while(rs.next());
			attendance.setOperatingDays();
			attendance.setOperatingTime();
			attendance.setHoliday();
			attendance.setSubstitute();
			attendance.setPaid();
			attendance.setAbsence();
			attendance.setOverNight();

			sql = "select * from Attendance where Attendance_Id='"
					+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' ORDER BY Upd_Dt DESC LIMIT 1;";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				attendance.setLastUpdate(1, rs.getString("Upd_Dt"));
			}
		}else{
			searchAttendanceChkFlag = insertAttendanceFirstTime(attendanceId, month, year, attendance, holiday);
		}
		return searchAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @param holiday - 休日のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分の勤務表レコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthから勤務表レコードを取得する。
	 */
	public boolean searchInAttendance(int attendanceId, int month, int year, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean searchAttendanceChkFlag = false;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Attendance where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Division='10';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのAttendanceに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAttendanceChkFlag = true;
			do {
				index++;
				attendance.setDay(index,rs.getString("Day"));
				attendance.setDivision(index,Integer.parseInt(rs.getString("Division")));
				attendance.setStartTime(index,rs.getString("Start_Time"));
				attendance.setFinishTime(index,rs.getString("Finish_Time"));
				attendance.setBreakTime(index,rs.getString("Break_Time"));
				attendance.setRemarks(index,rs.getString("Remarks"));
			}while(rs.next());
			attendance.setNumbersRecords(index);
			attendance.setInTime();
		}
		return searchAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @param holiday - 休日のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthから勤務表レコードを１月分作成してattendanceインスタンスにデフォルト値をセットする。
	 */
	public boolean insertAttendanceFirstTime(int attendanceId, int month, int year, Attendance attendance, Holiday holiday) throws SQLException, NoSuchAlgorithmException {

		boolean insertAttendanceFirstTimeChkFlag = false;

		// Atttendance_Idとyearとmonthを基に勤務表レコードを作成する
		String sql = "";
		int days=DateTimeController.getMonthDays(year, month);
		for(int i=1;i<=days;i++) {
			sql = "insert into Attendance value(DEFAULT,'" + attendanceId + "','" + year + "','" + month + "','"+i+"',DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,'" + attendanceId + "',DEFAULT,DEFAULT,DEFAULT);";
			st.executeUpdate(sql);
		}

		//休日と祝日の区分を欠勤に更新する
		LocalDate date = LocalDate.of(year, month, 1);
		int dayOfWeek = date.getDayOfWeek().getValue();
		for(int i=1;i<=days;i++) {
			if ((i - 1) + dayOfWeek > 6) {
				if (((i - 1) + dayOfWeek) % 7 == 0
						|| ((i - 1) + dayOfWeek) % 7 == 6) {
					sql = "update Attendance set Division ='7' ,Break_Time = '00:00', Upd_Us = \""+attendanceId+"\" where Attendance_Id='"+attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='"+i+"';";
					st.executeUpdate(sql);
				}
			} else {
				if ((i - 1) + dayOfWeek == 6) {
					sql = "update Attendance set Division ='7' ,Break_Time = '00:00', Upd_Us = \""+attendanceId+"\" where Attendance_Id='"+attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='"+i+"';";
					st.executeUpdate(sql);
				}
			}
		}
		for(int i=1;i<=holiday.getNumbersHoliday();i++) {
			sql = "update Attendance set Division ='7' ,Break_Time = '00:00', Upd_Us = \""+attendanceId+"\" where Attendance_Id='"+attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='"+holiday.getHoliday(i)+"';";
			st.executeUpdate(sql);
		}

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのAttendanceに取得したデータをセットしていく
		 */
		sql = "select * from Attendance where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		int index=1;
		if (rs.next()) {
			do {
				attendance.setDivision(index,rs.getInt("Division"));
				attendance.setBreakTime(index,rs.getString("Break_Time"));
				attendance.setStartTime(index,rs.getString("Start_Time"));
				attendance.setFinishTime(index,rs.getString("Finish_Time"));
				attendance.setRemarks(index,rs.getString("Remarks"));
				attendance.setMemo(index,rs.getString("Memo"));
				attendance.setWorkHours(index);
				index++;
			}while(rs.next());
			attendance.setOperatingDays();
			attendance.setOperatingTime();
			attendance.setHoliday();
			attendance.setSubstitute();
			attendance.setPaid();
			attendance.setAbsence();
			attendance.setOverNight();
		}
		if(index-1==days) {
			insertAttendanceFirstTimeChkFlag = true;
		}
		return insertAttendanceFirstTimeChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateNormalAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "";
		int days=DateTimeController.getMonthDays(year, month);
		int num=0;
		for(int i=1;i<=days;i++) {
			if(attendance.getDivision(i)!=0) {
				sql = "update Attendance set Start_Time = \""+attendance.getStartTime(i)+"\", Finish_Time = \""+attendance.getFinishTime(i)+"\", Break_Time = \""+attendance.getBreakTime(i)+"\", Operating_Time = \""+attendance.getWorkHours(i)+"\", division = \""+attendance.getDivision(i)+"\", Remarks = \""+attendance.getRemarks(i)+"\", Memo = \""+attendance.getMemo(i)+"\", Upd_Us = \""+updateId+"\" where Attendance_Id='"
						+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='"+i+"' and Division<'10';";
				num = st.executeUpdate(sql);
				if(num==0) {
					updateAttendanceChkFlag = false;
				}
			}
		}

		return updateAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateDirectAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "update Attendance set Start_Time = '"+attendance.getStartTime(1)+"', Finish_Time = '"+attendance.getFinishTime(1)+"', Break_Time = '"+attendance.getBreakTime(1)+"', Remarks = '"+attendance.getRemarks(1)+"', Memo = '"+attendance.getMemo(1)+"', Upd_Us = \""+updateId+"\" where Attendance_Id='"+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='32';";
		int num = st.executeUpdate(sql);
		if(num==0) {
			sql = "insert into Attendance value(DEFAULT,'" + attendanceId + "','" + year + "','" + month + "','32','11','"+attendance.getStartTime(1)+"','"+attendance.getFinishTime(1)+"','"+attendance.getBreakTime(1)+"',DEFAULT,'"+attendance.getRemarks(1)+"','"+attendance.getMemo(1)+"','" + attendanceId + "',DEFAULT,DEFAULT,DEFAULT);";
			st.executeUpdate(sql);
		}

		return updateAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updatePermitPtoAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "update Attendance set Division = "+attendance.getDivision(1)+", Upd_Us = \""+updateId+"\", Start_Time ='00:00' , Finish_Time ='00:00', Break_Time ='01:00', Operating_Time ='00:00' where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='" + day + "';";
		int num = st.executeUpdate(sql);
		if(num==0) {
			updateAttendanceChkFlag = false;
		}

		return updateAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateCancelPtoAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "update Attendance set Division = '1', Upd_Us = \""+updateId+"\", Start_Time ='00:00' , Finish_Time ='00:00', Break_Time ='01:00', Operating_Time ='00:00' where Attendance_Id='"
				+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='" + day + "';";
		int num = st.executeUpdate(sql);
		if(num==0) {
			updateAttendanceChkFlag = false;
		}

		return updateAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateNonTimeEntryAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateInAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "insert into Attendance value(DEFAULT,'" + attendanceId + "','" + year + "','" + month + "','"+day+"','10','"+attendance.getStartTime(1)+"','"+attendance.getFinishTime(1)+"','"+attendance.getWorkHours(1)+"',DEFAULT,'"+attendance.getRemarks(1)+"',DEFAULT,'" + attendanceId + "',DEFAULT,DEFAULT,DEFAULT);";
		int num = st.executeUpdate(sql);

		if(num==0) {
			updateInAttendanceChkFlag = false;
		}

		return updateInAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateNonTimeUpdateAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance, int index) throws SQLException, NoSuchAlgorithmException {

		boolean updateInAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "";
		int num=0;
		for(int i=1; i<=index; i++) {
			sql = "update Attendance set Upd_Us = \""+updateId+"\", Start_Time ='"+attendance.getStartTime(i)+"' , Finish_Time ='"+attendance.getFinishTime(i)+"', Break_Time ='"+attendance.getWorkHours(i)+"', Remarks='"+attendance.getRemarks(i)+"' where Attendance_Id='"+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='" + attendance.getDay(i) + "' and Division='10';";
			num = st.executeUpdate(sql);
		}

		if(num==0) {
			updateInAttendanceChkFlag = false;
		}

		return updateInAttendanceChkFlag;
	}

	/**
	 * @param attendanceId - 更新対象ユーザーID
	 * @param updateId - 更新を行うユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param attendance - 勤務表のモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたAttendance_Idとyearとmonthにマッチする勤務表レコードを更新する。
	 */
	public boolean updateNonTimeDeleteAttendance(int attendanceId, int updateId, int year, int month, int day, Attendance attendance) throws SQLException, NoSuchAlgorithmException {

		boolean updateInAttendanceChkFlag = true;

		// Atttendance_Idとyearとmonthがマッチした勤務表レコードを更新する
		String sql = "delete from  Attendance where Attendance_Id='"+ attendanceId + "' and Year='" + year + "' and Month='" + month + "' and Day='" + day + "' and Division='10';";
		int num = st.executeUpdate(sql);

		if(num==0) {
			updateInAttendanceChkFlag = false;
		}

		return updateInAttendanceChkFlag;
	}

}
