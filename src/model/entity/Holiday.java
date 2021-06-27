package model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 休日モデルクラス。
 */
public class Holiday {

	/**
	 * 祝日リスト
	 */
	private List<Integer> holiday = new ArrayList<>();

	/**
	 * 祝日数
	 */
	private int numbersHoliday;

	/**
	 * @return 各祝日
	 * 祝日を取得する。
	 */
	public String getHoliday(int i) {
		return String.valueOf(holiday.get(i-1));
	}

	/**
	 * @param i インデックス
	 * @param holiday 祝日
	 * 祝日をセットする。
	 */
	public void setHoliday(int i,int holiday) {
		this.holiday.add(holiday);
	}

	/**
	 * @return 祝日数
	 * 祝日数を取得する。
	 */
	public int getNumbersHoliday() {
		return numbersHoliday;
	}

	/**
	 * 祝日数をセットする。
	 */
	public void setNumbersHoliday() {
		this.numbersHoliday=holiday.size();
	}
}
