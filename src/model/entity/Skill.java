package model.entity;

/**
 * スキルシートモデルクラス。
 */
public class Skill {

	/**
	 * レコードのId
	 */
	private int[] recordId = new int[50];
	/**
	/**
	 * スタッフのId
	 */
	private int[] staffId = new int[50];
	/**
	/**
	 * プロジェクト名
	 */
	private String[] projectName = new String[50];
	/**
	/**
	 * 業務概要
	 */
	private String[] bussinessOverview = new String[50];
	/**
	 * 役割
	 */
	private int[] role = new int[50];
	/**
	 * 規模
	 */
	private int[] scale = new int[50];
	/**
	 * サーバ/OS
	 */
	private String[] server_OS = new String[50];
	/**
	 * DB
	 */
	private String[] db = new String[50];
	/**
	 * ツール
	 */
	private String[] tool = new String[50];
	/**
	 * 使用言語
	 */
	private String[] useLanguage = new String[50];
	/**
	 * その他
	 */
	private String[] other = new String[50];

	/**
	 * @return スタッフのId
	 * スタッフのIdを取得する。
	 */
	public int getStaffId(int i) {
		return staffId[i-1];
	}

	/**
	 * @param staffId スタッフのId
	 * スタッフのIdをセットする。
	 */
	public void setStaffId(int i, int staffId) {
		this.staffId[i-1]= staffId;
	}

	/**
	 * @return レコードのId
	 * レコードのIdを取得する。
	 */
	public int getRecordId(int i) {
		return recordId[i-1];
	}

	/**
	 * @param recordId レコードのId
	 * レコードのIdをセットする。
	 */
	public void setRecordId(int i, int recordId) {
		this.recordId[i-1]= recordId;
	}

	/**
	 * @return プロジェクト名
	 * プロジェクト名を取得する。
	 */
	public String getProjectName(int i) {
		return this.projectName[i-1];
	}

	/**
	 * @param projectName プロジェクト名
	 * プロジェクト名をセットする。
	 */
	public void setProjectName(int i, String projectName) {
		this.projectName[i-1]= projectName;
	}

	/**
	 * @return 業務概要
	 * 業務概要を取得する。
	 */
	public String getBussinessOverview(int i) {
		return this.bussinessOverview[i-1];
	}

	/**
	 * @param bussinessOverview 業務概要
	 * 業務概要をセットする。
	 */
	public void setBussinessOverview(int i, String bussinessOverview) {
		this.bussinessOverview[i-1]= bussinessOverview;
	}

	/**
	 * @return 役割
	 * 役割を取得する。
	 */
	public int getRole(int i) {
		return this.role[i-1];
	}

	/**
	 * @param role 役割
	 * 役割をセットする。
	 */
	public void setRole(int i, int role) {
		this.role[i-1]= role;
	}

	/**
	 * @return 規模
	 * 規模を取得する。
	 */
	public int getScale(int i) {
		return this.scale[i-1];
	}

	/**
	 * @param scale 規模
	 * 規模をセットする。
	 */
	public void setScale(int i, int scale) {
		this.scale[i-1]= scale;
	}

	/**
	 * @return サーバー/OS
	 * サーバー/OSを取得する。
	 */
	public String getServer_OS(int i) {
		return this.server_OS[i-1];
	}

	/**
	 * @param server_OS サーバー/OS
	 * サーバー/OSをセットする。
	 */
	public void setServer_OS(int i, String server_OS) {
		this.server_OS[i-1]= server_OS;
	}

	/**
	 * @return DB
	 * DBを取得する。
	 */
	public String getDB(int i) {
		return this.db[i-1];
	}

	/**
	 * @param  db DB
	 * DBをセットする。
	 */
	public void setDB(int i, String db) {
		this.db[i-1]= db;
	}

	/**
	 * @return ツール
	 * ツールを取得する。
	 */
	public String getTool(int i) {
		return this.tool[i-1];
	}

	/**
	 * @param tool ツール
	 * ツールをセットする。
	 */
	public void setTool(int i, String tool) {
		this.tool[i-1]= tool;
	}

	/**
	 * @return 使用言語
	 * 使用言語を取得する。
	 */
	public String getUseLanguage(int i) {
		return this.useLanguage[i-1];
	}

	/**
	 * @param useLanguage 使用言語
	 * 使用言語をセットする。
	 */
	public void setUseLanguage(int i, String useLanguage) {
		this.useLanguage[i-1]= useLanguage;
	}

	/**
	 * @return その他
	 * その他を取得する。
	 */
	public String getOther(int i) {
		return this.other[i-1];
	}

	/**
	 * @param other 処理その他
	 * その他をセットする。
	 */
	public void setOther(int i, String other) {
		this.other[i-1]= other;
	}
}
