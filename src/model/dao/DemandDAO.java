package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import model.entity.Demand;

/**
 * demandテーブルと繋ぐDAOクラス。
 */
public class DemandDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static DemandDAO instance = new DemandDAO();

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
	private DemandDAO() {
	}

	/**
	 * @return DemandDAOの唯一のインスタンス
	 * 唯一のインスタンスを取得する。
	 */
	public static DemandDAO getInstance() {
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
	 * @param demandId - 更新する請求書のレコードID
	 * @param month - 月
	 * @param year - 年
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 例外等が発生しなければtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Idとyearとmonthから請求書レコードを取得する。
	 */
	public boolean searchDemand(int demandId, int month, int year, Demand demand)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchDemandChkFlag = false;

		// Demand_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Demand where Demand_Id='"
				+ demandId + "' and Target_Year='" + year + "' and Target_Month='" + month + "' order by Target_Day;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのDemandに取得したデータをセットしていく
		 */
		int index = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = new Date();
		Date lastDay = new Date();
		while (rs.next()) {
			demand.setTargetDay(index, rs.getInt("Target_Day"));
			demand.setAplicationMonth(index, rs.getInt("Aplication_Month"));
			demand.setAplicationDay(index, rs.getInt("Aplication_Day"));
			demand.setAplicationAmount(index, rs.getInt("Aplication_Amount"));
			demand.setTo(index, rs.getString("To"));
			demand.setDescription(index, rs.getString("Description"));
			demand.setDivision(index, rs.getInt("Division"));
			demand.setRemarks(index, rs.getString("Remarks"));
			demand.setFlag(index, rs.getInt("Demand_Flg"));
			demand.setRecordId(index, rs.getInt("Demand_No"));
			demand.setMulti(index, rs.getInt("multi"));
			demand.setRecipt(index, rs.getInt("recipt"));
			demand.setMemo(index, rs.getString("memo"));
			try {
				day = sdf.parse(rs.getString("Upd_Dt"));
				if (index == 1) {
					lastDay = sdf.parse(rs.getString("Upd_Dt"));
				}
				if (day.after(lastDay)) {
					lastDay = sdf.parse(rs.getString("Upd_Dt"));
				}
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			index++;
		}
		demand.setTransportation();
		demand.setExpendables();
		demand.setMeeting();
		demand.setCommunications();
		demand.setCommission();
		demand.setSocial();
		demand.setOther();
		demand.setSum();
		demand.setLastUpdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastDay));
		demand.setNumbersRecords(index - 1);
		searchDemandChkFlag = true;

		return searchDemandChkFlag;
	}

	/**
	 * @param demandId - 更新対象請求書レコードID
	 * @param updateId - 更新するユーザーのレコードID
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 請求書レコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noにマッチする請求書レコードを更新する。
	 */
	public boolean updateDemand(int demandId, int updateId, int year, int month, Demand demand)
			throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * Demand_Noがマッチした請求書レコードを更新する。
		 * レコードが存在しない場合は新たにインサートする。
		 */
		String sql = "";
		int index = demand.getNumbersRecords();
		int num = 0;
		ResultSet rs;
		for (int i = 1; i <= index; i++) {
			sql = "select * from Demand where Demand_No='" + demand.getRecordId(i) + "';";
			rs = st.executeQuery(sql);
			if (rs.next()) {
				sql = "update Demand set Target_Day = '" + demand.getTargetDay(i) + "', Aplication_Month = '"
						+ demand.getAplicationMonth(i) + "', Aplication_Day = '" + demand.getAplicationDay(i)
						+ "',Aplication_Amount = '" + demand.getAplicationAmount(i) + "',multi = '" + demand.getMulti(i)
						+ "',recipt = '" + demand.getRecipt(i) + "',`To` = '" + demand.getTo(i) + "',Description = '"
						+ demand.getDescription(i) + "',Division = '" + demand.getDivision(i) + "',Remarks = '"
						+ demand.getRemarks(i) + "',memo = '" + demand.getMemo(i) + "',Demand_Flg = '"
						+ demand.getFlag(i) + "',Upd_Us = '" + updateId + "' where Demand_No='"
						+ demand.getRecordId(i) + "';";
			} else {
				sql = "insert into Demand value(DEFAULT,'" + demandId + "','" + year + "','" + month + "','"
						+ demand.getAplicationMonth(i) + "','" + demand.getTargetDay(i) + "','"
						+ demand.getAplicationDay(i) + "','" + demand.getAplicationAmount(i) + "','"
						+ demand.getMulti(i) + "','" + demand.getRecipt(i) + "','" + demand.getTo(i) + "','"
						+ demand.getDescription(i) + "','" + demand.getDivision(i) + "','" + demand.getRemarks(i)
						+ "',DEFAULT,'" + demand.getFlag(i) + "','" + updateId + "',DEFAULT,DEFAULT,DEFAULT);";
			}
			System.out.println(sql);
			num = st.executeUpdate(sql);
			if (num == 0) {
				updateDemandChkFlag = false;
			}
		}

		return updateDemandChkFlag;
	}

	/**
	 * @param demandId - 更新対象請求書レコードID
	 * @param updateId - 更新するユーザーのレコードID
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 請求書レコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noにマッチする請求書レコードを更新する。
	 */
	public boolean updateDemand(int updateId, int year, int month) throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * 対象月の請求書フラッグを全て更新する。
		 */
		String sql = "";
		int num = 0;
		sql = "update Demand set Demand_Flg = '1',Upd_Us = '" + updateId + "' where Target_Year='" + year
				+ "' and Target_Month='" + month + "';";
		num = st.executeUpdate(sql);

		return updateDemandChkFlag;
	}

	/**
	 * @param demandId - 更新対象請求書レコードID
	 * @param updateId - 更新するユーザーのレコードID
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 請求書レコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noがマッチした請求書レコードを削除する。
	 */
	public boolean deleteDemand(int demandId) throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * Demand_Noがマッチした請求書レコードを削除する。
		 */
		String sql = "";
		int num = 0;
		sql = "delete from Demand where Demand_No = '" + demandId + "';";
		num = st.executeUpdate(sql);

		return updateDemandChkFlag;
	}
}
