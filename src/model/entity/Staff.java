package model.entity;

/**
 * ユーザーモデルクラス。
 */
public class Staff {

	/**
	 * 権限
	 */
	private int authority;

	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * ユーザーID
	 */
	private int[] staffId = new int[50];

	/**
	 * ユーザー名(フルネーム)
	 */
	private String[] staffName = new String[50];

	/**
	 * ユーザー名(名前)
	 */
	private String[] firstName = new String[50];

	/**
	 * ユーザー名(苗字)
	 */
	private String[] lastName = new String[50];

	/**
	 * ユーザー名(なまえ)
	 */
	private String[] firstNameKana = new String[50];

	/**
	 * ユーザー名(みょうじ)
	 */
	private String[] lastNameKana = new String[50];

	/**
	 * ユーザー名(Namae)
	 */
	private String[] firstNameEnglish = new String[50];

	/**
	 * ユーザー名(Myoji)
	 */
	private String[] lastNameEnglish = new String[50];

	/**
	 * 性別
	 */
	private int[] gender = new int[50];

	/**
	 * 入社日
	 */
	private String[] hireDate = new String[50];

	/**
	 * メールアドレス
	 */
	private String[] mailAddress = new String[50];

	/**
	 * ユーザーのグループ
	 */
	private String[] group = new String[50];

	/**
	 * ユーザーの所属先
	 */
	private int[] position = new int[50];

	/**
	 * ユーザーのパスワードフラッグ
	 */
	private int[] flag = new int[50];

	/**
	 * 勤務表提出フラッグ
	 */
	private int[] attendanceFlag = new int[50];

	/**
	 * 請求書提出フラッグ
	 */
	private int[] demandFlag = new int[50];

	/**
	 * スキルシート提出フラッグ
	 */
	private int[] skillFlag = new int[50];

	/**
	 * ユーザーのイニシャル
	 */
	private String[] staffNameInitial = new String[50];

	/**
	 * ユーザーの最寄駅
	 */
	private String[] station = new String[50];

	/**
	 * ユーザーの基本始業時間
	 */
	private String[] startTime = new String[50];

	/**
	 * ユーザーの基本終業時間
	 */
	private String[] finishTime = new String[50];

	/**
	 * 有給数
	 */
	private double[] pto = new double[50];

	/**
	 * 有給消費数
	 */
	private double[] ptoConsume = new double[50];


	/**
	 * @return 権限
	 * 権限を取得する。
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * @param authority 権限
	 * 権限をセットする。
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * @return レコード数
	 * レコード数を取得する。
	 */
	public int getNumbersRecords() {
		return numbersRecords;
	}

	/**
	 * @param numbersRecords レコード数
	 * レコード数をセットする。
	 */
	public void setNumbersRecords(int numbersRecords) {
		this.numbersRecords = numbersRecords;
	}

	/**
	 * @param i インデックス
	 * @return ユーザーID
	 * ユーザーIDを取得する。
	 */
	public int getStaffId(int i) {
		return staffId[i-1];
	}

	/**
	 * @param i インデックス
	 * @param userId ユーザーID
	 * ユーザーIDをセットする。
	 */
	public void setStaffId(int i,int staffId) {
		this.staffId[i-1]=staffId;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(フルネーム)
	 * ユーザー名(フルネーム)を取得する。
	 */
	public String getStaffName(int i) {
		return staffName[i-1];
	}

	/**
	 * @param i インデックス
	 * @param staffName ユーザー名(フルネーム)
	 * ユーザー名(フルネーム)をセットする。
	 */
	public void setStaffName(int i,String staffName) {
		this.staffName[i-1]=staffName;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(フルネーム)
	 * ユーザー名(フルネーム)を取得する。
	 */
	public String getStaffNameID(int id) {
		for(int i=0;i<numbersRecords;i++) {
			if(staffId[i]==id) {
				return staffName[i];
			}
		}
		return "NONE";
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(名前)
	 * ユーザー名(名前)を取得する。
	 */
	public String getFirstName(int i) {
		return firstName[i-1];
	}

	/**
	 * @param i インデックス
	 * @param firstName ユーザー名(名前)
	 * ユーザー名(名前)をセットする。
	 */
	public void setFirstName(int i,String firstName) {
		this.firstName[i-1]=firstName;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(苗字)
	 * ユーザー名(苗字)を取得する。
	 */
	public String getLastName(int i) {
		return lastName[i-1];
	}

	/**
	 * @param i インデックス
	 * @param lastName ユーザー名(苗字)
	 * ユーザー名(苗字)をセットする。
	 */
	public void setLastName(int i,String lastName) {
		this.lastName[i-1]=lastName;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(なまえ)
	 * ユーザー名(なまえ)を取得する。
	 */
	public String getFirstNameKana(int i) {
		return firstNameKana[i-1];
	}

	/**
	 * @param i インデックス
	 * @param firstNameKana ユーザー名(なまえ)
	 * ユーザー名(なまえ)をセットする。
	 */
	public void setFirstNameKana(int i,String firstNameKana) {
		this.firstNameKana[i-1]=firstNameKana;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(みょうじ)
	 * ユーザー名(みょうじ)を取得する。
	 */
	public String getLastNameKana(int i) {
		return lastNameKana[i-1];
	}

	/**
	 * @param i インデックス
	 * @param lastNameKana ユーザー名(みょうじ)
	 * ユーザー名(みょうじ)をセットする。
	 */
	public void setLastNameKana(int i,String lastNameKana) {
		this.lastNameKana[i-1]=lastNameKana;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(Namae)
	 * ユーザー名(Namae)を取得する。
	 */
	public String getFirstNameEnglish(int i) {
		return firstNameEnglish[i-1];
	}

	/**
	 * @param i インデックス
	 * @param firstNameEnglish ユーザー名(Namae)
	 * ユーザー名(Namae)をセットする。
	 */
	public void setFirstNameEnglish(int i,String firstNameEnglish) {
		this.firstNameEnglish[i-1]=firstNameEnglish;
	}

	/**
	 * @param i インデックス
	 * @return ユーザー名(Myouji)
	 * ユーザー名(Myouji)を取得する。
	 */
	public String getLastNameEnglish(int i) {
		return lastNameEnglish[i-1];
	}

	/**
	 * @param i インデックス
	 * @param lastNameEnglish ユーザー名(Myouji)
	 * ユーザー名(Myouji)をセットする。
	 */
	public void setLastNameEnglish(int i,String lastNameEnglish) {
		this.lastNameEnglish[i-1]=lastNameEnglish;
	}

	/**
	 * @param i インデックス
	 * @return 性別
	 * 性別を取得する。
	 */
	public int getGender(int i) {
		return gender[i-1];
	}

	/**
	 * @param i インデックス
	 * @param gender 性別
	 * 性別をセットする。
	 */
	public void setGender(int i,int gender) {
		this.gender[i-1]=gender;
	}

	/**
	 * @param i インデックス
	 * @return 入社日
	 * 入社日を取得する。
	 */
	public String getHireDate(int i) {
		return hireDate[i-1];
	}

	/**
	 * @param i インデックス
	 * @param hireDate 入社日
	 * 入社日をセットする。
	 */
	public void setHireDate(int i,String hireDate) {
		this.hireDate[i-1]=hireDate;
	}

	/**
	 * @param i インデックス
	 * @return メールアドレス
	 * メールアドレスを取得する。
	 */
	public String getMailAddress(int i) {
		return mailAddress[i-1];
	}

	/**
	 * @param i インデックス
	 * @param mailAddress メールアドレス
	 * メールアドレスをセットする。
	 */
	public void setMailAddress(int i,String mailAddress) {
		this.mailAddress[i-1]=mailAddress;
	}

	/**
	 * @param i インデックス
	 * @return ユーザーのグループ
	 * ユーザーのグループを取得する。
	 */
	public String getGroup(int i) {
		return group[i-1];
	}

	/**
	 * @param i インデックス
	 * @param finishTime ユーザーのグループ
	 * ユーザーのグループをセットする。
	 */
	public void setGroup(int i,String group) {
		this.group[i-1]=group;
	}

	/**
	 * @param i インデックス
	 * @return 所属先
	 * 現場所属か自社所属かを取得する。
	 */
	public int getPosition(int i) {
		return position[i-1];
	}

	/**
	 * @param i インデックス
	 * @param position 所属先
	 * 所属先をセットする。
	 */
	public void setPosition(int i,int position) {
		this.position[i-1]=position;
	}

	/**
	 * @param i インデックス
	 * @return パスワードフラッグ
	 * パスワードフラッグを取得する。
	 */
	public int getFlag(int i) {
		return flag[i-1];
	}

	/**
	 * @param i インデックス
	 * @param flag パスワードフラッグ
	 * パスワードフラッグをセットする。
	 */
	public void setFlag(int i,int flag) {
		this.flag[i-1]=flag;
	}

	/**
	 * @param i インデックス
	 * @return 勤務表提出フラッグ
	 * 勤務表提出フラッグを取得する。
	 */
	public int getAttendanceFlag(int i) {
		return attendanceFlag[i-1];
	}

	/**
	 * @param i インデックス
	 * @param attendanceFlag
	 * 勤務表提出フラッグをセットする。
	 */
	public void setAttendanceFlag(int i,int attendanceFlag) {
		this.attendanceFlag[i-1] = attendanceFlag;
	}

	/**
	 * @param i インデックス
	 * @return 請求書提出フラッグ
	 * 請求書提出フラッグを取得する。
	 */
	public int getDemandFlag(int i) {
		return demandFlag[i-1];
	}

	/**
	 * @param i インデックス
	 * @param demandFlag
	 * 請求書提出フラッグをセットする。
	 */
	public void setDemandFlag(int i,int demandFlag) {
		this.demandFlag[i-1] = demandFlag;
	}


	/**
	 * @param i インデックス
	 * @return 請求書提出フラッグ
	 * スキルシート提出フラッグを取得する。
	 */
	public int getSkillFlag(int i) {
		return skillFlag[i-1];
	}

	/**
	 * @param i インデックス
	 * @param skillFlag
	 * スキルシート提出フラッグをセットする。
	 */
	public void setSkillFlag(int i,int skillFlag) {
		this.skillFlag[i-1] = skillFlag;
	}

	/**
	 * @param i インデックス
	 * @return イニシャル
	 * イニシャルを取得する。
	 */
	public String getStaffNameInitial(int i) {
		return staffNameInitial[i-1];
	}

	/**
	 * @param i インデックス
	 * @param staffNameInitial イニシャル
	 * イニシャルをセットする。
	 */
	public void setStaffNameInitial(int i,String staffNameInitial) {
		this.staffNameInitial[i-1]=staffNameInitial;
	}

	/**
	 * @param i インデックス
	 * @return 駅名
	 * 駅名を取得する。
	 */
	public String getStation(int i) {
		return station[i-1];
	}

	/**
	 * @param i インデックス
	 * @param station 駅名
	 * 駅名をセットする。
	 */
	public void setStation(int i,String station) {
		this.station[i-1]=station;
	}

	/**
	 * @param i インデックス
	 * @return 始業時間
	 * 始業時間を取得する。
	 */
	public String getStartTime(int i) {
		return startTime[i-1];
	}

	/**
	 * @param i インデックス
	 * @param startTime 始業時間
	 * 始業時間をセットする。
	 */
	public void setStartTime(int i,String startTime) {
		this.startTime[i-1]=startTime;
	}

	/**
	 * @param i インデックス
	 * @return 終業時間
	 * 終業時間を取得する。
	 */
	public String getFinishTime(int i) {
		return finishTime[i-1];
	}

	/**
	 * @param i インデックス
	 * @param finishTime 終業時間
	 * 終業時間をセットする。
	 */
	public void setFinishTime(int i,String finishTime) {
		this.finishTime[i-1]=finishTime;
	}

	/**
	 * @param i インデックス
	 * @return 有給数
	 * 有給数を取得する。
	 */
	public double getPTO(int i) {
		return pto[i-1];
	}

	/**
	 * @param i インデックス
	 * @param pto
	 * 有給数をセットする。
	 */
	public void setPTO(int i,double pto) {
		this.pto[i-1] = pto;
	}

	/**
	 * @param i インデックス
	 * @return 有給消費数
	 * 有給消費数を取得する。
	 */
	public double getPTO_Consume(int i) {
		return ptoConsume[i-1];
	}

	/**
	 * @param i インデックス
	 * @param ptoConsume
	 * 有給消費数をセットする。
	 */
	public void setPTO_Consume(int i,double ptoConsume) {
		this.ptoConsume[i-1] = ptoConsume;
	}
}
