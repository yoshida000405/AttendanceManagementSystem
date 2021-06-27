package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import controller.AutomaticCheckController;
import controller.PTOController;
import model.entity.Timer;

/**
 * timerテーブルと繋ぐDAOクラス
 */
public class TimerDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static TimerDAO instance = new TimerDAO();

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
	private TimerDAO() {
	}

	/**
	 * @return TimerDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static TimerDAO getInstance() {
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
	 * @param timer - タイマーのモデルクラスのインスタンス
	 * @return データベースに対象年月に該当する祝日のレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * タイマーのフラグをチェック状態にセットする。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean checkTimer(Timer timer, int id, Logger logger)
			throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean checkTimerChkFlag = false;

		// タイマーレコードを取得する
		String sql = "select * from Timer where Timer_Id='" + id + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのTimerに取得したデータをセットしていく
		 */
		if (rs.next()) {
			checkTimerChkFlag = true;
			do {
				timer.setFlag(rs.getInt("Flag"));
			} while (rs.next());
		}
		if (timer.getFlag() == 0 && id < 3) {
			AutomaticCheckController.automatic(logger);
			timer.setFlag(1);
			sql = "update Timer set Flag='1' where Timer_Id < '3';";
			int result = st.executeUpdate(sql);
			// マッチしたデータがあればtrueを代入する
			if (result > 0) {
				checkTimerChkFlag = true;
			} else {
				checkTimerChkFlag = false;
			}
		} else if (timer.getFlag() == 0 && id == 3) {
			PTOController pto = new PTOController();
			pto.check(logger);
			timer.setFlag(1);
			sql = "update Timer set Flag='1' where Timer_Id = '3';";
			int result = st.executeUpdate(sql);
			// マッチしたデータがあればtrueを代入する
			if (result > 0) {
				checkTimerChkFlag = true;
			} else {
				checkTimerChkFlag = false;
			}
		}
		return checkTimerChkFlag;
	}

	/**
	 * @param timer - タイマーのモデルクラスのインスタンス
	 * @return データベースに対象年月に該当する祝日のレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * タイマーのフラグをチェック状態にセットする。
	 */
	public boolean resetTimer(Timer timer, int id) throws SQLException, NoSuchAlgorithmException {

		boolean resetTimerChkFlag = false;

		// タイマーレコードを取得する
		String sql = "select * from Timer where Timer_Id='" + id + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのTimerに取得したデータをセットしていく
		 */
		if (rs.next()) {
			resetTimerChkFlag = true;
			do {
				timer.setFlag(rs.getInt("Flag"));
			} while (rs.next());
		}
		if (timer.getFlag() == 1) {
			sql = "update Timer set Flag='0' where Timer_Id = '" + id + "';";
			int result = st.executeUpdate(sql);
			// マッチしたデータがあればtrueを代入する
			if (result > 0) {
				resetTimerChkFlag = true;
			} else {
				resetTimerChkFlag = false;
			}
		}
		return resetTimerChkFlag;
	}
}
