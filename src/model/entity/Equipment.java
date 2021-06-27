package model.entity;

/**
 * 機材モデルクラス。
 */
public class Equipment {


	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * レコードID
	 */
	private int[] equipmentId = new int[50];

	/**
	 * レコードID
	 */
	private int[] staffId = new int[50];

	/**
	 * 機材名
	 */
	private String[] name = new String[50];

	/**
	 * 区分
	 */
	private int[] division = new int[50];

	/**
	 * CPU
	 */
	private String[] cpu = new String[50];

	/**
	 * メモリ
	 */
	private String[] memory = new String[50];

	/**
	 * ボリューム
	 */
	private String[] volume = new String[50];

	/**
	 * 購入日
	 */
	private String[] purchase = new String[50];

	/**
	 * 貸し出しフラッグ
	 */
	private int[] flag = new int[50];

	/**
	 * 最終更新日
	 */
	private String[] updDate = new String[50];

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
	 * @return レコードID
	 * レコードIDを取得する。
	 */
	public int getEquipmentId(int i) {
		return equipmentId[i-1];
	}

	/**
	 * レコードIDをセットする。
	 */
	public void setEquipmentId(int i, int equipmentId) {
		this.equipmentId[i-1]=equipmentId;
	}

	/**
	 * @return ユーザーID
	 * ユーザーIDを取得する。
	 */
	public int getStaffId(int i) {
		return staffId[i-1];
	}

	/**
	 * ユーザーIDをセットする。
	 */
	public void setStaffId(int i, int staffId) {
		this.staffId[i-1]=staffId;
	}

	/**
	 * @return 機材名
	 * 機材名を取得する。
	 */
	public String getName(int i) {
		return name[i-1];
	}

	/**
	 * 機材名をセットする。
	 */
	public void setName(int i, String name) {
		this.name[i-1]=name;
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
	 * @return
	 * CPUを取得する。
	 */
	public String getCPU(int i) {
		return cpu[i-1];
	}

	/**
	 * CPUをセットする。
	 */
	public void setCPU(int i, String cpu) {
		this.cpu[i-1]=cpu;
	}

	/**
	 * @return
	 * メモリを取得する。
	 */
	public String getMemory(int i) {
		return memory[i-1];
	}

	/**
	 * メモリをセットする。
	 */
	public void setMemory(int i, String memory) {
		this.memory[i-1]=memory;
	}

	/**
	 * @return
	 * ボリュームを取得する。
	 */
	public String getVolume(int i) {
		return volume[i-1];
	}

	/**
	 * ボリュームをセットする。
	 */
	public void setVolume(int i, String volume) {
		this.volume[i-1]=volume;
	}

	/**
	 * @return
	 * 購入日を取得する。
	 */
	public String getPurchase(int i) {
		return purchase[i-1];
	}

	/**
	 * 購入日をセットする。
	 */
	public void setPurchase(int i, String purchase) {
		this.purchase[i-1]=purchase;
	}

	/**
	 * @return フラッグ
	 * フラッグを取得する。
	 */
	public int getFlag(int i) {
		return flag[i-1];
	}

	/**
	 * フラッグをセットする。
	 */
	public void setFlag(int i, int flag) {
		this.flag[i-1]=flag;
	}

	/**
	 * @return 最終更新日
	 * 最終更新日を取得する。
	 */
	public String getUpdDate(int i) {
		return updDate[i-1];
	}

	/**
	 * 最終更新日をセットする。
	 */
	public void setUpdDate(int i, String updDate) {
		this.updDate[i-1]=updDate;
	}
}
