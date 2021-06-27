package model.entity;

/**
 * 月情報モデルクラス。
 */
public class Month {
	/**
	 * 平日数
	 */
	private int[] normalDay = new int[12];

	/**
	 * 処理チェク
	 */
	private int[] check = new int[12];

	/**
	 * @return 処理チェク
	 * 処理チェクを取得する。
	 */
	public int getNormalDay(int i) {
		return normalDay[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param check 処理チェク
	 * レコードのIdをセットする。
	 */
	public void setNormalDay(int i, int normalDay) {
		this.normalDay[i - 1] = normalDay;
	}

	/**
	 * @return 処理チェク
	 * 処理チェクを取得する。
	 */
	public int getCheck(int i) {
		return check[i - 1];
	}

	/**
	 * @param i インデックス
	 * @param check 処理チェク
	 * レコードのIdをセットする。
	 */
	public void setCheck(int i, int check) {
		this.check[i - 1] = check;
	}
}
