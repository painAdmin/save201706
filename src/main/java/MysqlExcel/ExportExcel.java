package MysqlExcel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class ExportExcel {

	public static void createExcel(List<List<String>> list){
		ExcelWriter writer=ExcelUtil.getWriter("C:\\Users\\pain\\Desktop\\ceshi.xlsx");
		//ExcelWriter writer = new ExcelWriter("C:\\Users\\pain\\Desktop\\ceshi.xlsx");
//		writer.passCurrentRow();
//
//		//合并单元格后的标题行，使用默认标题样式
//		writer.merge(list.size() - 1, "测试标题");
		//一次性写出内容
	
		writer.write(list);
		//关闭writer，释放内存
		writer.close();
	}
	public static void main(String[] args){
		String sql="select * from timelog";
		Connection conn=DBUtils.getConnection();
		long start=System.currentTimeMillis();
		List<Map<String,Object>> list=DBUtils.querySql(sql,conn);
//		List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
//		List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
//		List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
//		List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
//		List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
      
//		List<List<String>> list = CollUtil.newArrayList(row1, row2, row3, row4, row5);
		List<List<String>> res=new ArrayList<List<String>>();
		list=list.subList(1, 90000);
		System.out.println("开始写出到excel！");
		for(Map<String,Object> map:list){
			Collection<Object> coll=map.values();
			List<String> buf=new ArrayList<String>();
			for(String key:map.keySet()){
				buf.add(map.get(key)+"");
			}
			res.add(buf);
			
		}
		createExcel(res);
		long end=System.currentTimeMillis();
		System.out.println("用时："+(end-start)/1000);
		System.out.println("导出数据到Excel完成！");
	}
}
