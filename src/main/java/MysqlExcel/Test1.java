package MysqlExcel;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;





public class Test1 {
	
	public static void main(String[] args){
		String path="C:\\Users\\pain\\Desktop\\excelhead111111111.xlsx";
		fdzp(path);
		System.out.println("写出完成！");
	}

	public static void fdzp(String path) {
		try {
			File file=new File(path);
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
            OutputStream os=new FileOutputStream(file);
	        WritableWorkbook wbook = Workbook.createWorkbook(file); // 建立excel文件   
	        String tmptitle = "房客信息"; // 标题   
	        WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // sheet名称  
	        
	        wsheet.setColumnView(0, 30);
			// 开始生成主体内容                   
		
           for(int i=0;i<65535;i++){
        	   wsheet.addCell(new Label(0, i, "中国AA"));  
        	   wsheet.addCell(new Label(1, i, "中国BB"));  
        	   wsheet.addCell(new Label(2, i, "中国CC"));  
           }
			         
			// 主体内容生成结束           
			wbook.write(); // 写入文件   
			wbook.close();  
			
		}catch(Exception ex) { 
			
		} 
	}
}
