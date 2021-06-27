package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import model.entity.PTO;
import model.entity.Staff;

/**
 * PTOテーブルと繋ぐDAOクラス
 */
public class PTODAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static PTODAO instance = new PTODAO();

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
	private PTODAO() {
	}

	/**
	 * @return PTODAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static PTODAO getInstance() {
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
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return レコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 個人の年間の有給情報を取得する。
	 */
	public boolean searchPTOPersonal(int staffId, int year, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "SELECT * FROM PTO where Staff_Id='" + staffId + "' and Target_Year='" + year
				+ "' ORDER BY Target_Month ASC, Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setRequestYear(index, Integer.parseInt(rs.getString("Request_Year")));
			pto.setRequestMonth(index, Integer.parseInt(rs.getString("Request_Month")));
			pto.setRequestDay(index, Integer.parseInt(rs.getString("Request_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
			pto.setReason(index, rs.getString("Reason"));
		}
		pto.setNumbersRecords(index);
		pto.setWait();

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		sql = "SELECT COUNT(PTO_Id) AS 'NUMBER' FROM PTO where Staff_Id='" + staffId + "' and Request_Year='" + year
				+ "' GROUP BY Request_Year, Request_Month, Request_Day ORDER BY Request_Month ASC, Request_Day ASC;";
		rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		index = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			index++;
			pto.setNUMBER(index, rs.getInt("NUMBER"));
		}

		sql = "select * from PTO where Staff_Id='" + staffId + "' ORDER BY Upd_Dt DESC LIMIT 1;";
		rs = st.executeQuery(sql);

		if (rs.next()) {
			pto.setLastUpdate(1, rs.getString("Upd_Dt"));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 年間の取得日(対象日)を取得する。
	 */
	public boolean searchPTOExcel(int staffId, int year, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "SELECT * FROM PTO WHERE Flag='1' and Staff_Id='" + staffId
				+ "' and Target_Year='" + year
				+ "' ORDER BY Target_Month ASC, Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setRequestYear(index, Integer.parseInt(rs.getString("Request_Year")));
			pto.setRequestMonth(index, Integer.parseInt(rs.getString("Request_Month")));
			pto.setRequestDay(index, Integer.parseInt(rs.getString("Request_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
			if (rs.getInt("Division") == 1) {
				pto.setPTONumber(index, 1);
			} else {
				pto.setPTONumber(index, 0.5);
			}
		}
		pto.setNumbersRecords(index);

		sql = "select * from PTO where Staff_Id='" + staffId + "' ORDER BY Upd_Dt DESC LIMIT 1;";
		rs = st.executeQuery(sql);

		if (rs.next()) {
			pto.setLastUpdate(1, rs.getString("Upd_Dt"));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return 年間での申請日を取得ができればtrue、できない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 年間での申請日を取得する。
	 */
	public boolean searchPTO(int staffId, int year, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "SELECT * FROM PTO WHERE PTO_Id in(select max(PTO_Id) from PTO where  Flag='1' and Staff_Id='"
				+ staffId + "' and Target_Year='" + year
				+ "' GROUP BY Request_Year, Request_Month, Request_Day) ORDER BY Request_Month ASC, Request_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		double number = 0;
		double ptoNumber = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			ptoNumber = 0;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setRequestYear(index, Integer.parseInt(rs.getString("Request_Year")));
			pto.setRequestMonth(index, Integer.parseInt(rs.getString("Request_Month")));
			pto.setRequestDay(index, Integer.parseInt(rs.getString("Request_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
			if (rs.getInt("Division") == 1) {
				number++;
			} else {
				number += 0.5;
			}
		}
		pto.setNumbersRecords(index);
		pto.setNUMBER(1, number);

		sql = "select * from PTO where Staff_Id='" + staffId + "' ORDER BY Upd_Dt DESC LIMIT 1;";
		rs = st.executeQuery(sql);

		if (rs.next()) {
			pto.setLastUpdate(1, rs.getString("Upd_Dt"));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return 年間取得日数を取得できればtrue、できない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 年間取得日数を取得する。
	 */
	public boolean searchPTOTarget(int staffId, int year, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "SELECT * FROM PTO where Flag='1' and Staff_Id='" + staffId + "' and Target_Year='" + year
				+ "' ORDER BY Target_Month ASC, Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		double number = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setRequestYear(index, Integer.parseInt(rs.getString("Request_Year")));
			pto.setRequestMonth(index, Integer.parseInt(rs.getString("Request_Month")));
			pto.setRequestDay(index, Integer.parseInt(rs.getString("Request_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
			if (rs.getInt("Division") == 1) {
				number++;
			} else {
				number += 0.5;
			}
		}
		pto.setNumbersRecords(index);
		pto.setNUMBER(1, number);

		sql = "select * from PTO where Staff_Id='" + staffId + "' ORDER BY Upd_Dt DESC LIMIT 1;";
		rs = st.executeQuery(sql);

		if (rs.next()) {
			pto.setLastUpdate(1, rs.getString("Upd_Dt"));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return staffIdとyear,monthに該当するPTOのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * staffIdとyear,monthに該当するPTOのレコードを取得する。
	 */
	public double searchPTOTotal(int staffId, int year) throws SQLException, NoSuchAlgorithmException {

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "SELECT * FROM PTO where Flag='1' and Staff_Id='" + staffId + "' and Target_Year<'" + year
				+ "' ORDER BY Request_Month ASC, Request_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		double index = 0;
		while (rs.next()) {
			if (rs.getInt("Division") == 1) {
				index++;
			} else {
				index += 0.5;
			}
		}

		return index;
	}

	/**
	 * @param ptoId - PTOのレコードID
	 * @param staff - staffのモデルクラスのインスタンス
	 * @return ptoIdに該当するPTOのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * ptoIdに該当するPTOのレコードを取得する。
	 */
	public boolean searchPTO(int ptoId, Staff staff, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select * from PTO where PTO_Id='" + ptoId + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchPTOChkFlag = true;
			index++;
			staff.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param ptoId - PTOのレコードID
	 * @param staff - staffのモデルクラスのインスタンス
	 * @return ptoIdに該当するPTOのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * ptoIdに該当するPTOのレコードを取得する。
	 */
	public boolean searchPTOId(int ptoId, PTO pto, int year, int month, int day, int index)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select * from PTO where Staff_Id='" + ptoId + "' and Target_Year='" + year
				+ "' and Target_Month='" + month + "' and Target_Day='" + day + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		if (rs.next()) {
			searchPTOChkFlag = true;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
		}

		return searchPTOChkFlag;
	}

	/**
	 * @param staffId - staffのレコードID
	 * @param year - 年
	 * @param month - 月
	 * @param pto - ptoのモデルクラスのインスタンス
	 * @return データベースに現在以降の承認済みptoのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 現在以降の承認済みptoのレコードを取得する。
	 */
	public boolean searchPTOConsume(int staffId, int year, int month, PTO pto)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOConsumeChkFlag = false;
		month += 6;
		if (month > 12) {
			month -= 12;
		}

		int startYear = 0;
		int endYear = 0;
		String sql = "";
		int index = 0;
		if (month > LocalDateTime.now().getMonthValue()) {
			startYear = year - 1;
			endYear = year;
		} else {
			startYear = year;
			endYear = year + 1;
		}

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		sql = "select * from PTO where Staff_Id='" + staffId + "'and Flag='1' and Target_Year='" + startYear
				+ "' and Target_Month>=" + month + " ORDER BY Target_Month ASC, Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			searchPTOConsumeChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
		}
		sql = "select * from PTO where Staff_Id='" + staffId + "'and Flag='1' and Target_Year='" + endYear
				+ "' and Target_Month<" + month + " ORDER BY Target_Month ASC, Target_Day ASC;";
		rs = st.executeQuery(sql);
		while (rs.next()) {
			searchPTOConsumeChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
		}
		pto.setNumbersRecords(index);

		return searchPTOConsumeChkFlag;
	}

	/**
	 * @param staffId - staffのレコードID
	 * @param pto - ptoのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当するptoのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 現在以降の承認済みptoのレコードを取得する。
	 */
	public boolean searchPTOConsume(int staffId, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOConsumeChkFlag = false;

		String sql = "";
		int index = 0;
		int month = LocalDateTime.now().getMonthValue();
		int year = LocalDateTime.now().getYear();

		int startYear = year;
		int endYear = year + 1;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		sql = "select * from PTO where Staff_Id='" + staffId + "'and Flag='1' and Target_Year='" + startYear
				+ "' and Target_Month>=" + month + " ORDER BY Target_Month ASC, Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			searchPTOConsumeChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
		}
		sql = "select * from PTO where Staff_Id='" + staffId + "'and Flag='1' and Target_Year='" + endYear
				+ "' and Target_Month<" + month + " ORDER BY Target_Month ASC, Target_Day ASC;";
		rs = st.executeQuery(sql);
		while (rs.next()) {
			searchPTOConsumeChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
		}
		pto.setNumbersRecords(index);

		return searchPTOConsumeChkFlag;
	}

	/**
	 * @param staffId - staffのレコードID
	 * @param pto - ptoのモデルクラスのインスタンス
	 * @return レコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 申請済みptoのレコードを取得する。
	 */
	public boolean searchPTOAlready(int staffId, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchPTOConsumeChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select COUNT(PTO_Id) AS 'NUMBER' from PTO where Staff_Id='" + staffId
				+ "'and Flag>='0' and Flag<='1' and Target_Year='" + pto.getTargetYear(1) + "' and Target_Month='"
				+ pto.getTargetMonth(1) + "' and Target_Day='" + pto.getTargetDay(1) + "';";
		ResultSet rs = st.executeQuery(sql);

		while (rs.next()) {
			searchPTOConsumeChkFlag = rs.getInt("NUMBER") == 0 ? false : true;
		}

		return searchPTOConsumeChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return レコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象年を対象とした有給のレコードを取得する。
	 */
	public boolean searchAllPTO(int year, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchAllPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select * from PTO where Target_Year='" + year
				+ "' ORDER BY Staff_Id ASC,Flag ASC,Request_Month ASC,Request_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchAllPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
			pto.setReason(index, rs.getString("Reason"));
		}
		pto.setNumbersRecords(index);
		pto.setWait();
		pto.setDone();

		return searchAllPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当するptoのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全てのPTOのレコードを取得する。
	 */
	public boolean searchAllPTO(PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean searchAllPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select * from PTO ORDER BY Staff_Id ASC,Flag ASC,Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchAllPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
		}
		pto.setNumbersRecords(index);
		pto.setWait();
		pto.setDone();

		return searchAllPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return レコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象年に申請された全てのPTOのレコードを取得する。
	 */
	public boolean searchAllPTO(PTO pto, int year) throws SQLException, NoSuchAlgorithmException {

		boolean searchAllPTOChkFlag = false;

		// staffIdとyear,monthに該当するPTOのレコードを取得する
		String sql = "select * from PTO where Request_Year='" + year
				+ "' ORDER BY Staff_Id ASC,Flag ASC,Target_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのPTOに取得したデータをセットしていく
		 */
		int index = 0;
		while (rs.next()) {
			searchAllPTOChkFlag = true;
			index++;
			pto.setPTOId(index, Integer.parseInt(rs.getString("PTO_Id")));
			pto.setStaffId(index, Integer.parseInt(rs.getString("Staff_Id")));
			pto.setTargetYear(index, Integer.parseInt(rs.getString("Target_Year")));
			pto.setTargetMonth(index, Integer.parseInt(rs.getString("Target_Month")));
			pto.setTargetDay(index, Integer.parseInt(rs.getString("Target_Day")));
			pto.setDivision(index, Integer.parseInt(rs.getString("Division")));
			pto.setFlag(index, Integer.parseInt(rs.getString("Flag")));
		}
		pto.setNumbersRecords(index);
		pto.setWait();
		pto.setDone();

		return searchAllPTOChkFlag;
	}

	/**
	 * @param ptoId - PTOのレコードID
	 * @param month - 月
	 * @param year - 年
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return ptoのレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたptoIdにマッチする掲示板のレコードを更新する。
	 */
	public boolean updatePTO(int updateId, int ptoId, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean updatePTOChkFlag = true;

		// boardIdがマッチしたptoのレコードを更新する
		String sql = "";
		int num = 0;
		sql = "update PTO set Flag = '1',Permit_Dt = '" + LocalDateTime.now() + "',Upd_Us = \"" + updateId
				+ "\" where PTO_Id='" + ptoId + "';";
		num = st.executeUpdate(sql);
		if (num == 0) {
			updatePTOChkFlag = false;
		}

		return updatePTOChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param ptoId - PTOのレコードID
	 * @return ptoのレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたptoIdにマッチするptoのレコードを更新する。
	 */
	public boolean permitPTO(int updateId, int ptoId) throws SQLException, NoSuchAlgorithmException {

		boolean permitPTOChkFlag = true;

		// ptoIdがマッチしたptoのレコードを更新する
		String sql = "";
		int num = 0;
		sql = "update PTO set Flag = '1',Permit_Dt = '" + LocalDateTime.now() + "',Upd_Us = \"" + updateId
				+ "\" where PTO_Id='" + ptoId + "';";
		num = st.executeUpdate(sql);
		if (num == 0) {
			permitPTOChkFlag = false;
		}

		return permitPTOChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param ptoId - PTOのレコードID
	 * @param type - 取り消す種類
	 * @return ptoのレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたptoIdにマッチするptoのレコードを更新する。
	 */
	public boolean cancelPTO(int updateId, int ptoId, int type) throws SQLException, NoSuchAlgorithmException {

		boolean cancelPTOChkFlag = true;

		String sql = "";
		int num = 0;
		if (type == 1) {
			// ptoIdがマッチしたptoのレコードを削除する
			sql = "delete from PTO where PTO_Id='" + ptoId + "';";
		} else {
			// ptoIdがマッチしたptoのレコードを更新する
			sql = "update PTO set Flag = '3',Permit_Dt = '" + LocalDateTime.now() + "',Upd_Us = \"" + updateId
					+ "\" where PTO_Id='" + ptoId + "';";
		}

		num = st.executeUpdate(sql);
		if (num == 0) {
			cancelPTOChkFlag = false;
		}

		return cancelPTOChkFlag;
	}

	/**
	 * @param pto - PTOのモデルクラスのインスタンス
	 * @return ptoのレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * PTOのレコードを新規作成する。
	 */
	public boolean insertPTO(int staffId, PTO pto) throws SQLException, NoSuchAlgorithmException {

		boolean insertPTOChkFlag = false;

		// ptoのレコードを作成する
		String sql = "";
		sql = "insert into PTO value(DEFAULT,'" + staffId + "','" + pto.getRequestYear(1) + "','"
				+ pto.getRequestMonth(1) + "','" + pto.getRequestDay(1) + "','" + pto.getTargetYear(1) + "','"
				+ pto.getTargetMonth(1) + "','" + pto.getTargetDay(1) + "','" + pto.getReason(1) + "','"
				+ pto.getDivision(1) + "',DEFAULT,DEFAULT,'" + staffId + "',DEFAULT,DEFAULT,DEFAULT);";
		int rs = st.executeUpdate(sql);

		if (rs > 0) {
			pto.setPTOId(1, rs);
			insertPTOChkFlag = true;
		}
		return insertPTOChkFlag;
	}

}
