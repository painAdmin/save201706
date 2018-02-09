package ExcelUtils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import sun.misc.BASE64Encoder;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ExcelUtils {

    public static  int READNUM=10000;//审计导出时每次最大读取到内存的记录数
    public static  int readNUM=800; //审计导出时最多读取的次数
    public static  int MAXSHEETNUM=50*10000; //sheet 最大行数；

    public static WritableWorkbook createWorkBook(int index,WritableWorkbook  srcWorkbook,String workBookName, String[] heads, String[] fields, List<Map<String, Object>> dataList) {
        WritableWorkbook  workbook=srcWorkbook;      
        if ("".equals(workBookName)) {
            workBookName = "sheet1";//表格名称赋初始值
        }
        if (heads.length != fields.length) {
            throw new IllegalArgumentException("heads.length must equal fields.length");
        }
        WritableSheet sheet=null;
        int rowNum=0;
        if(index==-1){
            sheet = createSheet(workbook, workBookName, heads);
            rowNum=1;
        }else{
            sheet=workbook.getSheet(index);
            rowNum =sheet.getRows();
        }
        // 填充数据
        for (Map<String, Object> map : dataList) {
            String[] strings = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                strings[i] = map.get(fields[i]).toString();
            }
            if(rowNum>=65534){//当超过当前sheet最大值时创建新的sheet
                String workName=workBookName+workbook.getNumberOfSheets();
                sheet = createSheet(workbook, workName, heads);
                rowNum=1;   
            }
            ceateRow(sheet,strings);
            rowNum++;
        }
        return workbook;
    }
    public static WritableSheet createSheet(WritableWorkbook  workbook,String workBookName,String[] heads){
        int sheetNum=workbook.getNumberOfSheets();
        WritableSheet sheet = workbook.createSheet(workBookName,sheetNum);  
        ceateRow(sheet,heads);
        return sheet;
    }
    public static WritableSheet ceateRow(WritableSheet sheet,String[] data){
        int rows=sheet.getRows();
        for(int i=0;i<data.length;i++){
            try {
                sheet.addCell(new Label(i,rows , data[i]));
            } catch (Exception e) {
                e.printStackTrace();
                return sheet;
            }
        }
        return sheet;
    }
    public static void main(String[] args) throws Exception{
        String path="C:\\Users\\pain\\Desktop\\excelhead111111111.xls";
        File file=new File(path);
        if(!file.exists()){
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        OutputStream os=new FileOutputStream(file);
        WritableWorkbook wbook = Workbook.createWorkbook(file); // 建立excel文件   
        String[] heads={"AA","BB","CC"};
        String[] fields={"test1","test2","test3"};
        List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
        for(int i=0;i<20;i++){
            Map<String,Object> buf=new HashMap<String,Object>();
            buf.put("test1", "ceshiAA");
            buf.put("test2", "ceshiBB");
            buf.put("test3", "ceshi中国");
            dataList.add(buf);
        }
        String workBookName="loginAudit";
        int index=-1;
        WritableWorkbook resWork=createWorkBook(index,wbook,workBookName,heads,fields,dataList);
        resWork.write();
        resWork.close();
        System.out.println("写出完成！");
    }
}
