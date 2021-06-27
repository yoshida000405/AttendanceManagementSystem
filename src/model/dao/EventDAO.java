package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Event;

/**
 * eventテーブルと繋ぐDAOクラス
 */
public class EventDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static EventDAO instance = new EventDAO();

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
	private EventDAO() {
	}

	/**
	 * @return EventDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static EventDAO getInstance() {
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
	 * @param staffId - 社員番号
	 * @param event - 機材予約のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 個人の機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchUserEvent(int staffId, Event event) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchUserEventChkFlag = false;

		String sql = "select * from Event where Staff_Id='"+staffId+"' and Flag<'2';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchUserEventChkFlag = true;
			do {
				index++;
				event.setEventId(index,rs.getInt("Event_Id"));
				event.setEquipmentId(index,rs.getInt("Equipment_Id"));
				event.setFlag(index, rs.getInt("Flag"));
				event.setStart(index, rs.getString("StartTime"));
				event.setFinish(index, rs.getString("FinishTIme"));
			}while(rs.next());
		}
		event.setNumbersRecords(index);

		return searchUserEventChkFlag;
	}

	/**
	 * @param event - 機材予約のモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ての機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchAllEvent(Event event) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchAllEventChkFlag = false;

		String sql = "select * from Event where Flag<'2' ORDER BY Equipment_Id ASC,Flag DESC, StartTime ASC;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * マッチしたデータがあればtrueを代入する
		 * entityのRentalに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchAllEventChkFlag = true;
			do {
				index++;
				event.setEventId(index,rs.getInt("Event_Id"));
				event.setEquipmentId(index,rs.getInt("Equipment_Id"));
				event.setStaffId(index,rs.getInt("Staff_Id"));
				event.setFlag(index, rs.getInt("Flag"));
				event.setStart(index, rs.getString("StartTime"));
				event.setFinish(index, rs.getString("FinishTime"));
			}while(rs.next());
		}
		event.setNumbersRecords(index);
		event.setWait();

		return searchAllEventChkFlag;
	}

	/**
	 * @param year - 年
	 * @param month - 月
	 * @param event - イベントのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象月のイベントのレコードを取得する。
	 */
	public boolean searchEvent(String start, String finish, Event event) throws SQLException, NoSuchAlgorithmException {

		boolean searchEventChkFlag = false;

		// 対象月のイベントのレコードを取得する
		String sql = "select * from Event where Flag < '2' and (StartTime >= '"+start+"' or FinishTime < '"+finish+"') ORDER BY StartTime;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchEventChkFlag = true;
			index++;
			event.setEventId(index, rs.getInt("Event_Id"));
			event.setStaffId(index, rs.getInt("Staff_Id"));
			event.setEquipmentId(index, rs.getInt("Equipment_Id"));
			event.setTitle(index, rs.getString("Title"));
			event.setStart(index, rs.getString("StartTime"));
			event.setFinish(index, rs.getString("FinishTime"));
			event.setFlag(index, rs.getInt("Flag"));
			event.setDivision(index, rs.getInt("Division"));
		}
		event.setNumbersRecords(index);

		return searchEventChkFlag;
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
	public boolean searchEvent(int eventId, Event event) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchEventChkFlag = false;

		String sql = "select * from Event where Event_Id='"+eventId+"';";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchEventChkFlag = true;
			index++;
			event.setEventId(index, rs.getInt("Event_Id"));
			event.setStaffId(index, rs.getInt("Staff_Id"));
			event.setEquipmentId(index, rs.getInt("Equipment_Id"));
			event.setTitle(index, rs.getString("Title"));
			event.setStart(index, rs.getString("StartTime"));
			event.setFinish(index, rs.getString("FinishTime"));
			event.setFlag(index, rs.getInt("Flag"));
			event.setDivision(index, rs.getInt("Division"));
		}
		event.setNumbersRecords(index);

		return searchEventChkFlag;
	}

	/**
	 * @param rentalId - 機材予約のレコードNo
	 * @param staff - staffのモデルクラスのインスタンス
	 * @return データベースに該当するレコードがあればtrue。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 次順の機材予約状況を取得。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public boolean searchNextEvent(int equipmentId, Event event) throws SQLException, NoSuchAlgorithmException, SecurityException, IOException {

		boolean searchEventChkFlag = false;

		String sql = "select * from Event where Equipment_Id='"+equipmentId+"' and Flag ='0' ORDER BY StartTime;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchEventChkFlag = true;
			index++;
			event.setStart(index, rs.getString("StartTime"));
		}
		event.setNumbersRecords(index);

		return searchEventChkFlag;
	}

	/**
	 * @param day - 対象日
	 * @param event - イベントのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象日に予約のあるレコードを取得する。
	 */
	public boolean searchNotEvent(String day, Event event) throws SQLException, NoSuchAlgorithmException {

		boolean searchEventChkFlag = false;

		// 対象月のイベントのレコードを取得する
		String sql = "select * from Event where Flag ='1' and StartTime <= '"+day+"' and FinishTime >= '"+day+"' ORDER BY Equipment_Id, StartTime;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchEventChkFlag = true;
			index++;
			event.setEventId(index, rs.getInt("Event_Id"));
			event.setStaffId(index, rs.getInt("Staff_Id"));
			event.setEquipmentId(index, rs.getInt("Equipment_Id"));
			event.setTitle(index, rs.getString("Title"));
			event.setStart(index, rs.getString("StartTime"));
			event.setFinish(index, rs.getString("FinishTime"));
			event.setFlag(index, rs.getInt("Flag"));
			event.setDivision(index, rs.getInt("Division"));
		}
		event.setNumbersRecords(index);

		return searchEventChkFlag;
	}

	/**
	 * @param day - 対象日
	 * @param event - イベントのモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 対象日に予約のないレコードを取得する。
	 */
	public boolean searchOkEvent(String day, Event event, int equipmentId) throws SQLException, NoSuchAlgorithmException {

		boolean searchEventChkFlag = false;

		// 対象月のイベントのレコードを取得する
		String sql = "select * from Event where Equipment_id = '"+equipmentId+"' and StartTime > '"+day+"' ORDER BY StartTime;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのEventに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchEventChkFlag = true;
			index++;
			event.setEventId(index, rs.getInt("Event_Id"));
			event.setStaffId(index, rs.getInt("Staff_Id"));
			event.setEquipmentId(index, rs.getInt("Equipment_Id"));
			event.setTitle(index, rs.getString("Title"));
			event.setStart(index, rs.getString("StartTime"));
			event.setFinish(index, rs.getString("FinishTime"));
			event.setFlag(index, rs.getInt("Flag"));
			event.setDivision(index, rs.getInt("Division"));
		}
		event.setNumbersRecords(index);

		return searchEventChkFlag;
	}

	/**
	 * @param event - eventのモデルクラスのインスタンス
	 * @return レンタル申請のレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * eventのレコードを新規作成する。
	 */
	public boolean insertEvent(int staffId, Event event, int division) throws SQLException, NoSuchAlgorithmException {

		boolean insertEventChkFlag = false;

		// eventのレコードを作成する
		String sql = "";
		sql = "insert into Event value(DEFAULT,'"+event.getTitle(1)+"','" + staffId + "','" + event.getEquipmentId(1) + "','" + division + "','0','"+ event.getStart(1) + "','" + event.getFinish(1) + "','" + staffId +"',DEFAULT,DEFAULT,DEFAULT);";
		int rs = st.executeUpdate(sql);

		if (rs>0) {
			event.setEventId(1, rs);
			insertEventChkFlag = true;
		}
		return insertEventChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param eventId - rentalのレコードID
	 * @param type - 取り消す種類
	 * @return レンタル申請のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたeventIdにマッチするレコードを更新する。
	 */
	public boolean cancelEvent(int updateId, int eventId, int type) throws SQLException, NoSuchAlgorithmException {

		boolean cancelEventChkFlag = true;

		String sql = "";
		int num=0;
		if(type==1) {
			// eventIdがマッチしたレコードを削除する
			sql = "delete from Event where Event_Id='"+ eventId + "';";
		}else {
			// eventIdがマッチしたレコードを更新する
			sql = "update Event set Flag = '2',Upd_Us = \""+updateId+"\" where Event_Id='"+ eventId + "';";
		}

		num = st.executeUpdate(sql);
		if(num==0) {
			cancelEventChkFlag = false;
		}

		return cancelEventChkFlag;
	}

	/**
	 * @param updateId - 更新者のID
	 * @param eventId - rentalのレコードID
	 * @param event - eventのモデルクラスのインスタンス
	 * @return レンタル申請のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたrentalIdにマッチするレコードを更新する。
	 */
	public boolean permitEvent(int updateId, int eventId, Event event, int type) throws SQLException, NoSuchAlgorithmException {

		boolean permitEventChkFlag = true;

		// rentalIdがマッチしたレコードを更新する
		String sql = "";
		int num=0;
		if(type==1) {
			sql = "update Event set Flag = '1', StartTime ='"+event.getStart(1)+"',Upd_Us = \""+updateId+"\" where Event_Id='"+ eventId + "';";
		}else {
			sql = "update Event set StartTime ='"+event.getStart(1)+"',FinishTime='"+event.getFinish(1)+"',Upd_Us = \""+updateId+"\" where Event_Id='"+ eventId + "';";
		}
		num = st.executeUpdate(sql);
		if(num==0) {
			permitEventChkFlag = false;
		}

		return permitEventChkFlag;
	}

}
