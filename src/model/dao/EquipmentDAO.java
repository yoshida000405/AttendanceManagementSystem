package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Equipment;

/**
 * equipmentテーブルと繋ぐDAOクラス
 */
public class EquipmentDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static EquipmentDAO instance = new EquipmentDAO();

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
	private EquipmentDAO() {
	}

	/**
	 * @return TimerDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static EquipmentDAO getInstance() {
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
	 * @param equipment - 機材情報のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ての機材情報を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchAllEquipment(Equipment equipment) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchAllEquipmentChkFlag = false;

		String sql = "select * from Equipment;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのRentalに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAllEquipmentChkFlag = true;
			do {
				index++;
				equipment.setEquipmentId(index,rs.getInt("Equipment_Id"));
				equipment.setStaffId(index,rs.getInt("Staff_Id"));
				equipment.setName(index, rs.getString("Name"));
				equipment.setDivision(index, rs.getInt("Division"));
				equipment.setPurchase(index, rs.getString("Purchase_Dt"));
				equipment.setFlag(index, rs.getInt("Flag"));

				if(rs.getInt("Division")==1) {
					equipment.setCPU(index, rs.getString("CPU"));
					equipment.setMemory(index, rs.getString("Memory"));
					equipment.setVolume(index, rs.getString("Volume"));
				}
			}while(rs.next());
		}
		equipment.setNumbersRecords(index);

		return searchAllEquipmentChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param equipmentId - equipmentのレコードID
	 * @return レンタル申請のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたequipmentIdにマッチするレコードを更新する。
	 */
	public boolean updateEquipment(int updateId, int equipmentId, int type) throws SQLException, NoSuchAlgorithmException {

		boolean updateEquipmentChkFlag = true;

		// ptoIdがマッチした掲示板のレコードを更新する
		String sql = "";
		int num=0;
		sql = "update Equipment set Flag = '"+type+"', Staff_Id = '"+updateId+"' where Equipment_Id='"+ equipmentId + "';";
		num = st.executeUpdate(sql);
		if(num==0) {
			updateEquipmentChkFlag = false;
		}

		return updateEquipmentChkFlag;
	}

}
