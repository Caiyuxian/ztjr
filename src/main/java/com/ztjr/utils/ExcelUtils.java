package com.ztjr.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {
	
	public static HSSFWorkbook geneExcel(String sheetName, String[] title, String[][] value) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 3200);
		//设置标题
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		cellStyle.setFont(font);
		for(int i=0;i<title.length;i++) {
			cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(title[i]);
		}
		//设置值
		for(int i=0;i<value.length;i++) {
			row = sheet.createRow(i+1);
			for(int j=0; j<value[i].length; j++) {
				row.createCell(j).setCellValue(value[i][j]);
			}
		}
		return wb;
	} 
	
}
