package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import model.dao.PTODAO;
import model.entity.Attendance;
import model.entity.Demand;
import model.entity.Excel;
import model.entity.PTO;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Controller implementation class ExcelController
 * Excelを出力するクラス。
 */
public class ExcelController {
	/**
	 * 有給のデータをExcel出力する。
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void output(int year, int month, Staff staff, int index, Logger logger)
			throws FileNotFoundException, IOException {
		PTODAO ptoDAO = PTODAO.getInstance();

		PTO pto = new PTO();

		List<Excel> list = new ArrayList<Excel>();
		Excel excel = new Excel();
		excel.setName(staff.getStaffName(index));
		excel.setHireDate(staff.getHireDate(index));
		double[] prophase = new double[6];
		LocalDate ld1 = LocalDate.parse(staff.getHireDate(index), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate ld2 = LocalDate.parse(staff.getHireDate(index), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate ld3 = LocalDate.parse(staff.getHireDate(index), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		for (int i = 0; i <= 5; i++) {
			switch (i) {
			case 0:
				ld2 = LocalDate.parse((year - 4) + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = LocalDate.parse((year - 5) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						prophase[i] = PTOController.total(staff.getHireDate(1), year - 4)
								- ptoDAO.searchPTOTotal(staff.getStaffId(index), year - 4);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					if (ld3.getYear() != year - 5) {
						excel.setGiveDate1(ld3.toString());
						excel.setGive1(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}

				} else {
					prophase[i] = 0;
					excel.setGive1(0);
				}
				break;
			case 1:
				excel.setYear1(year - 4);
				excel.setProphase1(prophase[i - 1]);
				ld2 = LocalDate.parse((year - 3) + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = LocalDate.parse((year - 4) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					if (ld3.getYear() == year - 4) {
						excel.setGiveDate1(ld3.toString());
						excel.setGive1(PTOController.giveNumber(staff.getHireDate(index), ld3));
					} else {
						excel.setGiveDate2(ld3.toString());
						excel.setGive2(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						ptoDAO.searchPTOExcel(staff.getStaffId(index), year - 4, pto);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					double sum = 0;
					for (int j = 1; j <= pto.getNumbersRecords(); j++) {
						sum += pto.getPTONumber(j);
						switch (j) {
						case 1:
							excel.setConsue1date1(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number1(pto.getPTONumber(j));
							break;
						case 2:
							excel.setConsue1date2(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number2(pto.getPTONumber(j));
							break;
						case 3:
							excel.setConsue1date3(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number3(pto.getPTONumber(j));
							break;
						case 4:
							excel.setConsue1date4(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number4(pto.getPTONumber(j));
							break;
						case 5:
							excel.setConsue1date5(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number5(pto.getPTONumber(j));
							break;
						case 6:
							excel.setConsue1date6(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number6(pto.getPTONumber(j));
							break;
						case 7:
							excel.setConsue1date7(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number7(pto.getPTONumber(j));
							break;
						case 8:
							excel.setConsue1date8(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number8(pto.getPTONumber(j));
							break;
						case 9:
							excel.setConsue1date9(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number9(pto.getPTONumber(j));
							break;
						case 10:
							excel.setConsue1date10(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number10(pto.getPTONumber(j));
							break;
						case 11:
							excel.setConsue1date11(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number11(pto.getPTONumber(j));
							break;
						case 12:
							excel.setConsue1date12(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number12(pto.getPTONumber(j));
							break;
						case 13:
							excel.setConsue1date13(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number13(pto.getPTONumber(j));
							break;
						case 14:
							excel.setConsue1date14(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number14(pto.getPTONumber(j));
							break;
						case 15:
							excel.setConsue1date15(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number15(pto.getPTONumber(j));
							break;
						case 16:
							excel.setConsue1date16(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number16(pto.getPTONumber(j));
							break;
						case 17:
							excel.setConsue1date17(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number17(pto.getPTONumber(j));
							break;
						case 18:
							excel.setConsue1date18(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number18(pto.getPTONumber(j));
							break;
						case 19:
							excel.setConsue1date19(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number19(pto.getPTONumber(j));
							break;
						case 20:
							excel.setConsue1date20(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number20(pto.getPTONumber(j));
							break;
						case 21:
							excel.setConsue1date21(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number21(pto.getPTONumber(j));
							break;
						case 22:
							excel.setConsue1date22(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number22(pto.getPTONumber(j));
							break;
						case 23:
							excel.setConsue1date23(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number23(pto.getPTONumber(j));
							break;
						case 24:
							excel.setConsue1date24(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number24(pto.getPTONumber(j));
							break;
						case 25:
							excel.setConsue1date25(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue1number25(pto.getPTONumber(j));
							break;
						}
					}
					excel.setStarts1(prophase[i - 1] + excel.getGive1());
					excel.setConsue1sum(sum);
					prophase[i] = ((Double) excel.getStarts1() - sum);
					excel.setRemain1(prophase[i]);
				}
				break;
			case 2:
				excel.setYear2(year - 3);
				excel.setProphase2(prophase[i - 1]);
				ld2 = LocalDate.parse((year - 2) + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = LocalDate.parse((year - 3) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					if (ld3.getYear() == year - 3) {
						excel.setGiveDate2(ld3.toString());
						excel.setGive2(PTOController.giveNumber(staff.getHireDate(index), ld3));
					} else {
						excel.setGiveDate3(ld3.toString());
						excel.setGive3(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						ptoDAO.searchPTOExcel(staff.getStaffId(index), year - 3, pto);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					double sum = 0;
					for (int j = 1; j <= pto.getNumbersRecords(); j++) {
						sum += pto.getPTONumber(j);
						switch (j) {
						case 1:
							excel.setConsue2date1(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number1(pto.getPTONumber(j));
							break;
						case 2:
							excel.setConsue2date2(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number2(pto.getPTONumber(j));
							break;
						case 3:
							excel.setConsue2date3(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number3(pto.getPTONumber(j));
							break;
						case 4:
							excel.setConsue2date4(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number4(pto.getPTONumber(j));
							break;
						case 5:
							excel.setConsue2date5(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number5(pto.getPTONumber(j));
							break;
						case 6:
							excel.setConsue2date6(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number6(pto.getPTONumber(j));
							break;
						case 7:
							excel.setConsue2date7(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number7(pto.getPTONumber(j));
							break;
						case 8:
							excel.setConsue2date8(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number8(pto.getPTONumber(j));
							break;
						case 9:
							excel.setConsue2date9(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number9(pto.getPTONumber(j));
							break;
						case 10:
							excel.setConsue2date10(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number10(pto.getPTONumber(j));
							break;
						case 11:
							excel.setConsue2date11(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number11(pto.getPTONumber(j));
							break;
						case 12:
							excel.setConsue2date12(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number12(pto.getPTONumber(j));
							break;
						case 13:
							excel.setConsue2date13(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number13(pto.getPTONumber(j));
							break;
						case 14:
							excel.setConsue2date14(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number14(pto.getPTONumber(j));
							break;
						case 15:
							excel.setConsue2date15(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number15(pto.getPTONumber(j));
							break;
						case 16:
							excel.setConsue2date16(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number16(pto.getPTONumber(j));
							break;
						case 17:
							excel.setConsue2date17(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number17(pto.getPTONumber(j));
							break;
						case 18:
							excel.setConsue2date18(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number18(pto.getPTONumber(j));
							break;
						case 19:
							excel.setConsue2date19(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number19(pto.getPTONumber(j));
							break;
						case 20:
							excel.setConsue2date20(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number20(pto.getPTONumber(j));
							break;
						case 21:
							excel.setConsue2date21(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number21(pto.getPTONumber(j));
							break;
						case 22:
							excel.setConsue2date22(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number22(pto.getPTONumber(j));
							break;
						case 23:
							excel.setConsue2date23(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number23(pto.getPTONumber(j));
							break;
						case 24:
							excel.setConsue2date24(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number24(pto.getPTONumber(j));
							break;
						case 25:
							excel.setConsue2date25(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue2number25(pto.getPTONumber(j));
							break;
						}
					}
					excel.setStarts2(prophase[i - 1] + excel.getGive2());
					excel.setConsue2sum(sum);
					prophase[i] = ((Double) excel.getStarts2() - sum);
					excel.setRemain2(prophase[i]);
				}
				break;
			case 3:
				excel.setYear3(year - 2);
				excel.setProphase3(prophase[i - 1]);
				ld2 = LocalDate.parse((year - 1) + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = LocalDate.parse((year - 2) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					if (ld3.getYear() == year - 2) {
						excel.setGiveDate3(ld3.toString());
						excel.setGive3(PTOController.giveNumber(staff.getHireDate(index), ld3));
					} else {
						excel.setGiveDate4(ld3.toString());
						excel.setGive4(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						ptoDAO.searchPTOExcel(staff.getStaffId(index), year - 2, pto);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					double sum = 0;
					for (int j = 1; j <= pto.getNumbersRecords(); j++) {
						sum += pto.getPTONumber(j);
						switch (j) {
						case 1:
							excel.setConsue3date1(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number1(pto.getPTONumber(j));
							break;
						case 2:
							excel.setConsue3date2(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number2(pto.getPTONumber(j));
							break;
						case 3:
							excel.setConsue3date3(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number3(pto.getPTONumber(j));
							break;
						case 4:
							excel.setConsue3date4(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number4(pto.getPTONumber(j));
							break;
						case 5:
							excel.setConsue3date5(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number5(pto.getPTONumber(j));
							break;
						case 6:
							excel.setConsue3date6(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number6(pto.getPTONumber(j));
							break;
						case 7:
							excel.setConsue3date7(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number7(pto.getPTONumber(j));
							break;
						case 8:
							excel.setConsue3date8(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number8(pto.getPTONumber(j));
							break;
						case 9:
							excel.setConsue3date9(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number9(pto.getPTONumber(j));
							break;
						case 10:
							excel.setConsue3date10(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number10(pto.getPTONumber(j));
							break;
						case 11:
							excel.setConsue3date11(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number11(pto.getPTONumber(j));
							break;
						case 12:
							excel.setConsue3date12(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number12(pto.getPTONumber(j));
							break;
						case 13:
							excel.setConsue3date13(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number13(pto.getPTONumber(j));
							break;
						case 14:
							excel.setConsue3date14(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number14(pto.getPTONumber(j));
							break;
						case 15:
							excel.setConsue3date15(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number15(pto.getPTONumber(j));
							break;
						case 16:
							excel.setConsue3date16(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number16(pto.getPTONumber(j));
							break;
						case 17:
							excel.setConsue3date17(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number17(pto.getPTONumber(j));
							break;
						case 18:
							excel.setConsue3date18(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number18(pto.getPTONumber(j));
							break;
						case 19:
							excel.setConsue3date19(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number19(pto.getPTONumber(j));
							break;
						case 20:
							excel.setConsue3date20(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number20(pto.getPTONumber(j));
							break;
						case 21:
							excel.setConsue3date21(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number21(pto.getPTONumber(j));
							break;
						case 22:
							excel.setConsue3date22(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number22(pto.getPTONumber(j));
							break;
						case 23:
							excel.setConsue3date23(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number23(pto.getPTONumber(j));
							break;
						case 24:
							excel.setConsue3date24(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number24(pto.getPTONumber(j));
							break;
						case 25:
							excel.setConsue3date25(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue3number25(pto.getPTONumber(j));
							break;
						}
					}
					excel.setStarts3(prophase[i - 1] + excel.getGive3());
					excel.setConsue3sum(sum);
					prophase[i] = ((Double) excel.getStarts3() - sum);
					excel.setRemain3(prophase[i]);
				}
				break;
			case 4:
				excel.setYear4(year - 1);
				excel.setProphase4(prophase[i - 1]);
				ld2 = LocalDate.parse((year) + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = LocalDate.parse((year - 1) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					if (ld3.getYear() == year - 1) {
						excel.setGiveDate4(ld3.toString());
						excel.setGive4(PTOController.giveNumber(staff.getHireDate(index), ld3));
					} else {
						excel.setGiveDate5(ld3.toString());
						excel.setGive5(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						ptoDAO.searchPTOExcel(staff.getStaffId(index), year - 1, pto);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					double sum = 0;
					for (int j = 1; j <= pto.getNumbersRecords(); j++) {
						sum += pto.getPTONumber(j);
						switch (j) {
						case 1:
							excel.setConsue4date1(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number1(pto.getPTONumber(j));
							break;
						case 2:
							excel.setConsue4date2(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number2(pto.getPTONumber(j));
							break;
						case 3:
							excel.setConsue4date3(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number3(pto.getPTONumber(j));
							break;
						case 4:
							excel.setConsue4date4(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number4(pto.getPTONumber(j));
							break;
						case 5:
							excel.setConsue4date5(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number5(pto.getPTONumber(j));
							break;
						case 6:
							excel.setConsue4date6(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number6(pto.getPTONumber(j));
							break;
						case 7:
							excel.setConsue4date7(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number7(pto.getPTONumber(j));
							break;
						case 8:
							excel.setConsue4date8(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number8(pto.getPTONumber(j));
							break;
						case 9:
							excel.setConsue4date9(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number9(pto.getPTONumber(j));
							break;
						case 10:
							excel.setConsue4date10(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number10(pto.getPTONumber(j));
							break;
						case 11:
							excel.setConsue4date11(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number11(pto.getPTONumber(j));
							break;
						case 12:
							excel.setConsue4date12(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number12(pto.getPTONumber(j));
							break;
						case 13:
							excel.setConsue4date13(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number13(pto.getPTONumber(j));
							break;
						case 14:
							excel.setConsue4date14(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number14(pto.getPTONumber(j));
							break;
						case 15:
							excel.setConsue4date15(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number15(pto.getPTONumber(j));
							break;
						case 16:
							excel.setConsue4date16(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number16(pto.getPTONumber(j));
							break;
						case 17:
							excel.setConsue4date17(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number17(pto.getPTONumber(j));
							break;
						case 18:
							excel.setConsue4date18(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number18(pto.getPTONumber(j));
							break;
						case 19:
							excel.setConsue4date19(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number19(pto.getPTONumber(j));
							break;
						case 20:
							excel.setConsue4date20(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number20(pto.getPTONumber(j));
							break;
						case 21:
							excel.setConsue4date21(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number21(pto.getPTONumber(j));
							break;
						case 22:
							excel.setConsue4date22(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number22(pto.getPTONumber(j));
							break;
						case 23:
							excel.setConsue4date23(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number23(pto.getPTONumber(j));
							break;
						case 24:
							excel.setConsue4date24(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number24(pto.getPTONumber(j));
							break;
						case 25:
							excel.setConsue4date25(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue4number25(pto.getPTONumber(j));
							break;
						}
					}
					excel.setStarts4(prophase[i - 1] + excel.getGive4());
					excel.setConsue4sum(sum);
					prophase[i] = ((Double) excel.getStarts4() - sum);
					excel.setRemain4(prophase[i]);
				}
				break;
			case 5:
				excel.setYear5(year);
				excel.setProphase5(prophase[i - 1]);
				ld2 = LocalDate.now();
				ld3 = LocalDate.parse((year) + "-" + staff.getHireDate(index).substring(5, 7) + "-01",
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				ld3 = ld3.plusMonths(6);
				if (ld1.isBefore(ld2)) {
					if (ld3.getYear() == year) {
						excel.setGiveDate5(ld3.toString());
						excel.setGive5(PTOController.giveNumber(staff.getHireDate(index), ld3));
					}
					try {
						// 接続
						ptoDAO.dbConnect(logger);
						// ステートメント作成
						ptoDAO.createSt();

						ptoDAO.searchPTOExcel(staff.getStaffId(index), year, pto);

					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
					} finally {
						ptoDAO.dbDiscon();
					}
					double sum = 0;
					for (int j = 1; j <= pto.getNumbersRecords(); j++) {
						sum += pto.getPTONumber(j);
						switch (j) {
						case 1:
							excel.setConsue5date1(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number1(pto.getPTONumber(j));
							break;
						case 2:
							excel.setConsue5date2(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number2(pto.getPTONumber(j));
							break;
						case 3:
							excel.setConsue5date3(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number3(pto.getPTONumber(j));
							break;
						case 4:
							excel.setConsue5date4(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number4(pto.getPTONumber(j));
							break;
						case 5:
							excel.setConsue5date5(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number5(pto.getPTONumber(j));
							break;
						case 6:
							excel.setConsue5date6(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number6(pto.getPTONumber(j));
							break;
						case 7:
							excel.setConsue5date7(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number7(pto.getPTONumber(j));
							break;
						case 8:
							excel.setConsue5date8(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number8(pto.getPTONumber(j));
							break;
						case 9:
							excel.setConsue5date9(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number9(pto.getPTONumber(j));
							break;
						case 10:
							excel.setConsue5date10(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number10(pto.getPTONumber(j));
							break;
						case 11:
							excel.setConsue5date11(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number11(pto.getPTONumber(j));
							break;
						case 12:
							excel.setConsue5date12(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number12(pto.getPTONumber(j));
							break;
						case 13:
							excel.setConsue5date13(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number13(pto.getPTONumber(j));
							break;
						case 14:
							excel.setConsue5date14(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number14(pto.getPTONumber(j));
							break;
						case 15:
							excel.setConsue5date15(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number15(pto.getPTONumber(j));
							break;
						case 16:
							excel.setConsue5date16(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number16(pto.getPTONumber(j));
							break;
						case 17:
							excel.setConsue5date17(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number17(pto.getPTONumber(j));
							break;
						case 18:
							excel.setConsue5date18(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number18(pto.getPTONumber(j));
							break;
						case 19:
							excel.setConsue5date19(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number19(pto.getPTONumber(j));
							break;
						case 20:
							excel.setConsue5date20(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number20(pto.getPTONumber(j));
							break;
						case 21:
							excel.setConsue5date21(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number21(pto.getPTONumber(j));
							break;
						case 22:
							excel.setConsue5date22(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number22(pto.getPTONumber(j));
							break;
						case 23:
							excel.setConsue5date23(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number23(pto.getPTONumber(j));
							break;
						case 24:
							excel.setConsue5date24(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number24(pto.getPTONumber(j));
							break;
						case 25:
							excel.setConsue5date25(pto.getTargetYear(j) + "年" + pto.getTargetMonth(j) + "月"
									+ pto.getTargetDay(j) + "日");
							excel.setConsue5number25(pto.getPTONumber(j));
							break;
						}
					}
					excel.setStarts5(prophase[i - 1] + excel.getGive5());
					excel.setConsue5sum(sum);
					prophase[i] = ((Double) excel.getStarts5() - sum);
					excel.setRemain5(prophase[i]);
				}
				break;
			}
		}

		list.add(excel);
		try (InputStream is = ExcelController.class.getResourceAsStream("object_collection_pto_template.xls")) {
			try (OutputStream os = new FileOutputStream(Properties.SAVE_DIR_PATH + staff.getStaffId(index)
					+ "/rigare_pto_" + staff.getLastNameEnglish(index) + staff.getFirstNameEnglish(index) + "_" + year
					+ ".xls")) {
				Context context = new Context();
				context.putVar("employees", list);
				JxlsHelper.getInstance().processTemplate(is, os, context);
			} catch (Exception e) {
				logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
		}
	}

	/**
	 * 請求書のデータをExcel出力する。
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void output(int year, int month, Demand demand, Staff staff, int j, Logger logger)
			throws FileNotFoundException, IOException {
		List<Excel> list = new ArrayList<Excel>();
		Excel excel = new Excel();
		excel.setName(staff.getStaffName(j));
		int recipt = 0;
		for (int i = 1; i <= demand.getNumbersRecords(); i++) {
			if (i == 1) {
				excel.setDay1(month + "/" + demand.getTargetDay(1));
				switch (demand.getDivision(1)) {
				case 1:
					excel.setDivision1("旅費交通費");
					break;
				case 2:
					excel.setDivision1("消耗品費");
					break;
				case 3:
					excel.setDivision1("会議費");
					break;
				case 4:
					excel.setDivision1("通信費");
					break;
				case 5:
					excel.setDivision1("支払手数料");
					break;
				case 6:
					excel.setDivision1("交際費");
					break;
				case 7:
					excel.setDivision1("その他");
					break;
				}
				excel.setMoney1("" + (demand.getAplicationAmount(1) * demand.getMulti(1)));
				excel.setDescription1(demand.getDescription(1));
				excel.setTo1(demand.getTo(1));
				excel.setRemarks1(demand.getRemarks(1));
				if (demand.getRecipt(1) == 2) {
					recipt++;
					excel.setRecipt1("" + recipt);
				}
			} else if (i == 2) {
				excel.setDay2(month + "/" + demand.getTargetDay(2));
				switch (demand.getDivision(2)) {
				case 1:
					excel.setDivision2("旅費交通費");
					break;
				case 2:
					excel.setDivision2("消耗品費");
					break;
				case 3:
					excel.setDivision2("会議費");
					break;
				case 4:
					excel.setDivision2("通信費");
					break;
				case 5:
					excel.setDivision2("支払手数料");
					break;
				case 6:
					excel.setDivision2("交際費");
					break;
				case 7:
					excel.setDivision2("その他");
					break;
				}
				excel.setMoney2("" + (demand.getAplicationAmount(2) * demand.getMulti(2)));
				excel.setDescription2(demand.getDescription(2));
				excel.setTo2(demand.getTo(2));
				excel.setRemarks2(demand.getRemarks(2));
				if (demand.getRecipt(2) == 2) {
					recipt++;
					excel.setRecipt2("" + recipt);
				}
			} else if (i == 3) {
				excel.setDay3(month + "/" + demand.getTargetDay(3));
				switch (demand.getDivision(3)) {
				case 1:
					excel.setDivision3("旅費交通費");
					break;
				case 2:
					excel.setDivision3("消耗品費");
					break;
				case 3:
					excel.setDivision3("会議費");
					break;
				case 4:
					excel.setDivision3("通信費");
					break;
				case 5:
					excel.setDivision3("支払手数料");
					break;
				case 6:
					excel.setDivision3("交際費");
					break;
				case 7:
					excel.setDivision3("その他");
					break;
				}
				excel.setMoney3("" + (demand.getAplicationAmount(3) * demand.getMulti(3)));
				excel.setDescription3(demand.getDescription(3));
				excel.setTo3(demand.getTo(3));
				excel.setRemarks3(demand.getRemarks(3));
				if (demand.getRecipt(3) == 2) {
					recipt++;
					excel.setRecipt3("" + recipt);
				}
			} else if (i == 4) {
				excel.setDay4(month + "/" + demand.getTargetDay(4));
				switch (demand.getDivision(4)) {
				case 1:
					excel.setDivision4("旅費交通費");
					break;
				case 2:
					excel.setDivision4("消耗品費");
					break;
				case 3:
					excel.setDivision4("会議費");
					break;
				case 4:
					excel.setDivision4("通信費");
					break;
				case 5:
					excel.setDivision4("支払手数料");
					break;
				case 6:
					excel.setDivision4("交際費");
					break;
				case 7:
					excel.setDivision4("その他");
					break;
				}
				excel.setMoney4("" + (demand.getAplicationAmount(4) * demand.getMulti(4)));
				excel.setDescription4(demand.getDescription(4));
				excel.setTo4(demand.getTo(4));
				excel.setRemarks4(demand.getRemarks(4));
				if (demand.getRecipt(4) == 2) {
					recipt++;
					excel.setRecipt4("" + recipt);
				}
			} else if (i == 5) {
				excel.setDay5(month + "/" + demand.getTargetDay(5));
				switch (demand.getDivision(5)) {
				case 1:
					excel.setDivision5("旅費交通費");
					break;
				case 2:
					excel.setDivision5("消耗品費");
					break;
				case 3:
					excel.setDivision5("会議費");
					break;
				case 4:
					excel.setDivision5("通信費");
					break;
				case 5:
					excel.setDivision5("支払手数料");
					break;
				case 6:
					excel.setDivision5("交際費");
					break;
				case 7:
					excel.setDivision5("その他");
					break;
				}
				excel.setMoney5("" + (demand.getAplicationAmount(5) * demand.getMulti(5)));
				excel.setDescription5(demand.getDescription(5));
				excel.setTo5(demand.getTo(5));
				excel.setRemarks5(demand.getRemarks(5));
				if (demand.getRecipt(5) == 2) {
					recipt++;
					excel.setRecipt5("" + recipt);
				}
			} else if (i == 6) {
				excel.setDay6(month + "/" + demand.getTargetDay(6));
				switch (demand.getDivision(6)) {
				case 1:
					excel.setDivision6("旅費交通費");
					break;
				case 2:
					excel.setDivision6("消耗品費");
					break;
				case 3:
					excel.setDivision6("会議費");
					break;
				case 4:
					excel.setDivision6("通信費");
					break;
				case 5:
					excel.setDivision6("支払手数料");
					break;
				case 6:
					excel.setDivision6("交際費");
					break;
				case 7:
					excel.setDivision6("その他");
					break;
				}
				excel.setMoney6("" + (demand.getAplicationAmount(6) * demand.getMulti(6)));
				excel.setDescription6(demand.getDescription(6));
				excel.setTo6(demand.getTo(6));
				excel.setRemarks6(demand.getRemarks(6));
				if (demand.getRecipt(6) == 2) {
					recipt++;
					excel.setRecipt6("" + recipt);
				}
			} else if (i == 7) {
				excel.setDay7(month + "/" + demand.getTargetDay(7));
				switch (demand.getDivision(7)) {
				case 1:
					excel.setDivision7("旅費交通費");
					break;
				case 2:
					excel.setDivision7("消耗品費");
					break;
				case 3:
					excel.setDivision7("会議費");
					break;
				case 4:
					excel.setDivision7("通信費");
					break;
				case 5:
					excel.setDivision7("支払手数料");
					break;
				case 6:
					excel.setDivision7("交際費");
					break;
				case 7:
					excel.setDivision7("その他");
					break;
				}
				excel.setMoney7("" + (demand.getAplicationAmount(7) * demand.getMulti(7)));
				excel.setDescription7(demand.getDescription(7));
				excel.setTo7(demand.getTo(7));
				excel.setRemarks7(demand.getRemarks(7));
				if (demand.getRecipt(7) == 2) {
					recipt++;
					excel.setRecipt7("" + recipt);
				}
			} else if (i == 8) {
				excel.setDay8(month + "/" + demand.getTargetDay(8));
				switch (demand.getDivision(8)) {
				case 1:
					excel.setDivision8("旅費交通費");
					break;
				case 2:
					excel.setDivision8("消耗品費");
					break;
				case 3:
					excel.setDivision8("会議費");
					break;
				case 4:
					excel.setDivision8("通信費");
					break;
				case 5:
					excel.setDivision8("支払手数料");
					break;
				case 6:
					excel.setDivision8("交際費");
					break;
				case 7:
					excel.setDivision8("その他");
					break;
				}
				excel.setMoney8("" + (demand.getAplicationAmount(8) * demand.getMulti(8)));
				excel.setDescription8(demand.getDescription(8));
				excel.setTo8(demand.getTo(8));
				excel.setRemarks8(demand.getRemarks(8));
				if (demand.getRecipt(8) == 2) {
					recipt++;
					excel.setRecipt8("" + recipt);
				}
			} else if (i == 9) {
				excel.setDay9(month + "/" + demand.getTargetDay(9));
				switch (demand.getDivision(9)) {
				case 1:
					excel.setDivision9("旅費交通費");
					break;
				case 2:
					excel.setDivision9("消耗品費");
					break;
				case 3:
					excel.setDivision9("会議費");
					break;
				case 4:
					excel.setDivision9("通信費");
					break;
				case 5:
					excel.setDivision9("支払手数料");
					break;
				case 6:
					excel.setDivision9("交際費");
					break;
				case 7:
					excel.setDivision9("その他");
					break;
				}
				excel.setMoney9("" + (demand.getAplicationAmount(9) * demand.getMulti(9)));
				excel.setDescription9(demand.getDescription(9));
				excel.setTo9(demand.getTo(9));
				excel.setRemarks9(demand.getRemarks(9));
				if (demand.getRecipt(9) == 2) {
					recipt++;
					excel.setRecipt9("" + recipt);
				}
			} else if (i == 10) {
				excel.setDay10(month + "/" + demand.getTargetDay(10));
				switch (demand.getDivision(10)) {
				case 1:
					excel.setDivision10("旅費交通費");
					break;
				case 2:
					excel.setDivision10("消耗品費");
					break;
				case 3:
					excel.setDivision10("会議費");
					break;
				case 4:
					excel.setDivision10("通信費");
					break;
				case 5:
					excel.setDivision10("支払手数料");
					break;
				case 6:
					excel.setDivision10("交際費");
					break;
				case 7:
					excel.setDivision10("その他");
					break;
				}
				excel.setMoney10("" + (demand.getAplicationAmount(10) * demand.getMulti(10)));
				excel.setDescription10(demand.getDescription(10));
				excel.setTo10(demand.getTo(10));
				excel.setRemarks10(demand.getRemarks(10));
				if (demand.getRecipt(10) == 2) {
					recipt++;
					excel.setRecipt10("" + recipt);
				}
			} else if (i == 11) {
				excel.setDay11(month + "/" + demand.getTargetDay(11));
				switch (demand.getDivision(11)) {
				case 1:
					excel.setDivision11("旅費交通費");
					break;
				case 2:
					excel.setDivision11("消耗品費");
					break;
				case 3:
					excel.setDivision11("会議費");
					break;
				case 4:
					excel.setDivision11("通信費");
					break;
				case 5:
					excel.setDivision11("支払手数料");
					break;
				case 6:
					excel.setDivision11("交際費");
					break;
				case 7:
					excel.setDivision11("その他");
					break;
				}
				excel.setMoney11("" + (demand.getAplicationAmount(11) * demand.getMulti(11)));
				excel.setDescription11(demand.getDescription(11));
				excel.setTo11(demand.getTo(11));
				excel.setRemarks11(demand.getRemarks(11));
				if (demand.getRecipt(11) == 2) {
					recipt++;
					excel.setRecipt11("" + recipt);
				}
			} else if (i == 12) {
				excel.setDay12(month + "/" + demand.getTargetDay(12));
				switch (demand.getDivision(12)) {
				case 1:
					excel.setDivision12("旅費交通費");
					break;
				case 2:
					excel.setDivision12("消耗品費");
					break;
				case 3:
					excel.setDivision12("会議費");
					break;
				case 4:
					excel.setDivision12("通信費");
					break;
				case 5:
					excel.setDivision12("支払手数料");
					break;
				case 6:
					excel.setDivision12("交際費");
					break;
				case 7:
					excel.setDivision12("その他");
					break;
				}
				excel.setMoney12("" + (demand.getAplicationAmount(12) * demand.getMulti(12)));
				excel.setDescription12(demand.getDescription(12));
				excel.setTo12(demand.getTo(12));
				excel.setRemarks12(demand.getRemarks(12));
				if (demand.getRecipt(12) == 2) {
					recipt++;
					excel.setRecipt12("" + recipt);
				}
			} else if (i == 13) {
				excel.setDay13(month + "/" + demand.getTargetDay(13));
				switch (demand.getDivision(13)) {
				case 1:
					excel.setDivision13("旅費交通費");
					break;
				case 2:
					excel.setDivision13("消耗品費");
					break;
				case 3:
					excel.setDivision13("会議費");
					break;
				case 4:
					excel.setDivision13("通信費");
					break;
				case 5:
					excel.setDivision13("支払手数料");
					break;
				case 6:
					excel.setDivision13("交際費");
					break;
				case 7:
					excel.setDivision13("その他");
					break;
				}
				excel.setMoney13("" + (demand.getAplicationAmount(13) * demand.getMulti(13)));
				excel.setDescription13(demand.getDescription(13));
				excel.setTo13(demand.getTo(13));
				excel.setRemarks13(demand.getRemarks(13));
				if (demand.getRecipt(13) == 2) {
					recipt++;
					excel.setRecipt13("" + recipt);
				}
			} else if (i == 14) {
				excel.setDay14(month + "/" + demand.getTargetDay(14));
				switch (demand.getDivision(14)) {
				case 1:
					excel.setDivision14("旅費交通費");
					break;
				case 2:
					excel.setDivision14("消耗品費");
					break;
				case 3:
					excel.setDivision14("会議費");
					break;
				case 4:
					excel.setDivision14("通信費");
					break;
				case 5:
					excel.setDivision14("支払手数料");
					break;
				case 6:
					excel.setDivision14("交際費");
					break;
				case 7:
					excel.setDivision14("その他");
					break;
				}
				excel.setMoney14("" + (demand.getAplicationAmount(14) * demand.getMulti(14)));
				excel.setDescription14(demand.getDescription(14));
				excel.setTo14(demand.getTo(14));
				excel.setRemarks14(demand.getRemarks(14));
				if (demand.getRecipt(14) == 2) {
					recipt++;
					excel.setRecipt14("" + recipt);
				}
			} else if (i == 15) {
				excel.setDay15(month + "/" + demand.getTargetDay(15));
				switch (demand.getDivision(15)) {
				case 1:
					excel.setDivision15("旅費交通費");
					break;
				case 2:
					excel.setDivision15("消耗品費");
					break;
				case 3:
					excel.setDivision15("会議費");
					break;
				case 4:
					excel.setDivision15("通信費");
					break;
				case 5:
					excel.setDivision15("支払手数料");
					break;
				case 6:
					excel.setDivision15("交際費");
					break;
				case 7:
					excel.setDivision15("その他");
					break;
				}
				excel.setMoney15("" + (demand.getAplicationAmount(15) * demand.getMulti(15)));
				excel.setDescription15(demand.getDescription(15));
				excel.setTo15(demand.getTo(15));
				excel.setRemarks15(demand.getRemarks(15));
				if (demand.getRecipt(15) == 2) {
					recipt++;
					excel.setRecipt15("" + recipt);
				}
			}
		}
		excel.setSum1(demand.getTransportation());
		excel.setSum2(demand.getExpendables());
		excel.setSum3(demand.getMeeting());
		excel.setSum4(demand.getCommunications());
		excel.setSum5(demand.getCommission());
		excel.setSum6(demand.getSocial());
		excel.setSum7(demand.getOther());
		excel.setSum12(demand.getSum());
		list.add(excel);
		try (InputStream is = ExcelController.class.getResourceAsStream("object_collection_demand_template.xls")) {
			try (OutputStream os = new FileOutputStream(Properties.SAVE_DIR_PATH + staff.getStaffId(j) + "/rigare_bill_"
					+ staff.getLastNameEnglish(j) + staff.getFirstNameEnglish(j) + "_" + year + "." + month + ".xls")) {
				Context context = new Context();
				context.putVar("employees", list);
				JxlsHelper.getInstance().processTemplate(is, os, context);
			} catch (Exception e) {
				logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
		}
	}

	/**
	 * 勤務表のデータをExcel出力する。
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void output(int year, int month, Attendance attendance, Staff staff, int i, Logger logger)
			throws FileNotFoundException, IOException {
		List<Excel> list = new ArrayList<Excel>();
		int days = DateTimeController.getMonthDays(LocalDate.now().getYear(), month);
		Excel excel = new Excel();
		LocalDate date = LocalDate.of(year, month, 1);
		excel.setName(staff.getStaffName(i));
		excel.setMonth(year + "年" + month + "月　勤務報告書");
		excel.setStart1(attendance.getStartTime(1));
		excel.setStart2(attendance.getStartTime(2));
		excel.setStart3(attendance.getStartTime(3));
		excel.setStart4(attendance.getStartTime(4));
		excel.setStart5(attendance.getStartTime(5));
		excel.setStart6(attendance.getStartTime(6));
		excel.setStart7(attendance.getStartTime(7));
		excel.setStart8(attendance.getStartTime(8));
		excel.setStart9(attendance.getStartTime(9));
		excel.setStart10(attendance.getStartTime(10));
		excel.setStart11(attendance.getStartTime(11));
		excel.setStart12(attendance.getStartTime(12));
		excel.setStart13(attendance.getStartTime(13));
		excel.setStart14(attendance.getStartTime(14));
		excel.setStart15(attendance.getStartTime(15));
		excel.setStart16(attendance.getStartTime(16));
		excel.setStart17(attendance.getStartTime(17));
		excel.setStart18(attendance.getStartTime(18));
		excel.setStart19(attendance.getStartTime(19));
		excel.setStart20(attendance.getStartTime(20));
		excel.setStart21(attendance.getStartTime(21));
		excel.setStart22(attendance.getStartTime(22));
		excel.setStart23(attendance.getStartTime(23));
		excel.setStart24(attendance.getStartTime(24));
		excel.setStart25(attendance.getStartTime(25));
		excel.setStart26(attendance.getStartTime(26));
		excel.setStart27(attendance.getStartTime(27));
		excel.setStart28(attendance.getStartTime(28));
		excel.setStart29(attendance.getStartTime(29));
		excel.setStart30(attendance.getStartTime(30));
		excel.setStart31(attendance.getStartTime(31));

		excel.setFinish1(attendance.getFinishTime(1));
		excel.setFinish2(attendance.getFinishTime(2));
		excel.setFinish3(attendance.getFinishTime(3));
		excel.setFinish4(attendance.getFinishTime(4));
		excel.setFinish5(attendance.getFinishTime(5));
		excel.setFinish6(attendance.getFinishTime(6));
		excel.setFinish7(attendance.getFinishTime(7));
		excel.setFinish8(attendance.getFinishTime(8));
		excel.setFinish9(attendance.getFinishTime(9));
		excel.setFinish10(attendance.getFinishTime(10));
		excel.setFinish11(attendance.getFinishTime(11));
		excel.setFinish12(attendance.getFinishTime(12));
		excel.setFinish13(attendance.getFinishTime(13));
		excel.setFinish14(attendance.getFinishTime(14));
		excel.setFinish15(attendance.getFinishTime(15));
		excel.setFinish16(attendance.getFinishTime(16));
		excel.setFinish17(attendance.getFinishTime(17));
		excel.setFinish18(attendance.getFinishTime(18));
		excel.setFinish19(attendance.getFinishTime(19));
		excel.setFinish20(attendance.getFinishTime(20));
		excel.setFinish21(attendance.getFinishTime(21));
		excel.setFinish22(attendance.getFinishTime(22));
		excel.setFinish23(attendance.getFinishTime(23));
		excel.setFinish24(attendance.getFinishTime(24));
		excel.setFinish25(attendance.getFinishTime(25));
		excel.setFinish26(attendance.getFinishTime(26));
		excel.setFinish27(attendance.getFinishTime(27));
		excel.setFinish28(attendance.getFinishTime(28));
		excel.setFinish29(attendance.getFinishTime(29));
		excel.setFinish30(attendance.getFinishTime(30));
		excel.setFinish31(attendance.getFinishTime(31));

		excel.setBreak1(attendance.getBreakTime(1));
		excel.setBreak2(attendance.getBreakTime(2));
		excel.setBreak3(attendance.getBreakTime(3));
		excel.setBreak4(attendance.getBreakTime(4));
		excel.setBreak5(attendance.getBreakTime(5));
		excel.setBreak6(attendance.getBreakTime(6));
		excel.setBreak7(attendance.getBreakTime(7));
		excel.setBreak8(attendance.getBreakTime(8));
		excel.setBreak9(attendance.getBreakTime(9));
		excel.setBreak10(attendance.getBreakTime(10));
		excel.setBreak11(attendance.getBreakTime(11));
		excel.setBreak12(attendance.getBreakTime(12));
		excel.setBreak13(attendance.getBreakTime(13));
		excel.setBreak14(attendance.getBreakTime(14));
		excel.setBreak15(attendance.getBreakTime(15));
		excel.setBreak16(attendance.getBreakTime(16));
		excel.setBreak17(attendance.getBreakTime(17));
		excel.setBreak18(attendance.getBreakTime(18));
		excel.setBreak19(attendance.getBreakTime(19));
		excel.setBreak20(attendance.getBreakTime(20));
		excel.setBreak21(attendance.getBreakTime(21));
		excel.setBreak22(attendance.getBreakTime(22));
		excel.setBreak23(attendance.getBreakTime(23));
		excel.setBreak24(attendance.getBreakTime(24));
		excel.setBreak25(attendance.getBreakTime(25));
		excel.setBreak26(attendance.getBreakTime(26));
		excel.setBreak27(attendance.getBreakTime(27));
		excel.setBreak28(attendance.getBreakTime(28));
		excel.setBreak29(attendance.getBreakTime(29));
		excel.setBreak30(attendance.getBreakTime(30));
		excel.setBreak31(attendance.getBreakTime(31));

		excel.setOperating1(attendance.getWorkHours(1));
		excel.setOperating2(attendance.getWorkHours(2));
		excel.setOperating3(attendance.getWorkHours(3));
		excel.setOperating4(attendance.getWorkHours(4));
		excel.setOperating5(attendance.getWorkHours(5));
		excel.setOperating6(attendance.getWorkHours(6));
		excel.setOperating7(attendance.getWorkHours(7));
		excel.setOperating8(attendance.getWorkHours(8));
		excel.setOperating9(attendance.getWorkHours(9));
		excel.setOperating10(attendance.getWorkHours(10));
		excel.setOperating11(attendance.getWorkHours(11));
		excel.setOperating12(attendance.getWorkHours(12));
		excel.setOperating13(attendance.getWorkHours(13));
		excel.setOperating14(attendance.getWorkHours(14));
		excel.setOperating15(attendance.getWorkHours(15));
		excel.setOperating16(attendance.getWorkHours(16));
		excel.setOperating17(attendance.getWorkHours(17));
		excel.setOperating18(attendance.getWorkHours(18));
		excel.setOperating19(attendance.getWorkHours(19));
		excel.setOperating20(attendance.getWorkHours(20));
		excel.setOperating21(attendance.getWorkHours(21));
		excel.setOperating22(attendance.getWorkHours(22));
		excel.setOperating23(attendance.getWorkHours(23));
		excel.setOperating24(attendance.getWorkHours(24));
		excel.setOperating25(attendance.getWorkHours(25));
		excel.setOperating26(attendance.getWorkHours(26));
		excel.setOperating27(attendance.getWorkHours(27));
		excel.setOperating28(attendance.getWorkHours(28));
		excel.setOperating29(attendance.getWorkHours(29));
		excel.setOperating30(attendance.getWorkHours(30));
		excel.setOperating31(attendance.getWorkHours(31));

		excel.setRemarks1(attendance.getRemarks(1));
		excel.setRemarks2(attendance.getRemarks(2));
		excel.setRemarks3(attendance.getRemarks(3));
		excel.setRemarks4(attendance.getRemarks(4));
		excel.setRemarks5(attendance.getRemarks(5));
		excel.setRemarks6(attendance.getRemarks(6));
		excel.setRemarks7(attendance.getRemarks(7));
		excel.setRemarks8(attendance.getRemarks(8));
		excel.setRemarks9(attendance.getRemarks(9));
		excel.setRemarks10(attendance.getRemarks(10));
		excel.setRemarks11(attendance.getRemarks(11));
		excel.setRemarks12(attendance.getRemarks(12));
		excel.setRemarks13(attendance.getRemarks(13));
		excel.setRemarks14(attendance.getRemarks(14));
		excel.setRemarks15(attendance.getRemarks(15));
		excel.setRemarks16(attendance.getRemarks(16));
		excel.setRemarks17(attendance.getRemarks(17));
		excel.setRemarks18(attendance.getRemarks(18));
		excel.setRemarks19(attendance.getRemarks(19));
		excel.setRemarks20(attendance.getRemarks(20));
		excel.setRemarks21(attendance.getRemarks(21));
		excel.setRemarks22(attendance.getRemarks(22));
		excel.setRemarks23(attendance.getRemarks(23));
		excel.setRemarks24(attendance.getRemarks(24));
		excel.setRemarks25(attendance.getRemarks(25));
		excel.setRemarks26(attendance.getRemarks(26));
		excel.setRemarks27(attendance.getRemarks(27));
		excel.setRemarks28(attendance.getRemarks(28));
		excel.setRemarks29(attendance.getRemarks(29));
		excel.setRemarks30(attendance.getRemarks(30));
		excel.setRemarks31(attendance.getRemarks(31));

		excel.setOperatingTime("" + attendance.getOperatingTime());
		excel.setOperatingDays("" + attendance.getOperatingDays());

		switch (date.getDayOfWeek().getValue()) {
		case 1:
			excel.setDays1("月");
			excel.setDays2("火");
			excel.setDays3("水");
			excel.setDays4("木");
			excel.setDays5("金");
			excel.setDays6("土");
			excel.setDays7("日");
			excel.setDays8("月");
			excel.setDays9("火");
			excel.setDays10("水");
			excel.setDays11("木");
			excel.setDays12("金");
			excel.setDays13("土");
			excel.setDays14("日");
			excel.setDays15("月");
			excel.setDays16("火");
			excel.setDays17("水");
			excel.setDays18("木");
			excel.setDays19("金");
			excel.setDays20("土");
			excel.setDays21("日");
			excel.setDays22("月");
			excel.setDays23("火");
			excel.setDays24("水");
			excel.setDays25("木");
			excel.setDays26("金");
			excel.setDays27("土");
			excel.setDays28("日");
			if (days > 28) {
				excel.setDays29("月");
			}
			if (days > 29) {
				excel.setDays30("火");
			}
			if (days > 30) {
				excel.setDays31("水");
			}
			break;
		case 2:
			excel.setDays1("火");
			excel.setDays2("水");
			excel.setDays3("木");
			excel.setDays4("金");
			excel.setDays5("土");
			excel.setDays6("日");
			excel.setDays7("月");
			excel.setDays8("火");
			excel.setDays9("水");
			excel.setDays10("木");
			excel.setDays11("金");
			excel.setDays12("土");
			excel.setDays13("日");
			excel.setDays14("月");
			excel.setDays15("火");
			excel.setDays16("水");
			excel.setDays17("木");
			excel.setDays18("金");
			excel.setDays19("土");
			excel.setDays20("日");
			excel.setDays21("月");
			excel.setDays22("火");
			excel.setDays23("水");
			excel.setDays24("木");
			excel.setDays25("金");
			excel.setDays26("土");
			excel.setDays27("日");
			excel.setDays28("月");
			if (days > 28) {
				excel.setDays29("火");
			}
			if (days > 29) {
				excel.setDays30("水");
			}
			if (days > 30) {
				excel.setDays31("木");
			}
			break;
		case 3:
			excel.setDays1("水");
			excel.setDays2("木");
			excel.setDays3("金");
			excel.setDays4("土");
			excel.setDays5("日");
			excel.setDays6("月");
			excel.setDays7("火");
			excel.setDays8("水");
			excel.setDays9("木");
			excel.setDays10("金");
			excel.setDays11("土");
			excel.setDays12("日");
			excel.setDays13("月");
			excel.setDays14("火");
			excel.setDays15("水");
			excel.setDays16("木");
			excel.setDays17("金");
			excel.setDays18("土");
			excel.setDays19("日");
			excel.setDays20("月");
			excel.setDays21("火");
			excel.setDays22("水");
			excel.setDays23("木");
			excel.setDays24("金");
			excel.setDays25("土");
			excel.setDays26("日");
			excel.setDays27("月");
			excel.setDays28("火");
			if (days > 28) {
				excel.setDays29("水");
			}
			if (days > 29) {
				excel.setDays30("木");
			}
			if (days > 30) {
				excel.setDays31("金");
			}
			break;
		case 4:
			excel.setDays1("木");
			excel.setDays2("金");
			excel.setDays3("土");
			excel.setDays4("日");
			excel.setDays5("月");
			excel.setDays6("火");
			excel.setDays7("水");
			excel.setDays8("木");
			excel.setDays9("金");
			excel.setDays10("土");
			excel.setDays11("日");
			excel.setDays12("月");
			excel.setDays13("火");
			excel.setDays14("水");
			excel.setDays15("木");
			excel.setDays16("金");
			excel.setDays17("土");
			excel.setDays18("日");
			excel.setDays19("月");
			excel.setDays20("火");
			excel.setDays21("水");
			excel.setDays22("木");
			excel.setDays23("金");
			excel.setDays24("土");
			excel.setDays25("日");
			excel.setDays26("月");
			excel.setDays27("火");
			excel.setDays28("水");
			if (days > 28) {
				excel.setDays29("木");
			}
			if (days > 29) {
				excel.setDays30("金");
			}
			if (days > 30) {
				excel.setDays31("土");
			}
			break;
		case 5:
			excel.setDays1("金");
			excel.setDays2("土");
			excel.setDays3("日");
			excel.setDays4("月");
			excel.setDays5("火");
			excel.setDays6("水");
			excel.setDays7("木");
			excel.setDays8("金");
			excel.setDays9("土");
			excel.setDays10("日");
			excel.setDays11("月");
			excel.setDays12("火");
			excel.setDays13("水");
			excel.setDays14("木");
			excel.setDays15("金");
			excel.setDays16("土");
			excel.setDays17("日");
			excel.setDays18("月");
			excel.setDays19("火");
			excel.setDays20("水");
			excel.setDays21("木");
			excel.setDays22("金");
			excel.setDays23("土");
			excel.setDays24("日");
			excel.setDays25("月");
			excel.setDays26("火");
			excel.setDays27("水");
			excel.setDays28("木");
			if (days > 28) {
				excel.setDays29("金");
			}
			if (days > 29) {
				excel.setDays30("土");
			}
			if (days > 30) {
				excel.setDays31("日");
			}
			break;
		case 6:
			excel.setDays1("土");
			excel.setDays2("日");
			excel.setDays3("月");
			excel.setDays4("火");
			excel.setDays5("水");
			excel.setDays6("木");
			excel.setDays7("金");
			excel.setDays8("土");
			excel.setDays9("日");
			excel.setDays10("月");
			excel.setDays11("火");
			excel.setDays12("水");
			excel.setDays13("木");
			excel.setDays14("金");
			excel.setDays15("土");
			excel.setDays16("日");
			excel.setDays17("月");
			excel.setDays18("火");
			excel.setDays19("水");
			excel.setDays20("木");
			excel.setDays21("金");
			excel.setDays22("土");
			excel.setDays23("日");
			excel.setDays24("月");
			excel.setDays25("火");
			excel.setDays26("水");
			excel.setDays27("木");
			excel.setDays28("金");
			if (days > 28) {
				excel.setDays29("土");
			}
			if (days > 29) {
				excel.setDays30("日");
			}
			if (days > 30) {
				excel.setDays31("月");
			}
			break;
		case 7:
			excel.setDays1("日");
			excel.setDays2("月");
			excel.setDays3("火");
			excel.setDays4("水");
			excel.setDays5("木");
			excel.setDays6("金");
			excel.setDays7("土");
			excel.setDays8("日");
			excel.setDays9("月");
			excel.setDays10("火");
			excel.setDays11("水");
			excel.setDays12("木");
			excel.setDays13("金");
			excel.setDays14("土");
			excel.setDays15("日");
			excel.setDays16("月");
			excel.setDays17("火");
			excel.setDays18("水");
			excel.setDays19("木");
			excel.setDays20("金");
			excel.setDays21("土");
			excel.setDays22("日");
			excel.setDays23("月");
			excel.setDays24("火");
			excel.setDays25("水");
			excel.setDays26("木");
			excel.setDays27("金");
			excel.setDays28("土");
			if (days > 28) {
				excel.setDays29("日");
			}
			if (days > 29) {
				excel.setDays30("月");
			}
			if (days > 30) {
				excel.setDays31("火");
			}
			break;
		}
		list.add(excel);
		try (InputStream is = ExcelController.class.getResourceAsStream("object_collection_attendance_template.xls")) {
			try (OutputStream os = new FileOutputStream(Properties.SAVE_DIR_PATH + staff.getStaffId(i) + "/rigare_work_"
					+ staff.getLastNameEnglish(i) + staff.getFirstNameEnglish(i) + "_" + year + "." + month + ".xls")) {
				Context context = new Context();
				context.putVar("employees", list);
				JxlsHelper.getInstance().processTemplate(is, os, context);
			} catch (Exception e) {
				logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "[ExcelController.java]" + e.toString(), e.getMessage());
		}
	}
}