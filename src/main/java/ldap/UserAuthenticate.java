package ldap;

import java.util.Enumeration;
import java.util.Hashtable; 
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


public class UserAuthenticate { 
	private String URL = "ldap://192.168.11.76:389"; 
	private String SEARCHDN = "ou=Developer,dc=micmiu,dc=com";
	
	private String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory"; 
	private String BASEDN = "dc=micmiu,dc=com"; 
	private LdapContext ctx = null; 
	private Hashtable env = null; 
	private Control[] connCtls = null; 

    public static void main(String[] args){
    	UserAuthenticate ldap=new UserAuthenticate();
    	String userDn=ldap.getUserDN("");
    	//System.out.println(userDn);
    }
    private void LDAP_connect() { 
		env = new Hashtable(); 
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY); 
		env.put(Context.PROVIDER_URL, URL);// LDAP server 
		env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=micmiu,dc=com"); 
		env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
		env.put(Context.SECURITY_CREDENTIALS, "secret"); 
	// 此处若不指定用户名和密码,则自动转换为匿名登录 
	
	
		try { 
		ctx = new InitialLdapContext(env, connCtls); 
		System.out.println("认证成功！");
		} catch (NamingException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
    } 
    int totalResults = 0;

	private String getUserDN(String email) { 
		String userDN = ""; 
		
		
		LDAP_connect(); 
		
		
		try { 
//			String filters = "(&(&(objectCategory=person)(objectClass=inetOrgPerson))(sAMAccountName=elbert.chenh))"; 
			String filters = "objectClass=organizationalUnit"; //objectClass=*  --查询所有的
			String[] returnedAtts = { "objectClass","mail", "description", "employeeID" }; 
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

//				if (obj instanceof SearchResult) { 
//					SearchResult si = (SearchResult) obj; 
//					Attributes userInfo = si.getAttributes(); 
//					userDN += userInfo.toString(); 
//					userDN += "," + BASEDN; 
//				} else { 
//				    System.out.println(obj.toString()); 
//				} 
				if(obj instanceof SearchResult){
					SearchResult si=(SearchResult)obj;
					Attributes Attrs=si.getAttributes();
					if(Attrs!=null){

				          try {
				            for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
				              Attribute Attr = (Attribute) ne.next();

				              System.out.println(" AttributeID=" + Attr.getID().toString());

//				              //读取属性值
//				              for (NamingEnumeration e = Attr.getAll(); e.hasMore();totalResults++) {
//				                System.out.println("    AttributeValues=" + e.next().toString());
//				              }
//				              System.out.println("    ---------------");

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
				System.out.println(userDN); 
				} 
			} 
		} catch (Exception e) { 
		   System.out.println("Exception in search():" + e); 
		} 
		
	
	return userDN; 
    } 


	public boolean authenricate(String ID, String password) { 
		boolean valide = false; 
		String userDN = getUserDN(ID); 
		try { 
		ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN); 
		ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password); 
		ctx.reconnect(connCtls); 
		System.out.println(userDN + " is authenticated"); 
		valide = true; 
		} catch (AuthenticationException e) { 
		System.out.println(userDN + " is not authenticated"); 
		System.out.println(e.toString()); 
		valide = false; 
		} catch (NamingException e) { 
		System.out.println(userDN + " is not authenticated"); 
		valide = false; 
		} 
		
		
		return valide; 
   } 
}
