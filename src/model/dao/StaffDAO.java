package model.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Staff;

/**
 * staffテーブルと繋ぐDAOクラス。
 */
public class StaffDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static StaffDAO instance = new StaffDAO();

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
	private StaffDAO() {
	}

	/**
	 * @return StaffDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static StaffDAO getInstance() {
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
	 * @param staffNo - ユーザーID
	 * @param password - パスワード
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoとpasswordからユーザーがログインできるかどうかチェックする。
	 */
	public boolean loginUser(String staffNo, String password, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean loginUserChkFlag = false;

		//パスワードをハッシュ化
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] passwordDigest = digest.digest(password.getBytes());
		String sha1 = String.format("%040x", new BigInteger(1, passwordDigest));

		// Staff_Noとpasswordがマッチしたユーザレコードを取得する
		String sql = "select * from Staff where Staff_No='"
				+ staffNo + "' and password='" + sha1.substring(8) + "'and deleted='0';";
		ResultSet rs = st.executeQuery(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs.next()) {
			if (staffNo.equals(rs.getString("Staff_No"))) {
				if (sha1.substring(8).equals(rs.getString("Password"))) {
					loginUserChkFlag = true;
					staff.setAuthority(rs.getInt("Authority"));
				}
			}
		}
		return loginUserChkFlag;
	}

	/**
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @param password - パスワード
	 * @return データベースに新規ユーザーを挿入出来たらtrue、出来なかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 新規スタッフの情報を新規追加する。
	 */
	public boolean insertUser(Staff staff, String password) throws SQLException, NoSuchAlgorithmException {

		// オートコミットを無効にする
		con.setAutoCommit(false);

		boolean insertUserChkFlag = false;

		//パスワードをハッシュ化
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] passwordDigest = digest.digest(password.getBytes());
		String sha1 = String.format("%040x", new BigInteger(1, passwordDigest));

		String sql = "insert into Staff values(DEFAULT,DEFAULT,'" + staff.getFirstName(1) + "','"+ staff.getLastName(1) + "','"+ staff.getFirstNameKana(1) + "','"+ staff.getLastNameKana(1) + "','"+ staff.getFirstNameEnglish(1) + "','"+ staff.getLastNameEnglish(1) + "','"+ staff.getHireDate(1) + "','"+ staff.getGender(1) + "','" + sha1.substring(8) + "', DEFAULT,DEFAULT,'"+ staff.getMailAddress(1) + "','"+staff.getStaffNameInitial(1)+"','"+staff.getStation(1)+"', DEFAULT,DEFAULT,'"+ staff.getGroup(1) + "',DEFAULT,DEFAULT,DEFAULT,'"+ staff.getPTO(1) +"',DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT);";
		int result = st.executeUpdate(sql);

		// 正しく追加できた場合、コミットする
		if (result > 0) {
			insertUserChkFlag = true;
			con.commit();
		}

		sql = "select * from Staff ORDER BY Staff_No DESC LIMIT 1;";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			staff.setStaffId(1, rs.getInt("Staff_No"));
			staff.setMailAddress(1, rs.getString("Mailaddress"));
		}

		return insertUserChkFlag;
	}

	/**
	 * @param staffNo - ユーザーID
	 * @param password - パスワード
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーのパスワードを変更する。
	 */
	public boolean passwordChange(String staffNo, String password) throws SQLException, NoSuchAlgorithmException {

		boolean passwordChangeChkFlag = false;

		//パスワードをハッシュ化
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] passwordDigest = digest.digest(password.getBytes());
		String sha1 = String.format("%040x", new BigInteger(1, passwordDigest));

		// Staff_Noがマッチしたユーザのレコードのパスワードを変更する
		String sql = "update Staff set password='"+sha1.substring(8)+"',Password_Flg='1' where Staff_No='"
				+ staffNo + "' and deleted='0';";
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			passwordChangeChkFlag = true;
		}
		return passwordChangeChkFlag;
	}

	/**
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全てのユーザーレコードを取得する。
	 */
	public boolean searchAllUser(Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean searchAllUserChkFlag = false;

		// 全ユーザレコードを取得する
		String sql = "select * from Staff where deleted='0';";
		ResultSet rs = st.executeQuery(sql);

		int index = 0;
		// マッチしたデータがあればtrueを代入する
		while(rs.next()){
			index++;
			searchAllUserChkFlag =true;
			staff.setStaffName(index,rs.getString("LastName")+rs.getString("FirstName"));
			staff.setStaffId(index, rs.getInt("Staff_No"));
			staff.setFirstName(index,rs.getString("FirstName"));
			staff.setLastName(index,rs.getString("LastName"));
			staff.setFirstNameKana(index,rs.getString("FirstName_Kana"));
			staff.setLastNameKana(index,rs.getString("LastName_Kana"));
			staff.setFirstNameEnglish(index,rs.getString("FirstName_English"));
			staff.setLastNameEnglish(index,rs.getString("LastName_English"));
			staff.setStaffNameInitial(index,rs.getString("Initial"));
			staff.setStation(index,rs.getString("Station"));
			staff.setGroup(index,rs.getString("Group"));
			staff.setMailAddress(index,rs.getString("Mailaddress"));
			staff.setHireDate(index,rs.getString("Hire_Date"));
			staff.setAuthority(rs.getInt("Authority"));
			staff.setStaffNameInitial(index, rs.getString("Initial"));
			staff.setStation(index, rs.getString("Station"));
			staff.setGender(index, rs.getInt("gender"));
			staff.setFlag(index, rs.getInt("Password_Flg"));
			staff.setStartTime(index, rs.getString("Default_Start_Time"));
			staff.setFinishTime(index, rs.getString("Default_Finish_Time"));
			staff.setAttendanceFlag(index, rs.getInt("Attendance_Flg"));
			staff.setDemandFlag(index, rs.getInt("Demand_Flg"));
			staff.setSkillFlag(index, rs.getInt("Skill_Flg"));
			staff.setPTO(index, rs.getDouble("PTO"));
			staff.setPTO_Consume(index, rs.getDouble("PTO_Cons"));
			if(rs.getString("Group").contains(",4,")) {
				staff.setPosition(index,1);
			}
		}
		staff.setNumbersRecords(index);
		return searchAllUserChkFlag;
	}

	/**
	 * @param staffId - ユーザーID
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffIdに該当するユーザーレコードを取得する。
	 */
	public boolean searchUser(int staffId, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean searchUserChkFlag = false;

		// 指定されたstaffIdに該当するユーザレコードを取得する
		String sql = "select * from Staff where Staff_No ='"+staffId+"' and deleted='0';";
		ResultSet rs = st.executeQuery(sql);

		int index = 0;
		// マッチしたデータがあればtrueを代入する
		while(rs.next()){
			index++;
			searchUserChkFlag =true;
			staff.setStaffName(index,rs.getString("LastName")+rs.getString("FirstName"));
			staff.setStaffId(index, rs.getInt("Staff_No"));
			staff.setFirstName(index,rs.getString("FirstName"));
			staff.setLastName(index,rs.getString("LastName"));
			staff.setFirstNameKana(index,rs.getString("FirstName_Kana"));
			staff.setLastNameKana(index,rs.getString("LastName_Kana"));
			staff.setFirstNameEnglish(index,rs.getString("FirstName_English"));
			staff.setLastNameEnglish(index,rs.getString("LastName_English"));
			staff.setStaffNameInitial(index,rs.getString("Initial"));
			staff.setStation(index,rs.getString("Station"));
			staff.setGroup(index,rs.getString("Group"));
			staff.setMailAddress(index,rs.getString("Mailaddress"));
			staff.setHireDate(index,rs.getString("Hire_Date"));
			staff.setAuthority(rs.getInt("Authority"));
			staff.setStaffNameInitial(index, rs.getString("Initial"));
			staff.setStation(index, rs.getString("Station"));
			staff.setGender(index, rs.getInt("gender"));
			staff.setFlag(index, rs.getInt("Password_Flg"));
			staff.setStartTime(index, rs.getString("Default_Start_Time"));
			staff.setFinishTime(index, rs.getString("Default_Finish_Time"));
			staff.setAttendanceFlag(index, rs.getInt("Attendance_Flg"));
			staff.setDemandFlag(index, rs.getInt("Demand_Flg"));
			staff.setSkillFlag(index, rs.getInt("Skill_Flg"));
			staff.setPTO(index, rs.getDouble("PTO"));
			staff.setPTO_Consume(index, rs.getDouble("PTO_Cons"));
			if(rs.getString("Group").contains(",4,")) {
				staff.setPosition(index,1);
			}
		}
		staff.setNumbersRecords(index);
		return searchUserChkFlag;
	}

	/**
	 * @param group - グループ
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたGroupに所属するユーザーレコードを取得する。
	 */
	public boolean searchGroup(int group, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean searchGroupChkFlag = false;

		// 指定されたGroupに所属するユーザのレコードを取得する
		String sql = "select * from Staff where `Group` like '%,"+group+",%' and deleted='0';";
		ResultSet rs = st.executeQuery(sql);

		int index = 0;
		// マッチしたデータがあればtrueを代入する
		while(rs.next()){
			index++;
			searchGroupChkFlag =true;
			staff.setStaffName(index,rs.getString("LastName")+rs.getString("FirstName"));
			staff.setStaffId(index, rs.getInt("Staff_No"));
			staff.setMailAddress(index, rs.getString("Mailaddress"));
			if(rs.getString("Group").contains(",4,")) {
				staff.setPosition(index,1);
			}
		}
		staff.setNumbersRecords(index);
		return searchGroupChkFlag;
	}

	/**
	 * @param staffId - 更新対象のユーザーID
	 * @param updateId - 更新を担当するユーザーID
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーの登録情報を変更する。
	 */
	public boolean updateUserInfo(int staffId,int updateId, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean updateUserInfoChkFlag = false;

		// Staff_Noがマッチしたユーザのレコードの登録情報を変更する
		String sql = "update Staff set FirstName='"+staff.getFirstName(1)+"',LastName='"+staff.getLastName(1)+"',FirstName_Kana='"+staff.getFirstNameKana(1)+"',LastName_Kana='"+staff.getLastNameKana(1)+"',FirstName_English='"+staff.getFirstNameEnglish(1)+"',LastName_English='"+staff.getLastNameEnglish(1)+"',gender='"+staff.getGender(1)+"',`Group`='"+staff.getGroup(1)+"',Hire_Date='"+staff.getHireDate(1)+"',Mailaddress='"+staff.getMailAddress(1)+"',Authority='"+staff.getAuthority()+"',Initial='"+staff.getStaffNameInitial(1)+"',Station='"+staff.getStation(1)+"',Upd_Us='"+updateId+"' where Staff_No='"
				+ staffId + "' and deleted='0';";
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			updateUserInfoChkFlag = true;
		}
		return updateUserInfoChkFlag;
	}

	/**
	 * @param staffId - 更新対象のユーザーID
	 * @param updateId - 更新を担当するユーザーID
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return 成功したらtrue、失敗したらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーを論理削除する。
	 */
	public boolean deleteUser(int staffId,int updateId, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean deleteUserChkFlag = false;

		// Staff_Noがマッチしたユーザのレコードの登録情報を変更する
		String sql = "update Staff set deleted='1',Upd_Us='"+updateId+"' where Staff_No='"
				+ staffId + "' and deleted='0';";
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			deleteUserChkFlag = true;
		}
		return deleteUserChkFlag;
	}

	/**
	 * @param staffId - 更新対象のユーザーID
	 * @param updateId - 更新を担当するユーザーID
	 * @param type - 更新するフラッグのタイプ
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーの請求書と勤怠のフラッグ情報を変更する。
	 */
	public boolean updateFlag(int staffId,int updateId,int type, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean updateFlagChkFlag = false;

		// Staff_Noがマッチしたユーザのレコードの登録情報を変更する
		String sql;
		if(type==1) {
			sql = "update Staff set Attendance_Flg='"+staff.getAttendanceFlag(1)+"',Upd_Us='"+updateId+"' where Staff_No='"
					+ staffId + "' and deleted='0';";
		}else {
			sql = "update Staff set Demand_Flg='"+staff.getDemandFlag(1)+"',Upd_Us='"+updateId+"' where Staff_No='"
					+ staffId + "' and deleted='0';";
		}
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			updateFlagChkFlag = true;
		}
		return updateFlagChkFlag;
	}

	/**
	 * @param staffId - 更新対象のユーザーID
	 * @param updateId - 更新を担当するユーザーID
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーの基本始業時間と基本終業時間を変更する。
	 */
	public boolean updateDefaultTime(int staffId,int updateId, Staff staff) throws SQLException, NoSuchAlgorithmException {

		boolean updateDefaultTimeChkFlag = false;

		// Staff_Noがマッチしたユーザのレコードの登録情報を変更する
		String sql;
		sql = "update Staff set Default_Start_Time='"+staff.getStartTime(1)+"',Default_Finish_Time='"+staff.getFinishTime(1)+"',Upd_Us='"+updateId+"' where Staff_No='"
				+ staffId + "' and deleted='0';";
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			updateDefaultTimeChkFlag = true;
		}
		return updateDefaultTimeChkFlag;
	}

	/**
	 * @param staffId - 更新対象のユーザーID
	 * @param updateId - 更新を担当するユーザーID
	 * @param staff - スタッフのモデルクラスのインスタンス
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたstaffNoからユーザーの有給付与状況を変更する。
	 */
	public boolean updatePTO(int staffId,int updateId, Staff staff, int type) throws SQLException, NoSuchAlgorithmException {

		boolean updatePTOChkFlag = false;

		// Staff_Noがマッチしたユーザのレコードの登録情報を変更する
		String sql;
		if(type==1) {
			sql = "update Staff set PTO='"+staff.getPTO(staffId)+"',PTO_Cons='0',Upd_Us='"+updateId+"' where Staff_No='"
					+ staff.getStaffId(staffId) + "' and deleted='0';";
		}else {
			sql = "update Staff set PTO='"+staff.getPTO(staffId)+"',PTO_Cons='"+staff.getPTO_Consume(staffId)+"',Upd_Us='"+updateId+"' where Staff_No='"
					+ staff.getStaffId(staffId) + "' and deleted='0';";
		}
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			updatePTOChkFlag = true;
		}
		return updatePTOChkFlag;
	}


	/**
	 * @return データベースと一致していたらtrue、一致していなかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ユーザーの請求書と勤怠のフラッグ情報を変更する。
	 */
	public boolean resetFlag() throws SQLException, NoSuchAlgorithmException {

		boolean resetFlagChkFlag = false;

		// 全ユーザのレコードの登録情報を変更する
		String sql = "update Staff set Attendance_Flg='0',Demand_Flg='0',Skill_Flg='0' where deleted='0';";
		int rs = st.executeUpdate(sql);

		// マッチしたデータがあればtrueを代入する
		if (rs>0) {
			resetFlagChkFlag = true;
		}
		return resetFlagChkFlag;
	}
}
