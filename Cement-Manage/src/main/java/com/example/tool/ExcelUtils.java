package com.example.tool;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return          返回
     */

    public static HSSFWorkbook getHSSFWorkbookMaterial(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        //创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null){
            wb = new HSSFWorkbook();
        }

        //在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //调整列宽
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4500);
        sheet.setColumnWidth(6, 4500);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 4000);


        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style.setFillForegroundColor((short) 67);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style.setFont(font);//选择需要用到的字体


        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellValue(values[i][j]);
                cell.setCellStyle(style);
            }
        }
        return wb;

    }

    public static HSSFWorkbook getHSSFWorkbookGoods(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        //创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null)
            wb = new HSSFWorkbook();

        //在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //调整列宽
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 6000);
        sheet.setColumnWidth(8, 4000);

        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style.setFillForegroundColor((short) 67);// 设置背景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style.setFont(font);//选择需要用到的字体

        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellValue(values[i][j]);
                cell.setCellStyle(style);
            }
        }
        return wb;

    }
    public static HSSFWorkbook getHSSFWorkbookSellOrder(String sheetName, String[] title, String[][] values, HSSFWorkbook wb,
                                                        String goodSellId, int goodSellAllPrice, String goodSellPurchaser, String goodSellCreateTime ) {
        System.out.println("进入getHSSFWorkbookSellOrder");
        //创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null){
            wb = new HSSFWorkbook();
        }

        //在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //调整列宽
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 7000);

        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style.setFont(font);//选择需要用到的字体


        //声明列对象
        HSSFCell cell = null;

        // 在sheet中添加表头第0行
        int roenmm = 0;
        HSSFRow row = sheet.createRow(roenmm);
        CellRangeAddress callRangeAddress = new CellRangeAddress(roenmm, ++roenmm, 0, 3);
        sheet.addMergedRegion(callRangeAddress);
        // 在第一行第 一个单元格
        cell = row.createCell(0);
        // 第一行合并内容
        cell.setCellValue("出库单");
        cell.setCellStyle(style);


        //创建标题
        HSSFRow row1 = sheet.createRow(++roenmm);
        cell = row1.createCell(0);
        cell.setCellValue(title[4]);
        cell.setCellStyle(style);

        cell = row1.createCell(1);
        cell.setCellValue(title[5]);
        cell.setCellStyle(style);

        cell = row1.createCell(2);
        cell.setCellValue(title[6]);
        cell.setCellStyle(style);

        cell = row1.createCell(3);
        cell.setCellValue(title[7]);
        cell.setCellStyle(style);



        // 用同一个对象
        HSSFRow row2 = sheet.createRow(++roenmm);
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(roenmm, roenmm+1, 0, 0);
        sheet.addMergedRegion(callRangeAddress1);
        cell = row2.createCell(0);
        cell.setCellValue(goodSellId);
        cell.setCellStyle(style);


        CellRangeAddress callRangeAddress2 = new CellRangeAddress(roenmm, roenmm+1, 1, 1);
        sheet.addMergedRegion(callRangeAddress2);
        cell = row2.createCell(1);
        cell.setCellValue(goodSellAllPrice);
        cell.setCellStyle(style);

        CellRangeAddress callRangeAddress3 = new CellRangeAddress(roenmm, roenmm+1, 2, 2);
        sheet.addMergedRegion(callRangeAddress3);
        cell = row2.createCell(2);
        cell.setCellValue(goodSellPurchaser);
        cell.setCellStyle(style);


        CellRangeAddress callRangeAddress4 = new CellRangeAddress(roenmm, roenmm+1, 3, 3);
        sheet.addMergedRegion(callRangeAddress4);
        cell = row2.createCell(3);
        cell.setCellValue(goodSellCreateTime);
        cell.setCellStyle(style);
        roenmm++;


        HSSFRow row3 = sheet.createRow(++roenmm);
        CellRangeAddress callRangeAddress5 = new CellRangeAddress(roenmm, roenmm, 0, 3);
        sheet.addMergedRegion(callRangeAddress5);
        // 在第一行第 一个单元格
        cell = row3.createCell(0);
        // 第一行合并内容
        cell.setCellValue("产品明细");
        cell.setCellStyle(style);


        HSSFRow row4 = sheet.createRow(++roenmm);
        //创建标题
        for (int i = 0; i < 4; i++) {
            cell = row4.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            HSSFRow row5 = sheet.createRow(++roenmm);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                cell = row5.createCell(j);
                cell.setCellValue(values[i][j]);
                cell.setCellStyle(style);
            }
        }
        return wb;

    }
}
