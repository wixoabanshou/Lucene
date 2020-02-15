package com.xinhua;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class poi {
    public static void main(String[] args) throws Exception {

        InputStream inputStream = new FileInputStream("e://aaaaaaa.xls");
        POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
        HSSFWorkbook wb = new HSSFWorkbook(fileSystem);
        ExcelExtractor excelExtractor = new ExcelExtractor(wb);
        excelExtractor.setIncludeSheetNames(false);
        System.out.println(excelExtractor.getText());



    }
}
