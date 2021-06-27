package model.entity;

/**
 * 掲示板モデルクラス。
 */
public class Board {


	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * レコードID
	 */
	private int[] boardId = new int[50];

	/**
	 * タイトル
	 */
	private String[] subject = new String[50];

	/**
	 * 本文
	 */
	private String[] content = new String[50];

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
	public int getBoardId(int i) {
		return boardId[i-1];
	}

	/**
	 * レコードIDをセットする。
	 */
	public void setBoardId(int i, int boardId) {
		this.boardId[i-1]=boardId;
	}


	/**
	 * @return タイトル
	 * タイトルを取得する。
	 */
	public String getSubject(int i) {
		return subject[i-1];
	}

	/**
	 * タイトルをセットする。
	 */
	public void setSubject(int i, String subject) {
		this.subject[i-1]=subject;
	}

	/**
	 * @return 本文
	 * 本文を取得する。
	 */
	public String getContent(int i) {
		return content[i-1];
	}

	/**
	 * 本文をセットする。
	 */
	public void setContent(int i, String content) {
		this.content[i-1]=content;
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
