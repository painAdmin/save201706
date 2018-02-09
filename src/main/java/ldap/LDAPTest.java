package ldap;

import java.util.Arrays;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;

public class LDAPTest {   
	  public LDAPTest() {   
	  }   
	    
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) {   

	   String root = "dc=micmiu,dc=com"; //root   
	   String rootdn="cn=Manager,dc=micmiu,dc=com";
	   
	    @SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();   
	   env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");   
	   env.put(Context.PROVIDER_URL, "ldap://192.168.11.76:389/" + root);      
	   env.put(Context.SECURITY_AUTHENTICATION, "simple");   
	   env.put(Context.SECURITY_PRINCIPAL,rootdn );   
	   env.put(Context.SECURITY_CREDENTIALS, "secret");   
	   DirContext ctx = null;   
	   try {   
	    ctx = new InitialDirContext(env);   
	    System.out.println("认证成功");   
	   }   
	   catch (javax.naming.AuthenticationException e) {   
	     e.printStackTrace();   
	      System.out.println("认证失败");   
	    }   
	   catch (Exception e) {   
	     System.out.println("认证出错：");   
	    e.printStackTrace();   
	   }   
	   
	   if (ctx != null) {   
	     try {   
	        ctx.close();   
	     }   
	      catch (NamingException e) {   
	       //ignore   
	     }   
	    }   
	    System.exit(0);   
	 } 
	 
	} 