package model.entity;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 勤務表モデルクラス。
 */
public class Attendance {

	/**
	 * レコード数
	 */
	private int numbersRecords;

	/**
	 * 稼働日数
	 */
	private double operatingDays;
	/**

	/**
	 * 休日日数
	 */
	private int holiday;
	/**

	/**
	 * 代休日数
	 */
	private int substitute;
	/**

	/**
	 * 有給日数
	 */
	private double paid;
	/**

	/**
	 * 欠勤日数
	 */
	private int absence;
	/**

	/**
	 * 明休日数
	 */
	private int overNight;
	/**

	/**
	 * 稼働時間(通常)
	 */
	private double operatingTime;
	/**

	/**
	 * 稼働時間(自社勤務)
	 */
	private double inTime;
	/**

	/**
	 * 出勤時刻
	 */
	private String[] startTime = new String[50];

	/**
	 * 退勤時刻
	 */
	private String[] finishTime = new String[50];

	/**
	 * 休憩時間
	 */
	private String[] breakTime = new String[50];

	/**
	 * 区分
	 */
	private int[] division = new int[50];

	/**
	 * 勤務時間
	 */
	private String[] workingHours = new String[50];

	/**
	 * 備考
	 */
	private String[] remarks = new String[50];

	/**
	 * 管理者メモ
	 */
	private String[] memo = new String[50];

	/**
	 * 最終更新日
	 */
	private String[] lastUpdate = new String[50];

	/**
	 * 自社勤務日
	 */
	private String[] day = new String[50];

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
		this.numbersRecords= numbersRecords;
	}

	/**
	 * @return 稼働日数
	 * 稼働日数を取得する。
	 */
	public double getOperatingDays() {
		return this.operatingDays;
	}

	/**
	 * @param operatingDays 稼働日数
	 * 稼働日数をセットする。
	 */
	public void setOperatingDays() {
		this.operatingDays=0;
		int index=0;
		for(int div:this.division) {
			if(div==1) {
				if(!startTime[index].contentEquals("00:00")) {
					this.operatingDays++;
				}else {
					if(!finishTime[index].contentEquals("00:00")) {
						this.operatingDays++;
					}

				}
			}else if(div==5||div==6) {
				this.operatingDays+=0.5;
			}
			index++;
		}
	}

	/**
	 * @return 休日日数
	 * 休日日数を取得する。
	 */
	public int getHoliday() {
		return this.holiday;
	}

	/**
	 * @param holiday 休日日数
	 * 休日日数をセットする。
	 */
	public void setHoliday() {
		this.holiday=0;
		for(int div:this.division) {
			if(div==7) {
				this.holiday++;
			}
		}
	}

	/**
	 * @return 代休日数
	 * 代休日数を取得する。
	 */
	public int getSubstitute() {
		return this.substitute;
	}

	/**
	 * @param substitute 代休日数
	 * 代休日数をセットする。
	 */
	public void setSubstitute() {
		this.substitute=0;
		for(int div:this.division) {
			if(div==3) {
				this.substitute++;
			}
		}
	}

	/**
	 * @return 有給日数
	 * 有給日数を取得する。
	 */
	public double getPaid() {
		return this.paid;
	}

	/**
	 * @param paid 有給日数
	 * 有給日数をセットする。
	 */
	public void setPaid() {
		this.paid=0;
		for(int div:this.division) {
			if(div==4) {
				this.paid++;
			}else if(div==5||div==6){
				this.paid+=0.5;
			}
		}
	}

	/**
	 * @return 明休日数
	 * 明休日数を取得する。
	 */
	public int getOverNight() {
		return this.overNight;
	}

	/**
	 * @param overNight 明休日数
	 * 明休日数をセットする。
	 */
	public void setOverNight() {
		this.overNight=0;
		for(int div:this.division) {
			if(div==8) {
				this.overNight++;
			}
		}
	}

	/**
	 * @return 欠勤日数
	 * 欠勤日数を取得する。
	 */
	public int getAbsence() {
		return this.absence;
	}

	/**
	 * @param absence 欠勤日数
	 * 欠勤日数をセットする。
	 */
	public void setAbsence() {
		this.absence=0;
		for(int div:this.division) {
			if(div==2) {
				this.absence++;
			}
		}
	}

	/**
	 * @return 稼働時間
	 * 稼働時間を取得する。
	 */
	public double getOperatingTime() {
		return this.operatingTime;
	}

	/**
	 * @param operatingTime 稼働時間
	 * 稼働時間をセットする。
	 */
	public void setOperatingTime() {
		this.operatingTime=0;
		double minute=0;
		int hour=0;
		int index=0;
		for(String time:this.workingHours) {
			if(time!=null) {
				if(division[index]==1||division[index]==9) {
					hour+=Integer.parseInt(time.substring(0,2));
					minute+=Integer.parseInt(time.substring(3,5));
				}else if(division[index]==5||division[index]==6) {
					hour+=Integer.parseInt(time.substring(0,2));
					minute+=Integer.parseInt(time.substring(3,5));
				}
			}
			index++;
		}
		this.operatingTime= (double)hour+(minute/60);
	}

	/**
	 * @return 自社稼働時間
	 * 自社稼働時間を取得する。
	 */
	public double getInTime() {
		return this.inTime;
	}

	/**
	 * @param inTime 自社稼働時間
	 * 自社稼働時間をセットする。
	 */
	public void setInTime() {
		this.inTime=0;
		double minute=0;
		int hour=0;
		for(int i=0; i<this.numbersRecords; i++) {
			if(this.breakTime[i]!=null) {
				if(this.division[i]==10) {
					hour+=Integer.parseInt(this.breakTime[i].substring(0,2));
					minute+=Integer.parseInt(this.breakTime[i].substring(3,5));
				}
			}
		}
		this.inTime= (double)hour+(minute/60);
	}

	/**
	 * @return 出勤時刻
	 * 出勤時刻を取得する。
	 */
	public String getStartTime(int i) {
		return startTime[i-1];
	}

	/**
	 * @param i インデックス
	 * @param startTime 出勤時刻
	 * 出勤時刻をセットする。
	 */
	public void setStartTime(int i,String startTime) {
		this.startTime[i-1]= startTime;
	}

	/**
	 * @return 退勤時刻
	 * 退勤時刻を取得する。
	 */
	public String getFinishTime(int i) {
		return finishTime[i-1];
	}

	/**
	 * @param i インデックス
	 * @param finishTime 退勤時間
	 * 退勤時間をセットする。
	 */
	public void setFinishTime(int i,String finishTime) {
		this.finishTime[i-1]=finishTime;
	}

	/**
	 * @return 休憩時間
	 * 休憩時間を取得する。
	 */
	public String getBreakTime(int i) {
		return breakTime[i-1];
	}

	/**
	 * @param i インデックス
	 * @param breakTime 休憩時間
	 * 休憩時間をセットする。
	 */
	public void setBreakTime(int i,String breakTime) {
		this.breakTime[i-1]=breakTime;
	}

	/**
	 * @return 区分
	 * 区分を取得する。
	 */
	public int getDivision(int i) {
		return division[i-1];
	}

	/**
	 * @param i インデックス
	 * @param division 区分
	 * 区分をセットする。
	 */
	public void setDivision(int i,int division) {
		this.division[i-1]=division;
	}

	/**
	 * @param i インデックス
	 * @return 勤務時間。
	 * 勤務時間を取得する。
	 */
	public String getWorkHours(int i) {
		return workingHours[i-1];
	}

	/**
	 * @param i インデックス
	 * 勤務時間をセットする。
	 */
	public void setWorkHours(int i) {
		int startTimeHour = Integer.parseInt(startTime[i-1].substring(0,2));
		int startTimeMinute = Integer.parseInt(startTime[i-1].substring(3,5));
		int finishTimeHour = Integer.parseInt(finishTime[i-1].substring(0,2));
		int finishTimeMinute = Integer.parseInt(finishTime[i-1].substring(3,5));
		int breakTimeHour = Integer.parseInt(breakTime[i-1].substring(0,2));
		int breakTimeMinute = Integer.parseInt(breakTime[i-1].substring(3,5));
		LocalDateTime start = LocalDateTime.of(2020, 1,1,startTimeHour,startTimeMinute);
		LocalDateTime finish = LocalDateTime.of(2020, 1,1,finishTimeHour,finishTimeMinute);
		if(!start.isEqual(finish)) {
			finish = finish.minusHours(breakTimeHour);
			finish = finish.minusMinutes(breakTimeMinute);
		}
		if(start.isAfter(finish)) {
			finish = finish.plusDays(1);
		}
		Duration duration = Duration.between(start, finish);
		long hour = duration.toHours();
		long minute = (duration.toMinutes()-duration.toHours()*60);
		if(hour<10) {
			if(minute<10) {
				this.workingHours[i-1] =("0"+hour+":0"+minute);
			}else {
				this.workingHours[i-1] =("0"+hour+":"+minute);
			}
		}else {
			if(minute<10) {
				this.workingHours[i-1] =(hour+":0"+minute);
			}else {
				this.workingHours[i-1] =(hour+":"+minute);
			}
		}
	}

	/**
	 * @return 備考
	 * 備考を取得する。
	 */
	public String getRemarks(int i) {
		return remarks[i-1];
	}

	/**
	 * @param i インデックス
	 * @param remarks 備考
	 * 備考をセットする。
	 */
	public void setRemarks(int i,String remarks) {
		this.remarks[i-1]= remarks;
	}

	/**
	 * @return メモ
	 * メモを取得する。
	 */
	public String getMemo(int i) {
		return memo[i-1];
	}

	/**
	 * @param i インデックス
	 * @param memo メモ
	 * メモをセットする。
	 */
	public void setMemo(int i,String memo) {
		this.memo[i-1]= memo;
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
	 * @return 自社勤務日
	 * 自社勤務日を取得する。
	 */
	public String getDay(int i) {
		return day[i-1];
	}

	/**
	 * @param i インデックス
	 * @param day 自社勤務日
	 * 自社勤務日をセットする。
	 */
	public void setDay(int i,String day) {
		this.day[i-1]= day;
	}
}
