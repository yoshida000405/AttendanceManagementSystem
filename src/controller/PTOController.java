package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import model.dao.PTODAO;
import model.dao.StaffDAO;
import model.dao.TimerDAO;
import model.entity.PTO;
import model.entity.Staff;

/**
 * Controller implementation class PTOController
 * 有給を管理するクラス。
 */
public class PTOController {

	static final String SUBJECT = "有給付与のお知らせ";

	static final String CONTENT = "有給が新たに付与されました。\n\nご確認をよろしくお願いします。";

	/**
	 * 毎日0時に有給数を確認し更新する。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void check(Logger logger) throws SecurityException, IOException {

		Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
            	StaffDAO staffDAO = StaffDAO.getInstance();

        		Staff staff = new Staff();

        		PTODAO ptoDAO = PTODAO.getInstance();

        		PTO pto = new PTO();

        		boolean ptoChkFlag = false;

        		try {
        			// 接続
        			staffDAO.dbConnect(logger);
        			// ステートメント作成
        			staffDAO.createSt();

        			ptoChkFlag = staffDAO.searchAllUser(staff);

        		} catch (Exception e) {
        			e.printStackTrace();
        			logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
        		} finally {
        			staffDAO.dbDiscon();
        		}

        		if(LocalDate.now(ZoneId.of("Asia/Tokyo")).getMonthValue()==1) {
    				for(int i=1; i<=staff.getNumbersRecords(); i++) {
    					pto = new PTO();

    					try {
    						// 接続
    						ptoDAO.dbConnect(logger);
    						// ステートメント作成
    						ptoDAO.createSt();

    						ptoDAO.searchPTOTarget(staff.getStaffId(i), LocalDate.now(ZoneId.of("Asia/Tokyo")).getYear(), pto);

    						staff.setPTO_Consume(i, pto.getNumbersRecords());

    					} catch (Exception e) {
    						e.printStackTrace();
    						logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
    					} finally {
    						ptoDAO.dbDiscon();
    					}

    					try {
    	        			// 接続
    	        			staffDAO.dbConnect(logger);
    	        			// ステートメント作成
    	        			staffDAO.createSt();

    	        			staffDAO.updatePTO(i, 1, staff, 2);

    	        		} catch (Exception e) {
    	        			e.printStackTrace();
    	        			logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
    	        		} finally {
    	        			staffDAO.dbDiscon();
    	        		}
        			}
    			}

        		if (ptoChkFlag) {
        			for(int i=1; i<=staff.getNumbersRecords(); i++) {
        				Period period = Period.between(LocalDate.parse(staff.getHireDate(i), DateTimeFormatter.ofPattern("yyyy-MM-dd")),LocalDate.now());
        				int month=period.getMonths();
        				if(period.getYears()>0) {
        					month+=period.getYears()*12;
        				}
        				if((month-6)%12==0) {
        					update(month-6, staff, i);
        					logger.log(Level.INFO, "[PTOController.java]/update/staffId : "+staff.getStaffId(i)+"/month-6 : "+(month-6)+"/pto :"+staff.getPTO(i));

        					try {
        	        			// 接続
        	        			staffDAO.dbConnect(logger);
        	        			// ステートメント作成
        	        			staffDAO.createSt();

        	        			ptoChkFlag = staffDAO.updatePTO(i, 1, staff, 2);

        	    				logger.log(Level.INFO, "[PTOController.java]/updatePTO/staffId : "+staff.getStaffId(i)+"/pto : "+staff.getPTO(i));

        	        		} catch (Exception e) {
        	        			e.printStackTrace();
        	        			logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
        	        		} finally {
        	        			staffDAO.dbDiscon();
        	        		}

        					SendMailController con = new SendMailController();
        					String[] address = {staff.getMailAddress(i)};
        					try {
								con.send(SUBJECT, CONTENT, address, staff, 1, logger);

								logger.log(Level.INFO, "[PTOController.java]/send");
							} catch (SecurityException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
								logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
							} catch (MessagingException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
								logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
							} catch (IOException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
								logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
							}
        				}
        			}
        		}


        		TimerDAO timerDAO = TimerDAO.getInstance();

        		model.entity.Timer timer = new model.entity.Timer();

        		try {
        			// 接続
        			timerDAO.dbConnect(logger);
        			// ステートメント作成
        			timerDAO.createSt();

        			timerDAO.resetTimer(timer,2);

    				logger.log(Level.INFO, "[PTOController.java]/resetTimer");

        		} catch (Exception e) {
        			e.printStackTrace();
        			logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
        		} finally {
        			timerDAO.dbDiscon();
        		}
            }
        };

        LocalDate days = LocalDate.now();
        int year = days.getYear();
        int month = days.getMonthValue();
        int day = 1;
        String time = "00:00:00";

        if(month==12) {
			month=1;
		}else {
			month++;
		}

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date;
		try {
			date = format.parse(""+year+"/"+month+"/"+day+" "+time);
			timer.schedule(task,date);

			logger.log(Level.INFO, "[PTOController.java]/schedule/"+date);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			logger.log(Level.WARNING, "[PTOController.java]"+e.toString(), e.getMessage());
		}
	}

	/**
	 * 有給数を更新する。
	 */
	private void update(int month, Staff staff, int i) {
		switch(month){
		case 0:
			staff.setPTO(i, staff.getPTO(i)+10);
			break;
		case 12:
			staff.setPTO(i, staff.getPTO(i)+11);
			break;
		case 24:
			staff.setPTO(i, staff.getPTO(i)+12);
			if(staff.getPTO(i)>23){
				staff.setPTO(i, 23);
			}
			break;
		case 36:
			staff.setPTO(i, staff.getPTO(i)+14);
			if(staff.getPTO(i)>26){
				staff.setPTO(i, 26);
			}
			break;
		case 48:
			staff.setPTO(i, staff.getPTO(i)+16);
			if(staff.getPTO(i)>30){
				staff.setPTO(i, 30);
			}
			break;
		case 60:
			staff.setPTO(i, staff.getPTO(i)+18);
			if(staff.getPTO(i)>34){
				staff.setPTO(i, 34);
			}
			break;
		case 72:
			staff.setPTO(i, staff.getPTO(i)+20);
			if(staff.getPTO(i)>38){
				staff.setPTO(i, 38);
			}
			break;
		}
		if((month-6)>72) {
			staff.setPTO(i, staff.getPTO(i)+20);
			if(staff.getPTO(i)>40){
				staff.setPTO(i, 40);
			}
		}
	}

	/**
	 * 有給数の現在情報を出力する。
	 */
	public static double[] info(Staff staff, int i, Logger logger) {
		double[] result = new double[4];
		Period period = Period.between(LocalDate.parse(staff.getHireDate(i), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.now(ZoneId.of("Asia/Tokyo")));
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			result[0] = 12-(month-6);
			result[1] = 0;
			result[2] = staff.getPTO(i);
			result[3] = staff.getPTO_Consume(i);
		}else if(12<=(month-6)&& (month-6)<24) {
			if(staff.getPTO(i)>11){
				result[0] = 24-(month-6);
				result[1] = staff.getPTO(i)-11;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 24-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if(24<=(month-6)&& (month-6)<36) {
			if(staff.getPTO(i)>11){
				result[0] = 36-(month-6);
				result[1] = staff.getPTO(i)-12;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 36-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if(36<=(month-6)&& (month-6)<48) {
			if(staff.getPTO(i)>12){
				result[0] = 48-(month-6);
				result[1] = staff.getPTO(i)-14;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 48-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if(48<=(month-6)&& (month-6)<60) {
			if(staff.getPTO(i)>14){
				result[0] = 60-(month-6);
				result[1] = staff.getPTO(i)-16;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 60-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if(60<=(month-6)&& (month-6)<72) {
			if(staff.getPTO(i)>16){
				result[0] = 72-(month-6);
				result[1] = staff.getPTO(i)-18;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 72-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if(72<=(month-6)&& (month-6)<84) {
			if(staff.getPTO(i)>18){
				result[0] = 84-(month-6);
				result[1] = staff.getPTO(i)-20;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = 72-(month-6);
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}else if((month-6)>=84) {
			if(staff.getPTO(i)>20){
				result[0] = (int) (12*Math.ceil((month-6)/12)-(month-6));
				result[1] = staff.getPTO(i)-20;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}else {
				result[0] = (int) (12*Math.ceil((month-6)/12)-(month-6));
				result[1] = 0;
				result[2] = staff.getPTO(i);
				result[3] = staff.getPTO_Consume(i);
			}
		}
		return result;
	}

	/**
	 * ある年の開始時点での有給の総付与数を出力する。
	 */
	public static int total(String hireDate, int year) {
		Period period = Period.between(LocalDate.parse(hireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(year+"-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			return 10;
		}else if(12<=(month-6)&& (month-6)<24) {
			return 21;
		}else if(24<=(month-6)&& (month-6)<36) {
			return 33;
		}else if(36<=(month-6)&& (month-6)<48) {
			return 47;
		}else if(48<=(month-6)&& (month-6)<60) {
			return 63;
		}else if(60<=(month-6)&& (month-6)<72) {
			return 81;
		}else if(72<=(month-6)&& (month-6)<84) {
			return 101;
		}else if((month-6)>=84) {
			return ((month-78)/12)*12+100;
		}
		return 0;
	}

	/**
	 * ある年の開始時点での有給の総付与数を出力する。
	 */
	public static int total1(String hireDate, int year) {
		Period period = Period.between(LocalDate.parse(hireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse((year-1)+"-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			return 10;
		}else if(12<=(month-6)&& (month-6)<24) {
			return 21;
		}else if(24<=(month-6)&& (month-6)<36) {
			return 33;
		}else if(36<=(month-6)&& (month-6)<48) {
			return 47;
		}else if(48<=(month-6)&& (month-6)<60) {
			return 63;
		}else if(60<=(month-6)&& (month-6)<72) {
			return 81;
		}else if(72<=(month-6)&& (month-6)<84) {
			return 101;
		}else if((month-6)>=84) {
			return ((month-78)/12)*12+100;
		}
		return 0;
	}

	/**
	 * 現在までの有給の総付与数を出力する。
	 */
	public static int total(String hireDate) {
		Period period = Period.between(LocalDate.parse(hireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.now());
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			return 10;
		}else if(12<=(month-6)&& (month-6)<24) {
			return 21;
		}else if(24<=(month-6)&& (month-6)<36) {
			return 33;
		}else if(36<=(month-6)&& (month-6)<48) {
			return 47;
		}else if(48<=(month-6)&& (month-6)<60) {
			return 63;
		}else if(60<=(month-6)&& (month-6)<72) {
			return 81;
		}else if(72<=(month-6)&& (month-6)<84) {
			return 101;
		}else if((month-6)>=84) {
			return ((month-78)/12)*12+100;
		}
		return 0;
	}


	/**
	 * ある年の有給の総付与数を出力する。
	 */
	public static int give(String hireDate, LocalDate year) {
		Period period = Period.between(LocalDate.parse(hireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), year);
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			return 10;
		}else if(12<=(month-6)&& (month-6)<24) {
			return 21;
		}else if(24<=(month-6)&& (month-6)<36) {
			return 33;
		}else if(36<=(month-6)&& (month-6)<48) {
			return 47;
		}else if(48<=(month-6)&& (month-6)<60) {
			return 63;
		}else if(60<=(month-6)&& (month-6)<72) {
			return 81;
		}else if(72<=(month-6)&& (month-6)<84) {
			return 101;
		}else if((month-6)>=84) {
			return ((month-78)/12)*12+100;
		}
		return 0;
	}

	/**
	 * ある年の有給の付与数を出力する。
	 */
	public static int giveNumber(String hireDate, LocalDate year) {
		Period period = Period.between(LocalDate.parse(hireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), year);
		int month=period.getMonths();
		if(period.getYears()>0) {
			month+=period.getYears()*12;
		}
		if(0<=(month-6)&& (month-6)<12) {
			return 10;
		}else if(12<=(month-6)&& (month-6)<24) {
			return 11;
		}else if(24<=(month-6)&& (month-6)<36) {
			return 12;
		}else if(36<=(month-6)&& (month-6)<48) {
			return 14;
		}else if(48<=(month-6)&& (month-6)<60) {
			return 16;
		}else if(60<=(month-6)&& (month-6)<72) {
			return 18;
		}else if(72<=(month-6)&& (month-6)<84) {
			return 20;
		}else if((month-6)>=84) {
			return 20;
		}
		return 0;
	}

}