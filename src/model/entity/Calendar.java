package model.entity;

/**
 * カレンダーモデルクラス。
 */
public class Calendar {

	public Calendar(int id, String title, String start, String end){
		setId(id);
		setTitle(title);
		setStart(start);
		setEnd(end);
	}


	/**
	 * イベントID
	 */
	private int id;

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 開始日時
	 */
	private String start;

	/**
	 * 終了日時
	 */
	private String end;

	/**
	 * @return イベントID
	 * イベントIDを取得する。
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id イベントID
	 * イベントIDをセットする。
	 */
	public void setId(int id) {
		this.id=id;
	}

	/**
	 * @param i インデックス
	 * @return タイトル
	 * タイトルを取得する。
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title タイトル
	 * タイトルをセットする。
	 */
	public void setTitle(String title) {
		this.title=title;
	}

	/**
	 * @return 開始日時
	 * 開始日時を取得する。
	 */
	public String getStart() {
		return this.start;
	}

	/**
	 * @param start 開始日時
	 * 開始日時をセットする。
	 */
	public void setStart(String start) {
		this.start=start;
	}

	/**
	 * @return 終了日時
	 * 終了日時を取得する。
	 */
	public String getEnd() {
		return this.end;
	}

	/**
	 * @param end 終了日時
	 * 終了日時をセットする。
	 */
	public void setEnd(String end) {
		this.end=end;
	}

}
