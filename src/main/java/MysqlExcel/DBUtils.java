package MysqlExcel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {

	private static String driver="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://localhost:5106/htsmcc?3useUnicode=true&characterEncoding=utf8";
	private static String username="root";
	private static String password="83150FC75274C5C5";
	
	private static ComboPooledDataSource db;
	public static Connection getConnection(){
		try {
			db=new ComboPooledDataSource();
			db.setDriverClass(driver);
			db.setJdbcUrl(url);
			db.setUser(username);
			db.setPassword(password);
			
			return db.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		  return null;
		}
	}
	public static List<Map<String,Object>> querySql(String sql){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			Connection conn=getConnection();
			System.out.println("数据库连接获取成功:conn--"+conn);
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData m=rs.getMetaData();
			int columns=m.getColumnCount();
			while(rs.next()){
				Map<String,Object> buf=new HashMap<String,Object>();
				for(int i=1;i<=columns;i++){
					buf.put(m.getColumnName(i), rs.getString(i));	
				}
				list.add(buf);
			}
			
			conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static List<Map<String,Object>> querySql(String sql,Connection conn){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
		//	Connection conn=getConnection();
			System.out.println("数据库连接获取成功:conn--"+conn);
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData m=rs.getMetaData();
			int columns=m.getColumnCount();
			while(rs.next()){
				Map<String,Object> buf=new HashMap<String,Object>();
				for(int i=1;i<=columns;i++){
					buf.put(m.getColumnName(i), rs.getString(i));	
				}
				list.add(buf);
			}
			
			conn.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] args){
		String sql="select * from config";
		List<Map<String,Object>> list=querySql(sql);
		for(Map<String,Object> map:list){
			System.out.println(map);
		}
	}
}















