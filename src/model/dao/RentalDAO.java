package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.logging.Logger;

import model.entity.Equipment;
import model.entity.Rental;
import model.entity.Staff;

/**
 * rentalテーブルと繋ぐDAOクラス
 */
public class RentalDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static RentalDAO instance = new RentalDAO();

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
	private RentalDAO() {
	}

	/**
	 * @return TimerDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static RentalDAO getInstance() {
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
	 * @param rentalId - 機材予約のレコードNo
	 * @param staff - staffのモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 個人の機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchRental(int rentalId, Staff staff, Rental rental) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchRentalChkFlag = false;

		String sql = "select * from Rental where Rental_Id='"+rentalId+"';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのstaffに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchRentalChkFlag = true;
			do {
				index++;
				staff.setStaffId(index,rs.getInt("Staff_Id"));
				rental.setEquipmentId(index, rs.getInt("Equipment_Id"));
				rental.setStartYear(index, rs.getInt("Start_Year"));
				rental.setStartMonth(index, rs.getInt("Start_Month"));
				rental.setStartDay(index, rs.getInt("Start_Day"));
				rental.setFinishYear(index, rs.getInt("Finish_Year"));
				rental.setFinishMonth(index, rs.getInt("Finish_Month"));
				rental.setFinishDay(index, rs.getInt("Finish_Day"));
				rental.setRequestYear(index, rs.getInt("Request_Year"));
				rental.setRequestMonth(index, rs.getInt("Request_Month"));
				rental.setRequestDay(index, rs.getInt("Request_Day"));
			}while(rs.next());
		}
		staff.setNumbersRecords(index);

		return searchRentalChkFlag;
	}

	/**
	 * @param rentalId - 機材予約のレコードNo
	 * @param staff - staffのモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 個人の機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchRental(int equipmentId) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchRentalChkFlag = false;

		String sql = "select * from Rental where Equipment_Id='"+equipmentId+"' and Flag='1';";
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			if (rs.next()) {
				searchRentalChkFlag = true;
			}
		}

		return searchRentalChkFlag;
	}


	/**
	 * @param staffId - 社員番号
	 * @param rental - 機材予約のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 個人の機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchUserRental(int staffId, Rental rental) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchUserRentalChkFlag = false;

		String sql = "select * from Rental where Staff_Id='"+staffId+"' and Flag<'2';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのRentalに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchUserRentalChkFlag = true;
			do {
				index++;
				rental.setRentalId(index,rs.getInt("Rental_Id"));
				rental.setEquipmentId(index,rs.getInt("Equipment_Id"));
				rental.setFlag(index, rs.getInt("Flag"));
				rental.setPeriod(index, rs.getInt("Period"));
				rental.setStartMonth(index, rs.getInt("Start_Month"));
				rental.setStartDay(index, rs.getInt("Start_Day"));
				rental.setFinishMonth(index, rs.getInt("Finish_Month"));
				rental.setFinishDay(index, rs.getInt("Finish_Day"));
				rental.setRequestMonth(index, rs.getInt("Request_Month"));
				rental.setRequestDay(index, rs.getInt("Request_Day"));
			}while(rs.next());
		}
		rental.setNumbersRecords(index);

		return searchUserRentalChkFlag;
	}

	/**
	 * @param rental - 機材予約のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ての機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchAllRental(Rental rental) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchAllRentalChkFlag = false;

		String sql = "select * from Rental where Flag<'2' ORDER BY Equipment_Id ASC,Flag DESC, Request_Year ASC, Request_Month ASC, Request_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのRentalに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAllRentalChkFlag = true;
			do {
				index++;
				rental.setRentalId(index,rs.getInt("Rental_Id"));
				rental.setEquipmentId(index,rs.getInt("Equipment_Id"));
				rental.setStaffId(index,rs.getInt("Staff_Id"));
				rental.setFlag(index, rs.getInt("Flag"));
				rental.setPeriod(index, rs.getInt("Period"));
				rental.setStartMonth(index, rs.getInt("Start_Month"));
				rental.setStartDay(index, rs.getInt("Start_Day"));
				rental.setFinishMonth(index, rs.getInt("Finish_Month"));
				rental.setFinishDay(index, rs.getInt("Finish_Day"));
				rental.setRequestMonth(index, rs.getInt("Request_Month"));
				rental.setRequestDay(index, rs.getInt("Request_Day"));
			}while(rs.next());
		}
		rental.setNumbersRecords(index);
		rental.setWait();

		return searchAllRentalChkFlag;
	}

	/**
	 * @param rental - 機材予約のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ての機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchAllReserve(Map<String,String> mapMsg, Equipment equipment) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchAllReserveChkFlag = false;

		for(int i=1; i<=equipment.getNumbersRecords(); i++) {
			String sql = "select * from Rental where Flag='0' and Equipment_Id ='"+equipment.getEquipmentId(i)+"' ORDER BY Request_Year DESC, Request_Month DESC, Request_Day DESC;";
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				searchAllReserveChkFlag = true;
				mapMsg.put("finishMonth"+equipment.getEquipmentId(i), ""+rs.getInt("Finish_Month"));
				mapMsg.put("finishDay"+equipment.getEquipmentId(i), ""+rs.getInt("Finish_Day"));
			}else {
				searchAllReserveChkFlag = true;
				mapMsg.put("finishMonth"+equipment.getEquipmentId(i), ""+LocalDate.now().getMonthValue());
				mapMsg.put("finishDay"+equipment.getEquipmentId(i), ""+LocalDate.now().getDayOfMonth());
			}
		}

		return searchAllReserveChkFlag;
	}


	/**
	 * @param equipmentId - 機材番号
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象機材の予約１番待ちのユーザーを取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean waitUser(int equipmentId, Staff staff) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean waitUserChkFlag = false;

		String sql = "select * from Rental where Equipment_Id='"+equipmentId+"' and Flag='0' ORDER BY Request_Year ASC, Request_Month ASC, Request_Day ASC LIMIT 1;";
		ResultSet rs = st.executeQuery(sql);

		if (rs.next()) {
			waitUserChkFlag = true;
			staff.setStaffId(1, rs.getInt("Staff_Id"));
		}

		return waitUserChkFlag;
	}

	/**
	 * @param equipmentId - 機材番号
	 * @param staffId - 社員番号
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * ユーザーの予約順序を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public int waitNumber(int equipmentId, int staffId) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		int result =0;

		String sql = "select * from Rental where Equipment_Id='"+equipmentId+"' and Flag='0' ORDER BY Request_Year ASC, Request_Month ASC, Request_Day ASC;";
		ResultSet rs = st.executeQuery(sql);

		int index=0;
		if (rs.next()) {
			do {
				index++;
				if(rs.getInt("Staff_Id")==staffId) {
					result =index;
				}
			}while(rs.next());
		}

		return result;
	}

	/**
	 * @param rental - rentalのモデルクラスのインスタンス
	 * @return レンタル申請のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * rentalのレコードを新規作成する。
	 */
	public boolean insertRental(int staffId, Rental rental) throws SQLException, NoSuchAlgorithmException {

		boolean insertRentalChkFlag = false;

		// rentalのレコードを作成する
		String sql = "";
		sql = "insert into Rental value(DEFAULT,'"+rental.getEquipmentId(1)+"','" + staffId + "','" + rental.getRequestYear(1) + "','" + rental.getRequestMonth(1) + "','" + rental.getRequestDay(1) + "','"+ rental.getStartYear(1) + "','" + rental.getStartMonth(1) + "','" + rental.getStartDay(1) + "','"+ rental.getFinishYear(1) + "','" + rental.getFinishMonth(1) + "','" + rental.getFinishDay(1) + "','"+rental.getPeriod(1)+"','0',DEFAULT,'"+ staffId +"',DEFAULT,DEFAULT,DEFAULT);";
		int rs = st.executeUpdate(sql);

		if (rs>0) {
			rental.setRentalId(1, rs);
			insertRentalChkFlag = true;
		}
		return insertRentalChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param rentalId - rentalのレコードID
	 * @param type - 取り消す種類
	 * @return レンタル申請のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたrentalIdにマッチするレコードを更新する。
	 */
	public boolean cancelRental(int updateId, int rentalId, int type) throws SQLException, NoSuchAlgorithmException {

		boolean cancelRentalChkFlag = true;

		String sql = "";
		int num=0;
		if(type==1) {
			// rentalIdがマッチしたレコードを削除する
			sql = "delete from Rental where Rental_Id='"+ rentalId + "';";
		}else {
			// rentalIdがマッチしたレコードを更新する
			sql = "update Rental set Flag = '2', Staff_Id='0', Permit_Dt = '"+LocalDateTime.now(ZoneId.of("Asia/Tokyo"))+"',Upd_Us = \""+updateId+"\" where Rental_Id='"+ rentalId + "';";
		}

		num = st.executeUpdate(sql);
		if(num==0) {
			cancelRentalChkFlag = false;
		}

		return cancelRentalChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param rentalId - rentalのレコードID
	 * @return レンタル申請のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたrentalIdにマッチするレコードを更新する。
	 */
	public boolean permitRental(int updateId, int rentalId, Rental rental) throws SQLException, NoSuchAlgorithmException {

		boolean permitRentalChkFlag = true;

		// rentalIdがマッチしたレコードを更新する
		String sql = "";
		int num=0;
		sql = "update Rental set Flag = '1', Staff_Id='"+updateId+"', Start_Year ='"+rental.getStartYear(1)+"',Start_Month ='"+rental.getStartMonth(1)+"',Start_Day ='"+rental.getStartDay(1)+"',Finish_Year ='"+rental.getFinishYear(1)+"',Finish_Month ='"+rental.getFinishMonth(1)+"',Finish_Day ='"+rental.getFinishDay(1)+"',Permit_Dt = '"+LocalDateTime.now()+"',Upd_Us = \""+updateId+"\" where Rental_Id='"+ rentalId + "';";
		num = st.executeUpdate(sql);
		if(num==0) {
			permitRentalChkFlag = false;
		}

		return permitRentalChkFlag;
	}
}
