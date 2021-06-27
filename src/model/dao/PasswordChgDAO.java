package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * PasswordChgテーブルと繋ぐDAOクラス
 */
public class PasswordChgDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static PasswordChgDAO instance = new PasswordChgDAO();

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
	private PasswordChgDAO() {
	}

	/**
	 * @return PTODAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static PasswordChgDAO getInstance() {
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
	 * @param staffId - PTOのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当するPasswordChgのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * staffId,checkString,limitTimeに該当するPasswordChgのレコードを取得する
	 */
	public boolean searchPasswordChg(int staffId, String checkString, String limitTime) throws SQLException, NoSuchAlgorithmException {

		boolean searchPasswordChgChkFlag = false;

		// staffIdに該当するPasswordChgのレコードを取得する
		String sql = "select * from PasswordChg where Staff_Id ='"+staffId+"' and CheckString ='"+checkString+"' and LimitTime < '"+limitTime+"';";
		ResultSet rs = st.executeQuery(sql);


		if (rs.next()) {
			searchPasswordChgChkFlag = true;
		}

		return searchPasswordChgChkFlag;
	}

	/**
	 * @param staffId - PTOのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当するPasswordChgのレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * staffIdに該当するPasswordChgのレコードを取得する
	 */
	public boolean searchPasswordChg(int staffId) throws SQLException, NoSuchAlgorithmException {

		boolean searchPasswordChgChkFlag = false;

		// staffIdに該当するPasswordChgのレコードを取得する
		String sql = "select * from PasswordChg where Staff_Id ='"+staffId+"';";
		ResultSet rs = st.executeQuery(sql);


		if (rs.next()) {
			searchPasswordChgChkFlag = true;
		}

		return searchPasswordChgChkFlag;
	}



	/**
	 * @param staffId - staffId
	 * @return PasswordChgのレコードの削除に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffIdにマッチするPasswordChgのレコードを削除する。
	 */
	public boolean deletePasswordChg(int staffId) throws SQLException, NoSuchAlgorithmException {

		boolean deletePasswordChgChkFlag = true;

		// staffIdがマッチしたPasswordChgのレコードを削除する
		String sql = "";
		int num=0;
		sql = "delete from PasswordChg where Staff_Id ='"+staffId+"';";
		num = st.executeUpdate(sql);
		if(num==0) {
			deletePasswordChgChkFlag = false;
		}

		return deletePasswordChgChkFlag;
	}

	/**
	 * @param staffId - staffId
	 * @param checkString - チェック文字列
	 * @param limitTime - パスワード変更期限
	 * @return PasswordChgのレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * PasswordChgのレコードを新規作成する。
	 */
	public boolean insertPasswordChg(int staffId, String checkString, String limitTime) throws SQLException, NoSuchAlgorithmException {

		boolean insertPasswordChgChkFlag = false;

		// PasswordChgのレコードを作成する
		String sql = "";
		sql = "insert into PasswordChg value(DEFAULT,'" + staffId + "','"+checkString+"',DEFAULT,'" + limitTime + "','" + staffId + "',DEFAULT,DEFAULT,DEFAULT);";
		int rs = st.executeUpdate(sql);

		if (rs>0) {
			insertPasswordChgChkFlag = true;
		}
		return insertPasswordChgChkFlag;
	}


}
