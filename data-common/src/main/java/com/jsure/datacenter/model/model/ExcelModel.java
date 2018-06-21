package com.jsure.datacenter.model.model;

import lombok.Data;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/21
 * @Time: 13:39
 * I am a Code Man -_-!
 */
@Data
public class ExcelModel {

    private String shettName; //sheet名称
    private String titleName; //标题
    private String fileName; //excel文件名
    private int columnNumber;//列数目
    private int[] columnWidth;//列宽度
    private String[] columnName;//列标题
    private String[][] dataList;//数据列表
}
