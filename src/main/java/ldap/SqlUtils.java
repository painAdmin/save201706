package ldap;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException; 
import javax.naming.Context; 
import javax.naming.NamingEnumeration; 
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes; 
import javax.naming.directory.SearchControls; 
import javax.naming.directory.SearchResult; 
import javax.naming.ldap.Control; 
import javax.naming.ldap.InitialLdapContext; 
import javax.naming.ldap.LdapContext;

import sun.misc.BASE64Decoder; 

public class SqlUtils {
	public static String start="15";
	public static String dely="15";
	public static String crlUrl="";
	public static String like(String c) {
		return c + " like concat('%',?,'%') ";
	}
	private String URL = "ldap://10.52.138.55:389/cn=crl010200,ou=crl02,ou=crl,c=cn?certificateRevocationList,*?base?cn=crl010200"; 
	private String SEARCHDN = "cn=crl010200,ou=crl02,ou=crl,c=cn";
	
	private String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory"; 
	private String BASEDN = ""; //开始查询的节点
	private String rootDN = ""; 
	private String password="";
	private LdapContext ctx = null; 
	private Hashtable env = null; 
	private Control[] connCtls = null; 

    public static void main(String[] args) throws Exception{
    	SqlUtils ldap=new SqlUtils();
    	String src="";
        if(!ldap.LDAP_connect()){
        	src="连接创建失败！";
        }
        src=ldap.getUserDN();// 获取吊销用户列表
        LdapUtils.writerFile(src, "D:\\crl1501.txt");
        new LdapUtils().writeByteToFile(src.getBytes(), "D:\\crl1501.crl");
    }
    public boolean LDAP_connect() { 
    	if(ctx!=null){//如果已经连接了，则一下代码服务改函数
    		return true;
    	}
		env = new Hashtable(); 
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY); 
		env.put(Context.PROVIDER_URL, URL);// LDAP server 
		env.put(Context.SECURITY_PRINCIPAL, rootDN); 
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
		env.put(Context.SECURITY_CREDENTIALS, password); 
	    // 此处若不指定用户名和密码,则自动转换为匿名登录 
	
	
		try { 
		ctx = new InitialLdapContext(env, connCtls); 
		System.out.println("认证成功！");
		return true;
		} catch (NamingException e) { 
		  e.printStackTrace();
		  return false;
		} 
    } 
    int totalResults = 0;

	public String getUserDN() { 
		Map<String,Object> res=new HashMap<String,Object>();
		String userDN = ""; 
		List<String> list=new ArrayList<String>();
		LDAP_connect(); 
		try { 
			String filters = "objectClass=*"; //objectClass=*  --查询所有的
			String[] returnedAtts = { "objectClass","mail", "description", "certificateRevocationList" }; 
			SearchControls constraints = new SearchControls(); 
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE); 
						
			if (returnedAtts != null && returnedAtts.length > 0) { 
			  constraints.setReturningAttributes(returnedAtts); 
			} 
			NamingEnumeration en = ctx.search(BASEDN, filters, constraints);    // BaseDn  开始查询的节点
			if (en == null) { 
			   System.out.println("Have no NamingEnumeration."); 
			} 
			if (!en.hasMoreElements()) { 
			    System.out.println("Have no element."); 
			} else { 
				while (en != null && en.hasMoreElements()) { 
				Object obj = en.nextElement(); 

				if(obj instanceof SearchResult){
					SearchResult si=(SearchResult)obj;
					Attributes Attrs=si.getAttributes();
					if(Attrs!=null){

				          try {
				            for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
				              Attribute Attr = (Attribute) ne.next();
                              String attName=Attr.getID().toString();
				              System.out.println(" AttributeID=" + attName);

				              //读取属性值
				              Enumeration values = Attr.getAll();
				              String attValue="";
				              byte[] test=null;
				              if (values != null) { // 迭代
				                while (values.hasMoreElements()) {
                                   //list=new ArrayList<String>();
				                	//System.out.println(values.nextElement());
				                	Object c=values.nextElement();
				                	if(attName.equals("certificateRevocationList;binary")){
				                		test=(byte[])c;            
				                		return new BigInteger(test).toString(16);
				                	}
				                	
				                  System.out.println("    AttributeValues=" + c.toString());
				                }
				              }
				              System.out.println("    ---------------");
				             // res.put(attName, attValue);//将每个属性以key-vaklue形式存储返回  
				            }
				          }
				          catch (NamingException e) {
				            System.err.println("Throw Exception : " + e);
				          }
				        
					}
				}
				System.out.println(userDN); 
				} 
			} 
		} catch (Exception e) { 
		   System.out.println("Exception in search():" + e); 
		} 
	 return "下载失败！";
    } 
	 /* 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }
    /**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
            int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }
    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     * 
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }
    
}
