package model.entity;

/**
 * 管理機能用モデルクラス。
 */
public class Managed {
	/**
	 * スタッフ名
	 */
	private String[] name = new String[50];
	/**

	/**
	 * スタッフID
	 */
	private int[] id = new int[50];
	/**

	/**
	 * ポジション先
	 */
	private int[] position = new int[50];
	/**

	/**
	 * 稼働日数
	 */
	private double[] operatingDays = new double[50];
	/**

	/**
	 * 休日日数
	 */
	private int[] holiday = new int[50];
	/**

	/**
	 * 代休日数
	 */
	private int[] substitute = new int[50];
	/**

	/**
	 * 有給日数
	 */
	private double[] paid = new double[50];
	/**

	/**
	 * 欠勤日数
	 */
	private int[] absence = new int[50];
	/**

	/**
	 * 明休日数
	 */
	private int[] overNight  = new int[50];
	/**

	/**
	 * 稼働時間
	 */
	private double[] operatingTime = new double[50];
	/**

	/**
	 * 旅費交通費合計
	 */
	private int[] transportation = new int[50];

	/**
	 * 消耗品費合計
	 */
	private int[] expendables = new int[50];

	/**
	 * 会議費合計
	 */
	private int[] meeting = new int[50];

	/**
	 * 通信費合計
	 */
	private int[] communications = new int[50];

	/**
	 * 支払手数料合計
	 */
	private int[] commission = new int[50];

	/**
	 * 交際費合計
	 */
	private int[] social = new int[50];

	/**
	 * その他費用合計
	 */
	private int[] other = new int[50];

	/**
	 * 総計
	 */
	private int[] sum = new int[50];

	/**
	 * @return スタッフ名
	 * スタッフ名を取得する。
	 */
	public String getName(int i) {
		return this.name[i-1];
	}

	/**
	 * @param name スタッフ名
	 * スタッフ名をセットする。
	 */
	public void setName(int i, String name) {
		this.name[i-1] = name;
	}

	/**
	 * @return スタッフID
	 * スタッフIDを取得する。
	 */
	public int getId(int i) {
		return this.id[i-1];
	}

	/**
	 * @param id スタッフID
	 * スタッフIDをセットする。
	 */
	public void setId(int i, int id) {
		this.id[i-1]= id;
	}

	/**
	 * @return ポジション先
	 * ポジション先を取得する。
	 */
	public int getPosition(int i) {
		return this.position[i-1];
	}

	/**
	 * @param position ポジション先
	 * ポジション先をセットする。
	 */
	public void setPosition(int i, int position) {
		this.position[i-1] = position;
	}

	/**
	 * @return 稼働日数
	 * 稼働日数を取得する。
	 */
	public double getOperatingDays(int i) {
		return this.operatingDays[i-1];
	}

	/**
	 * @param operatingDays 稼働日数
	 * 稼働日数をセットする。
	 */
	public void setOperatingDays(int i, double operatingDays) {
		this.operatingDays[i-1] = operatingDays;
	}

	/**
	 * @return 休日日数
	 * 休日日数を取得する。
	 */
	public int getHoliday(int i) {
		return this.holiday[i-1];
	}

	/**
	 * @param holiday 休日日数
	 * 休日日数をセットする。
	 */
	public void setHoliday(int i, int holiday) {
		this.holiday[i-1] = holiday;
	}

	/**
	 * @return 代休日数
	 * 代休日数を取得する。
	 */
	public int getSubstitute(int i) {
		return this.substitute[i-1];
	}

	/**
	 * @param substitute 代休日数
	 * 代休日数をセットする。
	 */
	public void setSubstitute(int i, int substitute) {
		this.substitute[i-1] = substitute;
	}

	/**
	 * @return 有給日数
	 * 有給日数を取得する。
	 */
	public double getPaid(int i) {
		return this.paid[i-1];
	}

	/**
	 * @param paid 有給日数
	 * 有給日数をセットする。
	 */
	public void setPaid(int i, double paid) {
		this.paid[i-1] = paid;
	}

	/**
	 * @return 欠勤日数
	 * 欠勤日数を取得する。
	 */
	public int getAbsence(int i) {
		return this.absence[i-1];
	}

	/**
	 * @param overNight 明休日数
	 * 明休日数をセットする。
	 */
	public void setOverNight(int i, int overNight) {
		this.overNight[i-1] = overNight;
	}

	/**
	 * @return 明休日数
	 * 明休日数を取得する。
	 */
	public int getOverNight(int i) {
		return this.overNight[i-1];
	}

	/**
	 * @param absence 欠勤日数
	 * 欠勤日数をセットする。
	 */
	public void setAbsence(int i, int absence) {
		this.absence[i-1] = absence;
	}

	/**
	 * @return 稼働時間
	 * 稼働時間を取得する。
	 */
	public double getOperatingTime(int i) {
		return this.operatingTime[i-1];
	}

	/**
	 * @param operatingTime 稼働時間
	 * 稼働時間をセットする。
	 */
	public void setOperatingTime(int i, double operatingTime) {
		this.operatingTime[i-1] = operatingTime;
	}

	/**
	 * @return 旅費交通費
	 * 旅費交通費を取得する。
	 */
	public int getTransportation(int i) {
		return this.transportation[i-1];
	}

	/**
	 * @param transportation 旅費交通費
	 * 旅費交通費をセットする。
	 */
	public void setTransportation(int i, int transportation) {
		this.transportation[i-1]= transportation;
	}

	/**
	 * @return 消耗品費
	 * 消耗品費を取得する。
	 */
	public int getExpendables(int i) {
		return this.expendables[i-1];
	}

	/**
	 * @param expendables 消耗品費
	 * 消耗品費をセットする。
	 */
	public void setExpendables(int i, int expendables) {
		this.expendables[i-1]= expendables;
	}

	/**
	 * @return 会議費
	 * 会議費を取得する。
	 */
	public int getMeeting(int i) {
		return this.meeting[i-1];
	}

	/**
	 * @param meeting 会議費
	 * 会議費をセットする。
	 */
	public void setMeeting(int i, int meeting) {
		this.meeting[i-1]= meeting;
	}

	/**
	 * @return 通信費
	 * 通信費を取得する。
	 */
	public int getCommunications(int i) {
		return this.communications[i-1];
	}

	/**
	 * @param communications 通信費
	 * 通信費をセットする。
	 */
	public void setCommunications(int i, int communications) {
		this.communications[i-1]= communications;
	}

	/**
	 * @return 支払手数料
	 * 支払手数料を取得する。
	 */
	public int getCommission(int i) {
		return this.commission[i-1];
	}

	/**
	 * @param commission 支払手数料
	 * 支払手数料をセットする。
	 */
	public void setCommission(int i, int commission) {
		this.commission[i-1]= commission;
	}

	/**
	 * @return 交際費
	 * 交際費を取得する。
	 */
	public int getSocial(int i) {
		return this.social[i-1];
	}

	/**
	 * @param social 交際費
	 * 交際費をセットする。
	 */
	public void setSocial(int i, int social) {
		this.social[i-1]= social;
	}

	/**
	 * @return その他費用
	 * その他費用を取得する。
	 */
	public int getOther(int i) {
		return this.other[i-1];
	}

	/**
	 * @param other その他費用
	 * その他費用をセットする。
	 */
	public void setOther(int i, int other) {
		this.other[i-1]= other;
	}

	/**
	 * @return 総計
	 * 総計を取得する。
	 */
	public int getSum(int i) {
		return this.sum[i-1];
	}

	/**
	 * @param sum 総計
	 * 総計をセットする。
	 */
	public void setSum(int i, int sum) {
		this.sum[i-1]= sum;
	}

}
