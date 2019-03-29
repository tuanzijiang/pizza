package edu.ecnu.scsse.pizza.bussiness.server.utils;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelUtils {
    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    public static HashMap<String, Object> importXls(String xlsFile){
        HashMap<String,Object> map = new HashMap<>();
        try{
            HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(xlsFile));
            Sheet sheet = book.getSheetAt(0);
            map.put("sheet",sheet);
            Row title = sheet.getRow(0);
            String msg = "Wrong pattern: The first line of the input excel file must be \"名称\", \"供应商\", \"警戒值\" and \"数量\". as column titles.";
            int i = 0;
            for(i=0;i<title.getLastCellNum();i++){
                Cell cell = title.getCell(i);
                switch (i){
                    case 0:
                        if(!cell.getStringCellValue().equals("名称"))
                            map.put("msg",msg);
                        break;
                    case 1:
                        if(!cell.getStringCellValue().equals("供应商"))
                            map.put("msg",msg);
                        break;
                    case 2:
                        if(!cell.getStringCellValue().equals("警戒值"))
                            map.put("msg",msg);
                        break;
                    case 3:
                        if(!cell.getStringCellValue().equals("数量"))
                            map.put("msg",msg);
                        break;
                    default:
                        map.put("msg",msg);
                        break;
                }
            }
            if(i==title.getLastCellNum())
                map.put("sheet",sheet);
            else
                map.put("sheet",null);
        }catch (IOException e){
            String msg = e.getMessage();
            e.printStackTrace();
            log.error(msg);
            map.put("msg",msg);
            map.put("sheet",null);
        }
        return map;
    }

    public static HashMap<String, Object> importXlsx(String xlsxFile){
        HashMap<String,Object> map = new HashMap<>();
        try{
            XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(xlsxFile));
            Sheet sheet = book.getSheetAt(0);
            map.put("sheet",sheet);
            Row title = sheet.getRow(0);
            String msg = "Wrong pattern: The first line of the input excel file must be \"名称\", \"供应商\", \"警戒值\" and \"数量\". as column titles.";
            int i = 0;
            for(i=0;i<title.getLastCellNum();i++){
                Cell cell = title.getCell(i);
                switch (i){
                    case 0:
                        if(!cell.getStringCellValue().equals("名称"))
                            map.put("msg",msg);
                        break;
                    case 1:
                        if(!cell.getStringCellValue().equals("供应商"))
                            map.put("msg",msg);
                        break;
                    case 2:
                        if(!cell.getStringCellValue().equals("警戒值"))
                            map.put("msg",msg);
                        break;
                    case 3:
                        if(!cell.getStringCellValue().equals("数量"))
                            map.put("msg",msg);
                        break;
                    default:
                        map.put("msg",msg);
                        break;
                }
            }
            if(i==title.getLastCellNum())
                map.put("sheet",sheet);
            else
                map.put("sheet",null);
        }catch (IOException e){
            String msg = e.getMessage();
            e.printStackTrace();
            log.error(msg);
            map.put("msg",msg);
            map.put("sheet",null);
        }
        return map;
    }




}
