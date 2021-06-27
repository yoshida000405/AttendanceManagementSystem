package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Holiday;

/**
 * holidayテーブルと繋ぐDAOクラス
 */
public class HolidayDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static HolidayDAO instance = new HolidayDAO();

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
	private HolidayDAO() {
	}

	/**
	 * @return HolidayDAOの唯一のインスタンス
	 * 唯一のインスタンスを取得する。
	 */
	public static HolidayDAO getInstance() {
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
	 * @param year - 年
	 * @param month - 月
	 * @param holiday - 祝日のモデルクラスのインスタンス
	 * @return データベースに対象年月に該当する祝日のレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたyearとmonthから対象年月の祝日を取得してHolidayインスタンスにセットする。
	 */
	public boolean setHoliday(int year, int month, Holiday holiday) throws SQLException, NoSuchAlgorithmException {

		boolean setHolidayChkFlag = false;

		// YearとMonthがマッチした祝日レコードを取得する
		String sql = "select * from Holiday where Year='"
				+ year + "' and Month='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのHolidayに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			setHolidayChkFlag = true;
			do {
				holiday.setHoliday(index,rs.getInt("Day"));
				index++;
			}while(rs.next());
			holiday.setNumbersHoliday();
		}
		return setHolidayChkFlag;
	}
}
