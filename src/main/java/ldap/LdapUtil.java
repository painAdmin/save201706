package ldap;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;
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
/**
 * 用户登陆认证,LDAP跨域认证，通过LDAP对用户进行更新
 * 
 * @author xlj
 * @date 2015.07.10
 */
public class LdapUtil {

	private static DirContext ctx;

	// LDAP服务器端口默认为389
	private static final String LDAP_URL = "ldap://192.168.11.76:389";

	// ROOT根据此参数确认用户组织所在位置
	private static final String LDAP_PRINCIPAL = "cn=Manager,dc=micmiu,dc=com";

	// LDAP驱动
	private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	//private static Logger logger = Logger.getLogger(LdapUtil.class);

	/**** 测试 ****/
	public static void main(String[] args) {
		LdapUtil.getLoginContext();
		LdapUtil.addUserLdap("10000", "123456");
//		LdapUtil.updatePasswordLdap("10000", "1234567");
//		LdapUtil.deleteUserLdap("10000");
	}

	// 通过连接LDAP服务器对用户进行认证，返回LDAP对象
	public static DirContext getLoginContext() {
		String root = "dc=micmiu,dc=com"; //root   
		   String rootdn="cn=Manager,dc=micmiu,dc=com";
		for (int i = 0; i < 2; i++) { // 验证次数
			    @SuppressWarnings("rawtypes")
				Hashtable env = new Hashtable();   
			   env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");   
			   env.put(Context.PROVIDER_URL, "ldap://192.168.11.76:389/" + root);      
			   env.put(Context.SECURITY_AUTHENTICATION, "simple");   
			   env.put(Context.SECURITY_PRINCIPAL,rootdn );   
			   env.put(Context.SECURITY_CREDENTIALS, "secret");   
			try {
				// 连接LDAP进行认证
				ctx = new InitialDirContext(env);
				System.out.println("认证成功");	
			} catch (javax.naming.AuthenticationException e) {
				System.out.println("认证失败");
			} catch (NamingException err) {
			} catch (Exception e) {
				System.out.println("认证出错：");
				e.printStackTrace();
			}
		}
		return ctx;
	}

	// 将输入用户和密码进行加密算法后验证
	public static boolean verifySHA(String ldappw, String inputpw) {

		// MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 取出加密字符
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// 解码BASE64
		byte[] ldappwbyte = Base64.decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// 前20位是SHA-1加密段，20位后是最初加密时的随机明文
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}

		// 把用户输入的密码添加到摘要计算信息
		md.update(inputpw.getBytes());
		// 把随机明文添加到摘要计算信息
		md.update(salt);

		// 按SSHA把当前用户密码进行计算
		byte[] inputpwbyte = md.digest();

		// 返回校验结果
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	// 添加用户
	public static boolean addUserLdap(String account, String password) {
//		boolean success = false;
//		try {
//			ctx = LdapUtil.getLoginContext();
//			BasicAttributes attrsbu = new BasicAttributes();
//			BasicAttribute objclassSet = new BasicAttribute("objectclass");
//			objclassSet.add("person");
//			objclassSet.add("top");
//			objclassSet.add("organizationalPerson");
//			objclassSet.add("inetOrgPerson");
//			attrsbu.put(objclassSet);
//			attrsbu.put("sn", account);
//			attrsbu.put("uid", account);
//			attrsbu.put("userPassword", password);
//			ctx.createSubcontext("cn=" + account + ",ou=People", attrsbu);
//			ctx.close();
//			return true;
//		} catch (NamingException ex) {
//			ex.printStackTrace();
//			try {
//				if (ctx != null) {
//					ctx.close();
//				}
//			} catch (NamingException namingException) {
//				namingException.printStackTrace();
//			}
//			System.out.println("--------->>添加用户失败");
//		}


		Attributes attrs = new BasicAttributes(true);
		Attribute objclass = new BasicAttribute("objectclass");
		Attribute pass = new BasicAttribute("userpassword");

		String newUserName = "fancionwang";

		/** add password */
		pass.add("111111");

		/** add ObjectClass */
		String[] attrObjectClassPerson = { "user", "organizationalPerson", "person", "top" };
		Arrays.sort(attrObjectClassPerson);
		for (String ocp : attrObjectClassPerson) {
			objclass.add(ocp);
		}

		/** set attr */
		attrs.put(pass);
		attrs.put(objclass);

		String userDN = "CN=" + newUserName + "," + "DC=micmiu,DC=com";

		// int UF_ACCOUNTDISABLE = 0x0002;
		int UF_PASSWD_NOTREQD = 0x0020;
		// int UF_PASSWD_CANT_CHANGE = 0x0040;
		int UF_NORMAL_ACCOUNT = 0x0200;
		int UF_DONT_EXPIRE_PASSWD = 0x10000;
		// int UF_PASSWORD_EXPIRED = 0x800000;

		attrs.put("sn", "wang");
		attrs.put("givenName", "fancion");
		attrs.put("cn", newUserName);
		attrs.put("displayName", newUserName);
		attrs.put("mail", "fancionwang@163.com");
		attrs.put("description", "test");
		attrs.put("userPrincipalName", "fancionwang@wilcom.com.cn");
		attrs.put("sAMAccountName", newUserName);
		attrs.put("msDS-SupportedEncryptionTypes", "0");
		
		/** 设置传真 */
		attrs.put("facsimileTelephoneNumber", "fancionwang.fax.wiocom.com.cn");
		/** 寻呼机 */
		attrs.put("pager", "****");
		/** ip电话 */
		attrs.put("ipPhone", "****");
		/** 家庭电话 */
		attrs.put("homePhone", "********");
		/** 移动电话 */
		attrs.put("mobile", "***********");

		/** 设置账户信息 */
		attrs.put("userAccountControl",
				Integer.toString(UF_DONT_EXPIRE_PASSWD + UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD));

		try {
			ctx.createSubcontext(userDN, attrs);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ctx.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("<<<:[ADD success]:>>>");
		return false;
	}
  
	// 修改密码
	public static boolean updatePasswordLdap(String account, String password) {
		boolean success = false;
		try {
			ctx = LdapUtil.getLoginContext();
			ModificationItem[] modificationItem = new ModificationItem[1];
			modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));
			ctx.modifyAttributes("cn=" + account + ",ou=People", modificationItem);
			ctx.close();
			return true;
		} catch (NamingException ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
			System.out.println("--------->>修改密码失败");
		}
		return success;
	}

	// 删除用户
	public static boolean deleteUserLdap(String account) {
		try {
			ctx = LdapUtil.getLoginContext();
			ctx.destroySubcontext("cn=" + account);
		} catch (Exception ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
		//	System.out.println("--------->>删除用户失败");
			return false;
		}
		return true;
	}

	// 关闭LDAP服务器连接
	public static void closeCtx() {
		try {
			ctx.close();
		} catch (NamingException ex) {
			System.out.println("--------->> 关闭LDAP连接失败");
		}
	}
}