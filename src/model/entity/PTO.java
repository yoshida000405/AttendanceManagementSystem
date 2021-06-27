package model.entity;

/**
 * PTOモデルクラス。
 */
public class PTO {


	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * レコードID
	 */
	private int[] ptoId = new int[300];

	/**
	 * ユーザーID
	 */
	private int[] staffId = new int[300];

	/**
	 * 取得日数
	 */
	private double[] ptoNumber = new double[300];

	/**
	 * 申請年
	 */
	private int[] requestYear = new int[300];

	/**
	 * 申請月
	 */
	private int[] requestMonth = new int[300];

	/**
	 * 申請日
	 */
	private int[] requestDay = new int[300];

	/**
	 * 対象年
	 */
	private int[] targetYear = new int[300];

	/**
	 * 対象月
	 */
	private int[] targetMonth = new int[300];

	/**
	 * 対象日
	 */
	private int[] targetDay = new int[300];

	/**
	 * 区分
	 */
	private int[] division = new int[300];

	/**
	 * 取得理由
	 */
	private String[] reason = new String[300];

	/**
	 * 処理フラッグ
	 */
	private int[] flag = new int[300];

	/**
	 * 許可待ち
	 */
	private int[] wait = new int[300];

	/**
	 * 許可済み
	 */
	private int[] done = new int[300];

	/**
	 * 最終更新日
	 */
	private String[] lastUpdate = new String[300];

	/**
	 * 重複数
	 */
	private double[] number = new double[300];

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
	 * @return PTOID
	 * PTOIDを取得する。
	 */
	public int getPTOId(int i) {
		return ptoId[i-1];
	}

	/**
	 * PTOIDをセットする。
	 */
	public void setPTOId(int i, int ptoId) {
		this.ptoId[i-1]=ptoId;
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
	 * @return 取得日数
	 * 取得日数を取得する。
	 */
	public double getPTONumber(int i) {
		return ptoNumber[i-1];
	}

	/**
	 * 取得日数をセットする。
	 */
	public void setPTONumber(int i, double ptoNumber) {
		this.ptoNumber[i-1]=ptoNumber;
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
	 * @return 対象年
	 * 対象年を取得する。
	 */
	public int getTargetYear(int i) {
		return targetYear[i-1];
	}

	/**
	 * 対象年をセットする。
	 */
	public void setTargetYear(int i, int targetYear) {
		this.targetYear[i-1]=targetYear;
	}

	/**
	 * @return 対象月
	 * 対象月を取得する。
	 */
	public int getTargetMonth(int i) {
		return targetMonth[i-1];
	}

	/**
	 * 対象月をセットする。
	 */
	public void setTargetMonth(int i, int targetMonth) {
		this.targetMonth[i-1]=targetMonth;
	}

	/**
	 * @return 対象日
	 * 対象日を取得する。
	 */
	public int getTargetDay(int i) {
		return targetDay[i-1];
	}

	/**
	 * 対象日をセットする。
	 */
	public void setTargetDay(int i, int targetDay) {
		this.targetDay[i-1]=targetDay;
	}

	/**
	 * @return 区分
	 * 区分を取得する。
	 */
	public int getDivision(int i) {
		return division[i-1];
	}

	/**
	 * 区分をセットする。
	 */
	public void setDivision(int i, int division) {
		this.division[i-1]=division;
	}

	/**
	 * @return 取得理由
	 * 取得理由を取得する。
	 */
	public String getReason(int i) {
		return reason[i-1];
	}

	/**
	 * 取得理由をセットする。
	 */
	public void setReason(int i, String reason) {
		this.reason[i-1]=reason;
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
				wait[staffId[i]-1]++;
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
				done[staffId[i]-1]++;
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

	/**
	 * @return 重複数
	 * 重複数を取得する。
	 */
	public double getNUMBER(int i) {
		return number[i-1];
	}

	/**
	 * 重複数をセットする。
	 */
	public void setNUMBER(int i, double number) {
		this.number[i-1]=number;
	}
}
