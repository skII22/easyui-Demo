package com.example;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.*;


public class ExcelTest {
    @Test
    public void ex() {
        InputStream inputStream = null;
        Workbook workbook;
        try {
            inputStream = new FileInputStream("D:\\IDM\\学生学籍信息.xls");
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //InputStream inp = new FileInputStream("C:/Users/H__D/Desktop/workbook.xls");


        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {

            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                //单元格名称
//                System.out.print(cellRef.formatAsString());
//                System.out.print(" - ");

                //通过获取单元格值并应用任何数据格式（Date，0.00，1.23e9，$ 1.23等），获取单元格中显示的文本
                if (row.getRowNum()!=0) {
                    String text = formatter.formatCellValue(cell);
                    System.out.println(text);


                    //获取值并自己格式化
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_STRING:// 字符串型
//                            System.out.println(cell.getRichStringCellValue().getString());
//                            break;
//                        case Cell.CELL_TYPE_NUMERIC:// 数值型
//                            if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则 ，获取该cell的date值
//                                System.out.println(cell.getDateCellValue());
//                            } else {// 纯数字
//                                System.out.println(cell.getNumericCellValue());
//                            }
//                            break;
//                        case Cell.CELL_TYPE_BOOLEAN:// 布尔
//                            System.out.println(cell.getBooleanCellValue());
//                            break;
//                        case Cell.CELL_TYPE_FORMULA:// 公式型
//                            System.out.println(cell.getCellFormula());
//                            break;
//                        case Cell.CELL_TYPE_BLANK:// 空值
//                            System.out.println();
//                            break;
//                        case Cell.CELL_TYPE_ERROR: // 故障
//                            System.out.println();
//                            break;
//                        default:
//                            System.out.println();
//                    }
                }
            }
        }
    }
}

