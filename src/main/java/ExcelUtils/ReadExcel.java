package ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

public class ReadExcel {

	public static void main(String[] args){
		String path="C:\\Users\\pain\\Downloads\\loginAudit_2018-02-05_14-55-00.xlsx";
		String toPath="C:\\Users\\pain\\Downloads\\loginAudit_2018-02-05_14-55-00.txt";
		File file=new File(path);
		File toFile=new File(toPath);
		System.out.println(file.getParentFile().getPath());
//		long start=System.currentTimeMillis();
//		try {
//			if(toFile.exists()){
//				toFile.delete();
//			}else{
//				toFile.createNewFile();
//			}
//			PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(toFile,true),"UTF-8"));
//			
//			Workbook wk=StreamingReader.builder()
//					.rowCacheSize(1000)      //缓存到内存中的行数，默认是10
//					.bufferSize(1024*1024)       //读取资源时，缓存到内存的字节数 默认是1024
//					.open(file);            // 打开资源。只能是file 或者流  注意是只能打开 xlsx 格式文件
//			int sheetLength=wk.getNumberOfSheets();
//			boolean flag=true;
//			for(int i=0;i<sheetLength;i++){
//				Sheet sheet=wk.getSheetAt(i);
//				for(Row row:sheet){
//					if(flag){
//						flag=false;
//						continue;
//					}
//					for(Cell cell:row){
//						pw.print((cell.getStringCellValue()+",").getBytes("UTF-8"));
//					}
//					pw.println();
//				}
//				flag=true;
//			}	
//			pw.flush();
//			pw.close();
//			System.out.println("转换文件完成");
//			long end=System.currentTimeMillis();
//			long sec=(start-end)/1000;
//			System.out.println("用时："+sec);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
	}
}
