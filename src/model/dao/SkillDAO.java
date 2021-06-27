package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Skill;
import model.entity.Staff;

/**
 * skillテーブルと繋ぐDAOクラス
 */
public class SkillDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static SkillDAO instance = new SkillDAO();

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
	private SkillDAO() {
	}

	/**
	 * @return SkillDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static SkillDAO getInstance() {
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
	 * @param skillId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param skill - スキルシートのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分のスキルシートレコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたSkill_Idとyearとmonthからスキルシートレコードを取得する。
	 */
	public boolean searchSkill(int skillId, int month, int year, Skill skill)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchSkillChkFlag = false;

		// Skill_Idとyearとmonthがマッチした勤務表レコードを取得する
		String sql = "select * from Skill where Skill_Id='"
				+ skillId + "' and Year='" + year + "' and Month='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのSkillに取得したデータをセットしていく
		 */
		if (rs.next()) {
			searchSkillChkFlag = true;
			skill.setProjectName(1, rs.getString("Project_Name"));
			skill.setBussinessOverview(1, rs.getString("Bussiness_Overview"));
			skill.setRole(1, rs.getInt("role"));
			skill.setScale(1, rs.getInt("scale"));
			skill.setServer_OS(1, rs.getString("server_OS"));
			skill.setDB(1, rs.getString("DB"));
			skill.setTool(1, rs.getString("tool"));
			skill.setUseLanguage(1, rs.getString("Use_Language"));
			skill.setOther(1, rs.getString("Other"));
		} else {
			searchSkillChkFlag = insertSkillFirstTime(skillId, month, year, skill);
		}

		return searchSkillChkFlag;
	}

	/**
	 * @param skillId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param skill - スキルシートのモデルクラスのインスタンス
	 * @param staff - 社員情報のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分のスキルシートレコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたSkill_Idとyearとmonthからスキルシートレコードを取得する。
	 */
	public boolean searchAllSkill(int month, int year, Skill skill, Staff staff)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchSkillChkFlag = false;

		Skill personalSkill = new Skill();

		for (int i = 0; i < staff.getNumbersRecords(); i++) {
			searchSkill(staff.getStaffId(i), month, year, personalSkill);
			skill.setProjectName(i, personalSkill.getProjectName(1));
			skill.setBussinessOverview(i, personalSkill.getBussinessOverview(1));
			skill.setRole(i, personalSkill.getRole(1));
			skill.setScale(i, personalSkill.getScale(1));
			skill.setServer_OS(i, personalSkill.getServer_OS(1));
			skill.setDB(i, personalSkill.getDB(1));
			skill.setTool(i, personalSkill.getTool(1));
			skill.setUseLanguage(i, personalSkill.getUseLanguage(1));
			skill.setOther(i, personalSkill.getOther(1));
			searchSkillChkFlag = true;
		}

		return searchSkillChkFlag;
	}

	/**
	 * @param skillId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param skill - スキルシートのモデルクラスのインスタンス
	 * @param staff - 社員情報のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する当月分のスキルシートレコードがあればtrue、ない場合は初回作成メソッドで作成が成功し場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたSkill_Idとyearとmonthからスキルシートレコードを取得する。
	 */
	public boolean writeAllSkill(int month, int year, Staff staff)
			throws SQLException, NoSuchAlgorithmException {

		boolean searchSkillChkFlag = false;

		Skill personalSkill = new Skill();

		for (int i = 0; i < staff.getNumbersRecords(); i++) {
			if (staff.getSkillFlag(i) == 0) {
				searchSkill(staff.getStaffId(i), month - 2, year, personalSkill);
				updateSkill(1, staff.getStaffId(i), year, month - 1, personalSkill);
			}
			searchSkillChkFlag = true;
		}

		return searchSkillChkFlag;
	}

	/**
	 * @param skillId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param skill - スキルシートのモデルクラスのインスタンス
	 * @return スキルシートレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたskillIdとyearとmonthにマッチするスキルシートレコードを更新する。
	 */
	public boolean updateSkill(int updateId, int skillId, int year, int month, Skill skill)
			throws SQLException, NoSuchAlgorithmException {

		boolean updateSkillChkFlag = true;

		// skillIdとyearとmonthがマッチしたスキルシートレコードを更新する
		String sql = "";
		int num = 0;
		sql = "update Skill set Project_Name = \"" + skill.getProjectName(1) + "\",Bussiness_Overview = \""
				+ skill.getBussinessOverview(1) + "\", role = \"" + skill.getRole(1) + "\", scale = \""
				+ skill.getScale(1) + "\",server_OS = \"" + skill.getServer_OS(1) + "\",DB = \"" + skill.getDB(1)
				+ "\",tool = \"" + skill.getTool(1) + "\",Use_Language = \"" + skill.getUseLanguage(1) + "\",Other = \""
				+ skill.getOther(1) + "\",Upd_Us = \"" + updateId + "\" where Skill_Id='" + skillId + "' and Year='"
				+ year + "' and Month='" + month + "';";
		num = st.executeUpdate(sql);
		if (num == 0) {
			updateSkillChkFlag = false;
		}
		sql = "update Staff set Skill_Flg='1',Upd_Us='" + updateId + "' where Staff_No='"
				+ skillId + "';";
		num = st.executeUpdate(sql);
		if (num == 0) {
			updateSkillChkFlag = false;
		}

		return updateSkillChkFlag;
	}

	/**
	 * @param skillId - ユーザーID
	 * @param month - 月
	 * @param year - 年
	 * @param skill - スキルシートのモデルクラスのインスタンス
	 * @return 月数分のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたSkill_Idとyearとmonthからスキルシートレコードを１月分作成してskillインスタンスにデフォルト値をセットする。
	 */
	public boolean insertSkillFirstTime(int skillId, int month, int year, Skill skill)
			throws SQLException, NoSuchAlgorithmException {

		boolean insertSkillFirstTimeChkFlag = false;

		// Skill_Idとyearとmonthを基にスキルシートレコードを作成する
		String sql = "";
		sql = "insert into Skill value(DEFAULT,'" + skillId + "','" + year + "','" + month
				+ "',DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,'" + skillId
				+ "',DEFAULT,DEFAULT,DEFAULT);";
		st.executeUpdate(sql);

		sql = "select * from Skill where Skill_Id='"
				+ skillId + "' and Year='" + year + "' and Month='" + month + "';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのSkillに取得したデータをセットしていく
		 */
		if (rs.next()) {
			insertSkillFirstTimeChkFlag = true;
			skill.setProjectName(1, rs.getString("Project_Name"));
			skill.setBussinessOverview(1, rs.getString("Bussiness_Overview"));
			skill.setRole(1, rs.getInt("role"));
			skill.setScale(1, rs.getInt("scale"));
			skill.setServer_OS(1, rs.getString("server_OS"));
			skill.setDB(1, rs.getString("DB"));
			skill.setTool(1, rs.getString("tool"));
			skill.setUseLanguage(1, rs.getString("Use_Language"));
			skill.setOther(1, rs.getString("Other"));
		}
		return insertSkillFirstTimeChkFlag;
	}

}
