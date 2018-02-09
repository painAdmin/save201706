package MysqlExcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Test {

	public static void main(String[] args) {
		String fromPath="C:\\Users\\pain\\Desktop\\test.xml";
		String toPath="C:\\Users\\pain\\Desktop\\test2.xls";
		writerExcel(toPath,fromPath);
		System.out.println("写出到文件完成");
	}
	public void test(){
		String path="C:\\Users\\pain\\Downloads\\loginAudit_2018-01-23 10-02-58.xls";
		File file=new File(path);

		long time =file.lastModified();
		Date date=new Date(time);
		System.out.println("最后修改时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		System.out.println("最后修改时间:"+(time/1000));
	}
	public static void writerExcel(String toPath,String fromPath){
		File file=new File(toPath);
		
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos=new FileOutputStream(file,true);
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(fos,"UTF-8"));
			
			FileInputStream fis=new FileInputStream(fromPath);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			
			String bufLine="";
			while((bufLine=br.readLine())!=null){
				pw.println(bufLine);
			}
			pw.flush();
			pw.println("<Worksheet ss:Name=\"programAudit\">");
			pw.println("<Table ss:ExpandedColumnCount=\"11\" ss:ExpandedRowCount=\"11\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"54\" ss:DefaultRowHeight=\"13.5\">");
			pw.println("<Row><Cell><Data ss:Type=\"String\">主机标识</Data></Cell></Row>");
			pw.println(" </Table>");
			pw.println(" <WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\"><PageSetup><Header x:Margin=\"0.3\"/><Footer x:Margin=\"0.3\"/><PageMargins x:Left=\"0.699305555555556\" x:Right=\"0.699305555555556\" x:Top=\"0.75\" x:Bottom=\"0.75\"/></PageSetup><Selected/><TopRowVisible>0</TopRowVisible><LeftColumnVisible>0</LeftColumnVisible><PageBreakZoom>100</PageBreakZoom><FreezePanes/><FrozenNoSplit/><SplitHorizontal>1</SplitHorizontal><TopRowBottomPane>1</TopRowBottomPane><ActivePane>2</ActivePane><Panes><Pane><Number>3</Number></Pane><Pane><Number>2</Number><ActiveRow>40</ActiveRow><ActiveCol>6</ActiveCol><RangeSelection>R41C7</RangeSelection></Pane></Panes><ProtectObjects>False</ProtectObjects><ProtectScenarios>False</ProtectScenarios></WorksheetOptions></Worksheet></Workbook>");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
