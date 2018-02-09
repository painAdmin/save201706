package ldap;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
public class ADOperTest {
 public ADOperTest() {
 }
 public void GetADInfo() {
  String userName = "xxxx"; // 用户名称
  String passwd = "xxx";
  String host = "192.168.100.14"; // AD服务器
  String port = "389"; // 端口
  String domain = "@xxx.com"; // 邮箱的后缀名
  String url = new String("ldap://" + host + ":" + port);
  String user = userName.indexOf(domain) > 0 ? userName : userName
    + domain;
  Hashtable HashEnv = new Hashtable();
  // String adminName ="CN=oyxiaoyuanxy,CN=Users,DC=Hebmc,DC=com";//AD的用户名
  String adminName = "xueqiang"; // 注意用户名的写法：domain\User 或
  // User@domain.com
  adminName = "xueqiang"; // 注意用户名的写法：domain\User 或 User@domain.com
  String adminPassword = "123456"; // 密码
  HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
  HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); // AD User
  HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); // AD Password
  HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,
    "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
  HashEnv.put(Context.PROVIDER_URL, url);
  try {
   LdapContext ctx = new InitialLdapContext(HashEnv, null);
   // 域节点
   String searchBase = "DC=wanda-dev,DC=cn";
   // LDAP搜索过滤器类
   String searchFilter = "objectClass=User";
   // 搜索控制器
   SearchControls searchCtls = new SearchControls(); // Create the
   // search
   // controls
   // 创建搜索控制器
   searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Specify
   // the
   // search
   // scope
   // 设置搜索范围
   // searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE); //
   // Specify the search scope 设置搜索范围
   String returnedAtts[] = { "memberOf", "distinguishedName",
     "Pwd-Last-Set", "User-Password", "cn" };// 定制返回属性
   // String returnedAtts[] = { "url", "whenChanged", "employeeID",
   // "name", "userPrincipalName", "physicalDeliveryOfficeName",
   // "departmentNumber", "telephoneNumber", "homePhone",
   // "mobile", "department", "sAMAccountName", "whenChanged",
   // "mail" }; // 定制返回属性
   searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集
   // 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
   NamingEnumeration answer = ctx.search(searchBase, searchFilter,
     searchCtls);// Search for objects using the filter
   // 初始化搜索结果数为0
   int totalResults = 0;// Specify the attributes to return
   int rows = 0;
   while (answer.hasMoreElements()) {// 遍历结果集
    SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
    System.out.println(++rows
      + "************************************************");
    System.out.println(sr.getName());
    Attributes Attrs = sr.getAttributes();// 得到符合条件的属性集
    if (Attrs != null) {
     try {
      for (NamingEnumeration ne = Attrs.getAll(); ne
        .hasMore();) {
       Attribute Attr = (Attribute) ne.next();// 得到下一个属性
       System.out.println(" AttributeID=属性名："
         + Attr.getID().toString());
       // 读取属性值
       for (NamingEnumeration e = Attr.getAll(); e
         .hasMore(); totalResults++) {
        System.out.println("    AttributeValues=属性值："
          + e.next().toString());
       }
       System.out.println("    ---------------");
       // 读取属性值
       // Enumeration values = Attr.getAll();
       // if (values != null) { // 迭代
       // while (values.hasMoreElements()) {
       // System.out.println("    AttributeValues=属性值："+
       // values.nextElement());
       // }
       // }
       // System.out.println("    ---------------");
      }
     } catch (NamingException e) {
      System.err.println("Throw Exception : " + e);
     }
    }
   }
   System.out
     .println("************************************************");
   System.out.println("Number: " + totalResults);
   ctx.close();
  } catch (NamingException e) {
   e.printStackTrace();
   System.err.println("Throw Exception : " + e);
  }
 }
/* public static void main(String args[]) {
  // 实例化
  ADOperTest ad = new ADOperTest();
  ad.GetADInfo();
 }*/
 
 public static void main(String args[]) {
	    Hashtable HashEnv = new Hashtable();

	    String LDAP_URL = "ldap://xxx.com:389"; //LDAP访问地址
	    String adminName = "xxx@xxx.com"; //注意用户名的写法：domain\User 或 User@domain.com
	    String adminPassword = "xxxx"; //密码

	    HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
	    HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); //AD User
	    HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); //AD Password
	    HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
	    HashEnv.put(Context.PROVIDER_URL, LDAP_URL);

	    try {
	      LdapContext ctx = new InitialLdapContext(HashEnv, null);
	      SearchControls searchCtls = new SearchControls(); //Create the search controls
	      searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); //Specify the search scope

	      String searchFilter = "objectClass=User"; //specify the LDAP search filter
	    
	      String searchBase = "DC=xxx,DC=com"; //Specify the Base for the search//搜索域节点
	      int totalResults = 0;

	           String returnedAtts[] = {
	          "url", "whenChanged", "employeeID", "name", "userPrincipalName",
	          "physicalDeliveryOfficeName", "departmentNumber", "telephoneNumber",
	          "homePhone", "mobile", "department", "sAMAccountName", "whenChanged",
	          "mail"}; //定制返回属性

	      searchCtls.setReturningAttributes(returnedAtts); //设置返回属性集

	      //Search for objects using the filter
	      NamingEnumeration answer = ctx.search(searchBase, searchFilter,searchCtls);
	      
	      if(answer==null||answer.equals(null)){
	    	  System.out.println("answer is null");
	      }else{
	    	  System.out.println("answer not null");
	      }
	      
	      System.out.println(answer.hasMoreElements());
	      
	      while (answer.hasMoreElements()) {
	        SearchResult sr = (SearchResult) answer.next();
	        System.out.println("************************************************");
	        System.out.println(sr.getName());

	        Attributes Attrs = sr.getAttributes();
	        if (Attrs != null) {
	          try {
	            for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
	              Attribute Attr = (Attribute) ne.next();

	              System.out.println(" AttributeID=" + Attr.getID().toString());

	              //读取属性值
	              for (NamingEnumeration e = Attr.getAll(); e.hasMore();totalResults++) {
	                System.out.println("    AttributeValues=" + e.next().toString());
	              }
	              System.out.println("    ---------------");

	              //读取属性值
	              Enumeration values = Attr.getAll();
	              if (values != null) { // 迭代
	                while (values.hasMoreElements()) {
	                  System.out.println("    AttributeValues=" + values.nextElement());
	                }
	              }
	              System.out.println("    ---------------");
	            }
	          }
	          catch (NamingException e) {
	            System.err.println("Throw Exception : " + e);
	          }
	        }
	      }
	      System.out.println("Number: " + totalResults);
	      ctx.close();
	    }

	    catch (NamingException e) {
	      e.printStackTrace();
	      System.err.println("Throw Exception : " + e);
	    }
	}
}
