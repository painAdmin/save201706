package ExcelUtils;

public class ExcelFile {

	public int columnWidth=54;
	public int columnHeight=14;
	
	private String sheetName="sheet";
	//------------------------------------------excel 属性----------------------------
	// workbook开始
	public static String fileHead="";
	
	private int sheetNum=0;
	private int columNum=0;
	private boolean isCreateFileHead=true;
	private boolean isCreateSheetHead=true;
	private boolean isCreateFileFoot=true;
	
	
	
	// sheet body
	private String sheetHead="<Worksheet ss:Name=\""+sheetName+"\">";
	
	public String sheetTableHead="<Table ss:ExpandedColumnCount=\"1\" ss:ExpandedRowCount=\"3\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\""+columnWidth+"\" ss:DefaultRowHeight=\""+columnHeight+"\">";
	
	public String rowHead="<Row>";
	public String cellHead="<Cell><Data ss:Type=\"String\">";
	public String data=null;  //cell数据 都是String类型
	public String cellFoot="</Data></Cell>";
	public String rowFoot="</Row>";
	
	public String sheetTableFoot="</Table>";
	public String WorksheetOptions="<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\"><PageSetup><Header x:Margin=\"0.3\"/><Footer x:Margin=\"0.3\"/><PageMargins x:Left=\"0.699305555555556\" x:Right=\"0.699305555555556\" x:Top=\"0.75\" x:Bottom=\"0.75\"/></PageSetup><Selected/><TopRowVisible>2</TopRowVisible><LeftColumnVisible>0</LeftColumnVisible><PageBreakZoom>100</PageBreakZoom><Panes><Pane><Number>3</Number><ActiveRow>4</ActiveRow><ActiveCol>0</ActiveCol><RangeSelection>R5C1</RangeSelection></Pane></Panes><ProtectObjects>False</ProtectObjects><ProtectScenarios>False</ProtectScenarios></WorksheetOptions>";
	public String sheetFoot="</Worksheet>";
	
	//excel结尾
	public String fileFoot="</Workbook>";
	
	
	
	public String getSheetHead(){
		return sheetHead+sheetTableHead;
	}
	public String getSheetFoot(){
		return sheetTableFoot+WorksheetOptions+sheetFoot;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName){
		this.sheetHead="<Worksheet ss:Name=\""+sheetName+"\">";
	}
	public int getSheetNum() {
		return sheetNum;
	}
	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}
	public int getColumNum() {
		return columNum;
	}
	public void setColumNum(int columNum) {
		this.columNum = columNum;
	}
	public boolean isCreateFileHead() {
		return isCreateFileHead;
	}
	public void setCreateFileHead(boolean isCreateFileHead) {
		this.isCreateFileHead = isCreateFileHead;
	}
	public boolean isCreateSheetHead() {
		return isCreateSheetHead;
	}
	public void setCreateSheetHead(boolean isCreateSheetHead) {
		this.isCreateSheetHead = isCreateSheetHead;
	}
	public boolean isCreateFileFoot() {
		return isCreateFileFoot;
	}
	public void setCreateFileFoot(boolean isCreateFileFoot) {
		this.isCreateFileFoot = isCreateFileFoot;
	}
}
