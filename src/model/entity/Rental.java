package model.entity;

/**
 * 機材予約モデルクラス。
 */
public class Rental {


	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * レコードID
	 */
	private int[] rentalId = new int[50];

	/**
	 * 機材ーID
	 */
	private int[] equipmentId = new int[50];

	/**
	 * ユーザーID
	 */
	private int[] staffId = new int[50];

	/**
	 * 申請年
	 */
	private int[] requestYear = new int[50];

	/**
	 * 申請月
	 */
	private int[] requestMonth = new int[50];

	/**
	 * 申請日
	 */
	private int[] requestDay = new int[50];

	/**
	 * 開始年
	 */
	private int[] startYear = new int[50];

	/**
	 * 開始月
	 */
	private int[] startMonth = new int[50];

	/**
	 * 開始日
	 */
	private int[] startDay = new int[50];

	/**
	 * 返却年
	 */
	private int[] finishYear = new int[50];

	/**
	 * 返却月
	 */
	private int[] finishMonth = new int[50];

	/**
	 * 返却日
	 */
	private int[] finishDay = new int[50];

	/**
	 * 期間
	 */
	private int[] period = new int[50];

	/**
	 * 処理フラッグ
	 */
	private int[] flag = new int[50];

	/**
	 * 許可待ち
	 */
	private int[] wait = new int[50];

	/**
	 * 許可済み
	 */
	private int[] done = new int[50];

	/**
	 * 最終更新日
	 */
	private String[] lastUpdate = new String[50];

	/**
	 * @return レコード数
	 * レコード数を取得する。
	 */
	public int getNumbersRecords() {
		return numbersRecords;
	}

	/**
	 * レコード数をセットする。
	 */
	public void setNumbersRecords(int numbersRecords) {
		this.numbersRecords=numbersRecords;
	}

	/**
	 * @return RentalID
	 * RentalIDを取得する。
	 */
	public int getRentalId(int i) {
		return rentalId[i-1];
	}

	/**
	 * RentalIDをセットする。
	 */
	public void setRentalId(int i, int rentalId) {
		this.rentalId[i-1]=rentalId;
	}

	/**
	 * @return equipmentId
	 * equipmentIdを取得する。
	 */
	public int getEquipmentId(int i) {
		return equipmentId[i-1];
	}

	/**
	 * equipmentIdをセットする。
	 */
	public void setEquipmentId(int i, int equipmentId) {
		this.equipmentId[i-1]=equipmentId;
	}

	/**
	 * @return staffId
	 * staffIdを取得する。
	 */
	public int getStaffId(int i) {
		return staffId[i-1];
	}

	/**
	 * staffIdをセットする。
	 */
	public void setStaffId(int i, int staffId) {
		this.staffId[i-1]=staffId;
	}

	/**
	 * @return 申請年
	 * 申請年を取得する。
	 */
	public int getRequestYear(int i) {
		return requestYear[i-1];
	}

	/**
	 * 申請年をセットする。
	 */
	public void setRequestYear(int i, int requestYear) {
		this.requestYear[i-1]=requestYear;
	}

	/**
	 * @return 申請月
	 * 申請月を取得する。
	 */
	public int getRequestMonth(int i) {
		return requestMonth[i-1];
	}

	/**
	 * 申請月をセットする。
	 */
	public void setRequestMonth(int i, int requestMonth) {
		this.requestMonth[i-1]=requestMonth;
	}

	/**
	 * @return 申請日
	 * 申請日を取得する。
	 */
	public int getRequestDay(int i) {
		return requestDay[i-1];
	}

	/**
	 * 申請日をセットする。
	 */
	public void setRequestDay(int i, int requestDay) {
		this.requestDay[i-1]=requestDay;
	}

	/**
	 * @return 開始年
	 * 開始年を取得する。
	 */
	public int getStartYear(int i) {
		return startYear[i-1];
	}

	/**
	 * 開始年をセットする。
	 */
	public void setStartYear(int i, int startYear) {
		this.startYear[i-1]=startYear;
	}

	/**
	 * @return 開始月
	 * 開始月を取得する。
	 */
	public int getStartMonth(int i) {
		return startMonth[i-1];
	}

	/**
	 * 開始月をセットする。
	 */
	public void setStartMonth(int i, int startMonth) {
		this.startMonth[i-1]=startMonth;
	}

	/**
	 * @return 開始日
	 * 開始日を取得する。
	 */
	public int getStartDay(int i) {
		return startDay[i-1];
	}

	/**
	 * 開始日をセットする。
	 */
	public void setStartDay(int i, int startDay) {
		this.startDay[i-1]=startDay;
	}

	/**
	 * @return 返却年
	 * 返却年を取得する。
	 */
	public int getFinishYear(int i) {
		return finishYear[i-1];
	}

	/**
	 * 返却年をセットする。
	 */
	public void setFinishYear(int i, int finishYear) {
		this.finishYear[i-1]=finishYear;
	}

	/**
	 * @return 返却月
	 * 返却月を取得する。
	 */
	public int getFinishMonth(int i) {
		return finishMonth[i-1];
	}

	/**
	 * 返却月をセットする。
	 */
	public void setFinishMonth(int i, int finishMonth) {
		this.finishMonth[i-1]=finishMonth;
	}

	/**
	 * @return 対象日
	 * 返却日を取得する。
	 */
	public int getFinishDay(int i) {
		return finishDay[i-1];
	}

	/**
	 * 返却日をセットする。
	 */
	public void setFinishDay(int i, int finishDay) {
		this.finishDay[i-1]=finishDay;
	}


	/**
	 * @return 期間
	 * 期間を取得する。
	 */
	public int getPeriod(int i) {
		return period[i-1];
	}

	/**
	 * 期間をセットする。
	 */
	public void setPeriod(int i, int period) {
		this.period[i-1]=period;
	}

	/**
	 * @return 処理フラッグ
	 * 処理フラッグを取得する。
	 */
	public int getFlag(int i) {
		return flag[i-1];
	}

	/**
	 * 処理フラッグをセットする。
	 */
	public void setFlag(int i, int flag) {
		this.flag[i-1]=flag;
	}

	/**
	 * @return 許可待ち
	 * 許可待ちを取得する。
	 */
	public int getWait(int i) {
		return wait[i-1];
	}

	/**
	 * 許可待ちをセットする。
	 */
	public void setWait() {
		for(int i=0; i<this.numbersRecords; i++) {
			if(flag[i]==0) {
				wait[equipmentId[i]-1]++;
			}
		}
	}

	/**
	 * @return 許可待ち
	 * 許可待ちを取得する。
	 */
	public int getDone(int i) {
		return done[i-1];
	}

	/**
	 * 許可待ちをセットする。
	 */
	public void setDone() {
		for(int i=0; i<this.numbersRecords; i++) {
			if(flag[i]==1) {
				done[equipmentId[i]-1]++;
			}
		}
	}

	/**
	 * @return 最終更新日
	 * 最終更新日を取得する。
	 */
	public String getLastUpdate(int i) {
		return lastUpdate[i-1];
	}

	/**
	 * @param i インデックス
	 * @param lastUpdate 最終更新日
	 * 最終更新日をセットする。
	 */
	public void setLastUpdate(int i,String lastUpdate) {
		this.lastUpdate[i-1]= lastUpdate;
	}
}
