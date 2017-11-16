package testInetWorm;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDemo {

	private static String savepath="G:\\";
	//等待趴的url
	private static List<String> allwaitList=new ArrayList<String>();
	//趴取过的url
	private static Set<String> alloverList=new HashSet<String>();
	//记录url趴取的深度
	private static Map<String,Integer> allurldepth=new HashMap<String,Integer>();
	//最大趴取深度
	private static int maxdepth=2;
	public static void main(String[] args){
	       String url="http://book.dangdang.com/";
	       workurl(url,1);
	}
	public static void workurl(String strurl,int depth){
		// 判断当前url是否读取过
		if(!alloverList.contains(strurl) || depth>maxdepth){
			try{
				URL url=new URL(strurl);
				URLConnection conn=url.openConnection();
				InputStream is=conn.getInputStream();
				System.out.println(conn.getContentEncoding());
				BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String line=null;
				//提取网页链接
				Pattern p=Pattern.compile("<a> .*href=.+</a>");
				
				PrintWriter pw=new PrintWriter(new File(savepath+System.currentTimeMillis()+".txt"),"UTF-8");
				while((line=br.readLine())!=null){
					pw.println(line);
					Matcher m=p.matcher(line);
					while(m.find()){
						String href=m.group();
						href=href.substring(href.indexOf("href="));
						if(href.charAt(5)=='\"'){
							href=href.substring(6);
						}else{
							href=href.substring(5);
						}
						try{
							href=href.substring(0, href.indexOf("\""));
						}catch(Exception e){
							try{
								href=href.substring(0,href.indexOf(" "));
							}catch(Exception e1){
								href=href.substring(0,href.indexOf(">"));
							}
						}
						if(href.startsWith("http") || href.startsWith("https")){
							allwaitList.add(href);
							allurldepth.put(href,depth+1);
						}
							
					}
				}
				pw.close();
				br.close();
				
			}catch(Exception e){
				
			}
			alloverList.add(strurl);
			System.out.println(strurl +"网页爬取完，已爬去数量：+"+alloverList.size()+"剩余爬去数量："+allwaitList.size());
			//递归的方式查取剩余的url
			String nextUrl=allwaitList.get(0);
			allwaitList.remove(0);
			workurl(nextUrl,allurldepth.get(nextUrl));
		}
	}
	public static void getResult(){
		Pattern p=Pattern.compile("\\d{11}");
	       String content="<div><div class='jg666'>[转让]<a href='/17610866588' title='手机号码17610866588估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>17610866588</a>由 张云龙 300元转让,联系电话：17610866588</div><div class='jg666'>[转让]<a href='/17777351513' title='手机号码17777351513估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>17777351513</a>由 胡俊宏 888元转让,QQ：762670775,联系电话：17777351513,可以小砍价..</div><div class='jg666'>[求购]<a href='/15019890606' title='手机号码15019890606估价评估_值多少钱_归属地查询_测吉凶_数字含义_求购转让信息' class='lj44'>15019890606</a>由 张宝红 600元求购,联系电话：15026815169</div><div class='jg666'>";
	       Matcher m=p.matcher(content);
	       Set<String> set=new HashSet<String>();
	       while(m.find()){
	    	   String value=m.group();
	    	   set.add(value);
	       }
	       
	       System.out.println(set);
	}
	public static void getResourse(){
		//确定数据源网址
				String resultUrl="http://www.dangdang.com/?_utm_brand_id=11106&_ddclickunion=460-5-biaoti|ad_type=0|sys_id=1";
				try{
					URL url=new URL(resultUrl);
					//通过url建立网页连接
					URLConnection conn=url.openConnection();
					InputStream is=conn.getInputStream();
					System.out.println(conn.getContentEncoding());
					// 按行读取网页数据
					BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
					String line=null;
					while((line=br.readLine())!=null){
						System.out.println(line);
					}
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
	}
}
