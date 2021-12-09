package com.company.shop;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersEx implements  ExportFile{

    @Override
    public void writeOrders(OutputStream out, ResultSet result) throws SQLException, IOException {
        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet("Orders");

        // creating a row object
        XSSFRow row;

        int row_id=0;
        String arr[]={"Order ID","Customer Name","Total Price","Item Name","Item Price"};
        row=spreadsheet.createRow(row_id++);
        for (int i=0; i< arr.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(arr[i]);
        }

        while (result.next()){
            row=spreadsheet.createRow(row_id++);
            for (int col_num=1,cell_id=0 ; col_num<=5 ; col_num++,cell_id++){
                Cell cell = row.createCell(cell_id);
                cell.setCellValue(result.getString(col_num));
            }
        }
        workbook.write(out);
        out.close();
    }
}
