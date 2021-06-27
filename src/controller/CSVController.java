package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.Skill;
import model.entity.Staff;
import model.properties.Properties;

/**
 * Controller implementation class CSVController
 * csvを出力するクラス。
 */
public class CSVController {
	//カンマ
	private static final String COMMA = ",";

	/**
	 * スキルシートのcsvを出力する。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void output(int year, int month, Skill skill, Staff staff, Logger logger) throws SecurityException, IOException {

		File f = new File(Properties.SAVE_DIR_PATH+"rigare_skill_"+year+"."+month+".csv");
	    OutputStreamWriter osw  = new OutputStreamWriter(new FileOutputStream(f), "Shift-JIS");
	    BufferedWriter bw = new BufferedWriter(osw);

		try {
				int index=1;
				//リストの内容を順に処理
				for (int i=1; i<=staff.getNumbersRecords(); i++) {
					if(staff.getStaffId(i)==skill.getStaffId(index)) {
						bw.write(staff.getStaffName(i));
						bw.write(COMMA);
						if(skill.getProjectName(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getProjectName(index));
						}
						bw.write(COMMA);
						if(skill.getBussinessOverview(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getBussinessOverview(index));
						}
						bw.write(COMMA);
						if(skill.getRole(index)==0) {
							bw.write("");
						}else {
							switch(skill.getRole(index)) {
							case 1:
								bw.write("役割1");
								break;
							case 2:
								bw.write("役割2");
								break;
							case 3:
								bw.write("役割3");
								break;
							}
						}
						bw.write(COMMA);
						if(skill.getScale(index)==0) {
							bw.write("");
						}else {
							bw.write(""+skill.getScale(index));
						}
						bw.write(COMMA);
						if(skill.getServer_OS(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getServer_OS(index));
						}
						bw.write(COMMA);
						if(skill.getDB(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getDB(index));
						}
						bw.write(COMMA);
						if(skill.getTool(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getTool(index));
						}
						bw.write(COMMA);
						if(skill.getUseLanguage(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getUseLanguage(index));
						}
						bw.write(COMMA);
						if(skill.getOther(index)==null) {
							bw.write("");
						}else {
							bw.write(skill.getOther(index));
						}
						bw.newLine();
						index++;
					}else {
						bw.write(staff.getStaffName(i));
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.write(COMMA);
						bw.write("");
						bw.newLine();
					}
				}

		}catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "[CSVController.java]"+e.toString(), e.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.log(Level.WARNING, "[CSVController.java]"+e.toString(), e.getMessage());
			}
		}
	}
}