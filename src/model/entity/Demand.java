package model.entity;

/**
 * 請求書モデルクラス。
 */
public class Demand {

	/**
	 * 追加分含めたレコード数
	 */
	private int numbersRecords;

	/**
	 * レコードのId
	 */
	private int[] recordId = new int[50];

	/**
	/**
	 * 対象日
	 */
	private int[] targetDay = new int[50];

	/**
	 * 申請月
	 */
	private int[] aplicationMonth = new int[50];

	/**
	 * 申請日
	 */
	private int[] aplicationDay = new int[50];

	/**
	 * 申請金額
	 */
	private int[] aplicationAmount = new int[50];

	/**
	 * 口数
	 */
	private int[] multi = new int[50];

	/**
	 * 領収書フラッグ
	 */
	private int[] recipt = new int[50];

	/**
	 * 支払先
	 */
	private String[] to = new String[50];

	/**
	 * 摘要
	 */
	private String[] description = new String[50];

	/**
	 * 区分
	 */
	private int[] division = new int[50];

	/**
	 * 詳細
	 */
	private String[] remarks = new String[50];

	/**
	 * 処理フラグ
	 */
	private int[] flag = new int[50];

	/**
	 * 最終更新日
	 */
	private String lastUpdate;

	/**
	 * 旅費交通費合計
	 */
	private int transportation;

	/**
	 * 消耗品費合計
	 */
	private int expendables;

	/**
	 * 会議費合計
	 */
	private int meeting;

	/**
	 * 通信費合計
	 */
	private int communications;

	/**
	 * 支払手数料合計
	 */
	private int commission;

	/**
	 * 交際費合計
	 */
	private int social;

	/**
	 * その他費用合計
	 */
	private int other;

	/**
	 * 総計
	 */
	private int sum;

	/**
	 * 管理者メモ
	 */
	private String[] memo = new String[50];

	/**
	 * @return 追加分含めたレコード数
	 * レコード数を取得する。
	 */
	public int getNumbersRecords() {
		return numbersRecords;
	}

	/**
	 * @param numbersRecords 追加分含めたレコード数
	 * レコード数をセットする。
	 */
	public void setNumbersRecords(int numbersRecords) {
		this.numbersRecords = numbersRecords;
	}

	/**
	 * @return レコードのId
	 * レコードのIdを取得する。
	 */
	public int getRecordId(int i) {
		return recordId[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param recordId レコードのId
	 * レコードのIdをセットする。
	 */
	public void setRecordId(int i, int recordId) {
		this.recordId[i - 1] = recordId;
	}

	/**
	 * @return 対象日
	 * 対象日を取得する。
	 */
	public int getTargetDay(int i) {
		return targetDay[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param targetDay 対象日
	 * 対象日をセットする。
	 */
	public void setTargetDay(int i, int targetDay) {
		this.targetDay[i - 1] = targetDay;
	}

	/**
	 * @return 申請月
	 * 申請月を取得する。
	 */
	public int getAplicationMonth(int i) {
		return aplicationMonth[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param aplicationMonth 申請月
	 * 申請月をセットする。
	 */
	public void setAplicationMonth(int i, int aplicationMonth) {
		this.aplicationMonth[i - 1] = aplicationMonth;
	}

	/**
	 * @return 申請日
	 * 申請日を取得する。
	 */
	public int getAplicationDay(int i) {
		return aplicationDay[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param aplicationDay 申請日
	 * 申請日をセットする。
	 */
	public void setAplicationDay(int i, int aplicationDay) {
		this.aplicationDay[i - 1] = aplicationDay;
	}

	/**
	 * @return 申請金額
	 * 申請金額を取得する。
	 */
	public int getAplicationAmount(int i) {
		return aplicationAmount[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param aplicationAmount 申請金額
	 * 申請金額をセットする。
	 */
	public void setAplicationAmount(int i, int aplicationAmount) {
		this.aplicationAmount[i - 1] = aplicationAmount;
	}

	/**
	 * @return 口数
	 * 口数を取得する。
	 */
	public int getMulti(int i) {
		return multi[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param multi 口数
	 * 口数をセットする。
	 */
	public void setMulti(int i, int multi) {
		this.multi[i - 1] = multi;
	}

	/**
	 * @return 領収書フラッグ
	 * 領収書フラッグを取得する。
	 */
	public int getRecipt(int i) {
		return recipt[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param recipt 領収書フラッグ
	 * 領収書フラッグをセットする。
	 */
	public void setRecipt(int i, int recipt) {
		this.recipt[i - 1] = recipt;
	}

	/**
	 * @return 支払先
	 * 支払先を取得する。
	 */
	public String getTo(int i) {
		return to[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param  to 支払先
	 * 支払先をセットする。
	 */
	public void setTo(int i, String to) {
		this.to[i - 1] = to;
	}

	/**
	 * @return 摘要
	 * 摘要を取得する。
	 */
	public String getDescription(int i) {
		return description[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param  description 摘要
	 * 摘要をセットする。
	 */
	public void setDescription(int i, String description) {
		this.description[i - 1] = description;
	}

	/**
	 * @return 区分
	 * 区分を取得する。
	 */
	public int getDivision(int i) {
		return division[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param aplicationAmount 区分
	 * 区分をセットする。
	 */
	public void setDivision(int i, int division) {
		this.division[i - 1] = division;
	}

	/**
	 * @return 詳細
	 * 詳細を取得する。
	 */
	public String getRemarks(int i) {
		return remarks[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param remarks 詳細
	 * 詳細をセットする。
	 */
	public void setRemarks(int i, String remarks) {
		this.remarks[i - 1] = remarks;
	}

	/**
	 * @return 処理フラグ
	 * 処理フラグを取得する。
	 */
	public int getFlag(int i) {
		return flag[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param flag 処理フラグ
	 * 処理フラグをセットする。
	 */
	public void setFlag(int i, int flag) {
		this.flag[i - 1] = flag;
	}

	/**
	 * @return 最終更新日
	 * 最終更新日を取得する。
	 */
	public String getLastUpdate() {
		return this.lastUpdate;
	}

	/**
	 * @param remarks 最終更新日
	 * 最終更新日をセットする。
	 */
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return 旅費交通費
	 * 旅費交通費を取得する。
	 */
	public int getTransportation() {
		return this.transportation;
	}

	/**
	 * @param transportation 旅費交通費
	 * 旅費交通費をセットする。
	 */
	public void setTransportation() {
		this.transportation = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 1) {
				this.transportation += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 消耗品費
	 * 消耗品費を取得する。
	 */
	public int getExpendables() {
		return this.expendables;
	}

	/**
	 * @param expendables 消耗品費
	 * 消耗品費をセットする。
	 */
	public void setExpendables() {
		this.expendables = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 2) {
				this.expendables += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 会議費
	 * 会議費を取得する。
	 */
	public int getMeeting() {
		return this.meeting;
	}

	/**
	 * @param meeting 交通費
	 * 会議費をセットする。
	 */
	public void setMeeting() {
		this.meeting = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 3) {
				this.meeting += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 通信費
	 * 通信費を取得する。
	 */
	public int getCommunications() {
		return this.communications;
	}

	/**
	 * @param communications 通信費
	 * 通信費をセットする。
	 */
	public void setCommunications() {
		this.communications = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 4) {
				this.communications += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 支払手数料
	 * 支払手数料を取得する。
	 */
	public int getCommission() {
		return this.commission;
	}

	/**
	 * @param commission 支払手数料
	 * 支払手数料をセットする。
	 */
	public void setCommission() {
		this.commission = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 5) {
				this.commission += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 交際費
	 * 交際費を取得する。
	 */
	public int getSocial() {
		return this.social;
	}

	/**
	 * @param social 交際費
	 * 交際費をセットする。
	 */
	public void setSocial() {
		this.social = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 6) {
				this.social += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return その他費用
	 * その他費用を取得する。
	 */
	public int getOther() {
		return this.other;
	}

	/**
	 * @param other その他費用
	 * その他費用をセットする。
	 */
	public void setOther() {
		this.other = 0;
		for (int i = 0; i < division.length; i++) {
			if (division[i] == 7) {
				this.other += (aplicationAmount[i] * multi[i]);
			}
		}
	}

	/**
	 * @return 総計
	 * 総計を取得する。
	 */
	public int getSum() {
		return this.sum;
	}

	/**
	 * @param sum 総計
	 * 総計をセットする。
	 */
	public void setSum() {
		this.sum = this.transportation + this.expendables + this.meeting + this.communications + this.commission
				+ this.social + this.other;
	}

	/**
	 * @return 管理者メモ
	 * 管理者メモを取得する。
	 */
	public String getMemo(int i) {
		return memo[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param memo 管理者メモ
	 * 管理者メモをセットする。
	 */
	public void setMemo(int i, String memo) {
		this.memo[i - 1] = memo;
	}
}
