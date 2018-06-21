package com.jsure.datacenter.utils;

import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.model.model.ExcelModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @Author: wuxiaobiao
 * @Description: 到处excel工具类
 * @Date: Created in 2018/6/21
 * @Time: 10:22
 * I am a Code Man -_-!
 */
public class ExcelUtil {

    private static final int TITLE_HEIGHT_INPOINTS = 50;    //标题的高度
    private static final short FONT_HEIGHT_INPOINTS = 15;   //字体大小
    private static final float ROW_HEIGHT_INPOINTS = 37;    //表头高度
    private static final String FONT_NAME = "黑体";          //字体

    /**
     * @param excelModel excel实体
     * @param response 响应体
     * @param request  请求体
     * @throws Exception
     */
    public static void ExportWithResponse(ExcelModel excelModel, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (excelModel.getColumnNumber() == excelModel.getColumnWidth().length
                && excelModel.getColumnWidth().length == excelModel.getColumnName().length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet, 对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(excelModel.getShettName());
            sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < excelModel.getColumnNumber(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, excelModel.getColumnWidth()[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(TITLE_HEIGHT_INPOINTS); // 设备标题的高度

            // 第三步创建标题的单元格样式style以及字体样式cellHeaderFont
            HSSFCellStyle titleCellStyle = wb.createCellStyle();
            titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            titleCellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            titleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont titlecellFont = wb.createFont();                  // 创建字体样式
            titlecellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);     // 字体加粗
            titlecellFont.setFontName(FONT_NAME);                      // 设置字体类型
            titlecellFont.setFontHeightInPoints(FONT_HEIGHT_INPOINTS); // 设置字体大小
            titleCellStyle.setFont(titlecellFont);                     // 为标题样式设置字体样式

            // 创建标题第一列
            HSSFCell titleCell = titleRow.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelModel.getColumnNumber() - 1)); // 合并列标题
            titleCell.setCellValue(excelModel.getTitleName()); // 设置值标题
            titleCell.setCellStyle(titleCellStyle); // 设置标题样式


            // 创建第1行 也就是表头
            HSSFRow headerRow = sheet.createRow(1);
            headerRow.setHeightInPoints(ROW_HEIGHT_INPOINTS);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle headerStyle = wb.createCellStyle();
            headerStyle.setWrapText(true);                                   // 设置自动换行
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
            headerStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = wb.createFont();                      // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         // 字体加粗
            headerFont.setFontName(FONT_NAME);                          // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10);               // 设置字体大小
            headerStyle.setFont(headerFont);                            // 为表头样式设置字体样式

            // 第四.一步，创建表头的列
            for (int i = 0; i < excelModel.getColumnNumber(); i++) {
                HSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(excelModel.getColumnName()[i]);
                cell.setCellStyle(headerStyle);
            }

            // 第五步，创建单元格，并设置值
            for (int i = 0; i < excelModel.getDataList().length; i++) {
                headerRow = sheet.createRow(i + 2);
                // 为数据内容设置特点 新单元格样式 自动换行 上下居中
                HSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setWrapText(true);                                    // 设置自动换行
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  // 创建一个居中格式
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);             // 左右居中

                // 设置边框
                cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

                HSSFCell datacell = null;
                for (int j = 0; j < excelModel.getColumnNumber(); j++) {
                    datacell = headerRow.createCell(j);
                    datacell.setCellValue(excelModel.getDataList()[i][j]);
                    datacell.setCellStyle(cellStyle);
                }
            }

            // 第六步，将文件存到浏览器设置的下载位置
            String fileName = excelModel.getFileName() + ".xls";
            processFileName(request, response, fileName);
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            OutputStream out = response.getOutputStream();
            wb.write(out);// 将数据写出去
            out.close();
        } else {
            throw new CustomException("123456", "列数目长度名称和列数目数组长度不一致");
        }

    }


    /**
     * 解决设置名称时的乱码
     * @param request
     * @param response
     * @param fileName
     * @return
     */
    public static String processFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            String agent = request.getHeader("USER-AGENT");
            if (agent != null) {
                if (agent.contains("firefox")) {  //火狐
                    response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s.xls",URLEncoder.encode(fileName, "utf-8")));
                } else if (agent.contains("msie")) { //ie
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
                }  else {
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
