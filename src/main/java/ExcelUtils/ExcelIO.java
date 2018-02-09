package ExcelUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;


public class ExcelIO {

	public static  int READNUM=10000;//审计导出时每次最大读取到内存的记录数
    public static  int readNUM=800; //审计导出时最多读取的次数
    public static  int MAXSHEETNUM=50*10000; //sheet 最大行数；
	  /**
     * @param workBookNames 工作表名字 默认“sheet1” 多个的话以','隔开
     * @param heads         表头名称数组
     * @param fields        数据库字段名数组 与heads一一对应 只有dataLists为空时才允许为空
     * @param dataLists     数据源 和workBookName的长度对应
     * @return
     */
    public static PrintWriter createWorkBook(PrintWriter pw,ExcelFile file,String workBookName, String[] heads, String[] fields, List<Map<String, Object>> dataList) {
        if (workBookName==null || "".equals(workBookName)) {
            workBookName = "sheet1";//表格名称赋初始值
        }
     
//        if (heads.length != fields.length) {
//            throw new IllegalArgumentException("heads.length must equal fields.length");
//        }
        int size=MAXSHEETNUM;// 2007 sheet 最大行数限制
        int sheetNum=file.getSheetNum();
        int colnum=file.getColumNum();
        
        if(file.isCreateFileHead()){//创建workbook头
        	pw.println(file.fileHead);
        	file.setCreateFileHead(false);
        }
        StringBuffer sb=new StringBuffer();
        for(int j=0;j<dataList.size();j++){
        	if(file.isCreateSheetHead()){//创建sheet头
        		sheetNum++;
        		file.setSheetName(workBookName+"("+sheetNum+")");
            	pw.println(file.getSheetHead());
            	file.setSheetNum(sheetNum);
            	file.setCreateSheetHead(false);
            }
        	sb=new StringBuffer();
        	sb=getRowStr(sb,file,fields,dataList.get(j));
        	pw.println(sb.toString());
        	colnum++;
        	file.setColumNum(colnum);
        	if(colnum>=MAXSHEETNUM){
        		pw.println(file.getSheetFoot());
        		file.setCreateSheetHead(true);	
        	}
        }
        if(file.isCreateFileFoot()){
        	pw.println(file.fileFoot);
        }
        pw.flush();
        return pw;
    }
   public static StringBuffer getRowStr(StringBuffer sb,ExcelFile file,String[] fields,Map<String, Object> map){
	   sb.append(file.rowHead);
	   for(String key:fields){
		   sb.append(file.cellHead);
		   sb.append(map.get(key).toString());
		   sb.append(file.cellFoot); 
	   }
	   sb.append(file.rowFoot);
	   return sb;
   }
   public static void readFileHead(){
	   String path="C:\\Users\\pain\\Desktop\\excelhead1.xml";
	   File file=new File(path);
	 
	try {
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br=new BufferedReader(new InputStreamReader (fis,"UTF-8"));
		
		String buf="";
		StringBuffer sb=new StringBuffer();
		while((buf=br.readLine())!=null){
			sb.append(buf);
		}
		ExcelFile.fileHead=sb.toString();
		br.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	  
   }
   public static void main(String[] args) throws Exception{
	   readFileHead();
	   List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	   for(int i=0;i<10;i++){
		   Map<String,Object> map=new HashMap<String,Object>();
		   map.put("test1", "ceshi"+i);
		   map.put("test2", "ceshiHHHHH");
		   map.put("test3", "ceshiFFFF");
		   list.add(map);
	   }
	   String path="C:\\Users\\pain\\Desktop\\excelhead111111111.xml";
	   File tofile=new File(path);
	   if(!tofile.exists()){
		   tofile.createNewFile();   
	   }else{
		   tofile.delete();
		   tofile.createNewFile();
	   }
	    FileOutputStream fos=new FileOutputStream(tofile,true);
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(fos,"UTF-8"));
		String workBookName="sheetsheet";
		String[] heads={"test1","test2","test3"};
		String[] fields={"test1","test2","test3"};
		ExcelFile file=new ExcelFile();
		long start=System.currentTimeMillis();
		System.out.println("开始写文件");
	   createWorkBook(pw,file,workBookName,heads,fields,list);
	   pw.flush();
	   pw.close();
	   long end =System.currentTimeMillis();
	   
	   System.out.println("写出到文件!");
	   System.out.println("用时："+((end-start)/1000));
   }
}













