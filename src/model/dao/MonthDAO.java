package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Month;

/**
 * monthテーブルと繋ぐDAOクラス
 */
public class MonthDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static MonthDAO instance = new MonthDAO();

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
	private MonthDAO() {
	}

	/**
	 * @return HolidayDAOの唯一のインスタンス
	 * 唯一のインスタンスを取得する。
	 */
	public static MonthDAO getInstance() {
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
	 * @param month - 月
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 例外等が発生しなければtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたmonthから請求書チェック状況を取得する。
	 */
	public void searchDemandCheck(int month, Month monthInfo) throws SQLException, NoSuchAlgorithmException {

		// monthがマッチした請求書チェック状況を取得する
		String sql = "select * from MonthInfo where `Month`='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのDemandに取得したデータをセットしていく
		 */
		if (rs.next()) {
			monthInfo.setCheck(1, rs.getInt("DemandCheckFlag"));
		}
	}

	/**
	 * @param month - 月
	 * @param demand - 請求書のモデルクラスのインスタンス
	 * @return 例外等が発生しなければtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたmonthから請求書チェック状況を取得する。
	 */
	public int searchNormalDay(int month) throws SQLException, NoSuchAlgorithmException {

		// monthがマッチした請求書チェック状況を取得する
		String sql = "select * from MonthInfo where `Month`='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			return rs.getInt("NormalDay");
		}
		return 0;
	}

	/**
	 * @param month - 対象月
	 * @return 請求書チェックの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noにマッチする請求書チェック状態を更新する。
	 */
	public boolean updateDemandCheck(int month) throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * 対象月の請求書フラッグを更新する。
		 */
		String sql = "";
		sql = "update MonthInfo set DemandCheckFlag = '1' where `Month`='" + month + "';";
		st.executeUpdate(sql);

		return updateDemandChkFlag;
	}

	/**
	 * @param month - 対象月
	 * @return 請求書チェックの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noにマッチする請求書チェック状態を更新する。
	 */
	public boolean resetDemandCheck() throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * 対象月の請求書フラッグを全てリセットする。
		 */
		String sql = "";
		sql = "update MonthInfo set DemandCheckFlag = '0';";
		st.executeUpdate(sql);

		return updateDemandChkFlag;
	}

	/**
	 * @param month - 対象月
	 * @return 請求書チェックの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたDemand_Noにマッチする請求書チェック状態を更新する。
	 */
	public boolean updateNormalDay(int month, int day) throws SQLException, NoSuchAlgorithmException {

		boolean updateDemandChkFlag = true;

		/**
		 * 対象月の請求書フラッグを更新する。
		 */
		String sql = "";
		sql = "update MonthInfo set NormalDay = '" + day + "' where `Month`='" + month + "';";
		st.executeUpdate(sql);

		return updateDemandChkFlag;
	}
}
