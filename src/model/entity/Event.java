package model.entity;

/**
 * イベントモデルクラス。
 */
public class Event {

	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * イベントID
	 */
	private int[] eventId = new int[50];

	/**
	 * ユーザーID
	 */
	private int[] staffId = new int[50];

	/**
	 * 機材ID
	 */
	private int[] equipmentId = new int[50];

	/**
	 * レンタルID
	 */
	private int[] rentalId = new int[50];

	/**
	 * 区分
	 */
	private int[] division = new int[50];

	/**
	 * フラッグ
	 */
	private int[] flag = new int[50];

	/**
	 * タイトル
	 */
	private String[] title = new String[50];

	/**
	 * 開始日時
	 */
	private String[] start = new String[50];

	/**
	 * 終了日時
	 */
	private String[] finish = new String[50];

	/**
	 * 予約待ち
	 */
	private int[] wait = new int[500];


	/**
	 * @return レコード数
	 * レコード数を取得する。
	 */
	public int getNumbersRecords() {
		return numbersRecords;
	}

	/**
	 * @param numbersRecords レコード数
	 * レコード数をセットする。
	 */
	public void setNumbersRecords(int numbersRecords) {
		this.numbersRecords = numbersRecords;
	}

	/**
	 * @param i インデックス
	 * @return イベントID
	 * イベントIDを取得する。
	 */
	public int getEventId(int i) {
		return eventId[i-1];
	}

	/**
	 * @param i インデックス
	 * @param eventId イベントID
	 * イベントIDをセットする。
	 */
	public void setEventId(int i,int eventId) {
		this.eventId[i-1]=eventId;
	}

	/**
	 * @param i インデックス
	 * @return ユーザーID
	 * ユーザーIDを取得する。
	 */
	public int getStaffId(int i) {
		return staffId[i-1];
	}

	/**
	 * @param i インデックス
	 * @param userId ユーザーID
	 * ユーザーIDをセットする。
	 */
	public void setStaffId(int i,int staffId) {
		this.staffId[i-1]=staffId;
	}

	/**
	 * @param i インデックス
	 * @return 機材ID
	 * 機材IDを取得する。
	 */
	public int getEquipmentId(int i) {
		return equipmentId[i-1];
	}

	/**
	 * @param i インデックス
	 * @param equipmentId 機材ID
	 * 機材IDをセットする。
	 */
	public void setEquipmentId(int i,int equipmentId) {
		this.equipmentId[i-1]=equipmentId;
	}

	/**
	 * @param i インデックス
	 * @return レンタルID
	 * レンタルIDを取得する。
	 */
	public int getRentalId(int i) {
		return rentalId[i-1];
	}

	/**
	 * @param i インデックス
	 * @param rentalId レンタルID
	 * レンタルIDをセットする。
	 */
	public void setRentalId(int i,int rentalId) {
		this.rentalId[i-1]=rentalId;
	}

	/**
	 * @param i インデックス
	 * @return タイトル
	 * タイトルを取得する。
	 */
	public String getTitle(int i) {
		return title[i-1];
	}

	/**
	 * @param i インデックス
	 * @param title タイトル
	 * タイトルをセットする。
	 */
	public void setTitle(int i,String title) {
		this.title[i-1]=title;
	}

	/**
	 * @param i インデックス
	 * @return 開始日時
	 * 開始日時を取得する。
	 */
	public String getStart(int i) {
		return start[i-1];
	}

	/**
	 * @param i インデックス
	 * @param start 開始日時
	 * 開始日時をセットする。
	 */
	public void setStart(int i,String start) {
		this.start[i-1]=start;
	}

	/**
	 * @param i インデックス
	 * @return 終了日時
	 * 終了日時を取得する。
	 */
	public String getFinish(int i) {
		return finish[i-1];
	}

	/**
	 * @param i インデックス
	 * @param finish 終了日時
	 * 終了日時をセットする。
	 */
	public void setFinish(int i,String finish) {
		this.finish[i-1]=finish;
	}


	/**
	 * @param i インデックス
	 * @return フラッグ
	 * フラッグを取得する。
	 */
	public int getFlag(int i) {
		return flag[i-1];
	}

	/**
	 * @param i インデックス
	 * @param flag フラッグ
	 * フラッグをセットする。
	 */
	public void setFlag(int i,int flag) {
		this.flag[i-1]=flag;
	}


	/**
	 * @param i インデックス
	 * @return 区分
	 * 区分を取得する。
	 */
	public int getDivision(int i) {
		return division[i-1];
	}

	/**
	 * @param i インデックス
	 * @param division
	 * 区分をセットする。
	 */
	public void setDivision(int i,int division) {
		this.division[i-1] = division;
	}

	/**
	 * @return 予約待ち
	 * 予約待ちを取得する。
	 */
	public int getWait(int i) {
		return wait[i-1];
	}

	/**
	 * 予約待ちをセットする。
	 */
	public void setWait() {
		for(int i=0; i<this.numbersRecords; i++) {
			if(flag[i]==0) {
				wait[equipmentId[i]-1]++;
			}
		}
	}
}
