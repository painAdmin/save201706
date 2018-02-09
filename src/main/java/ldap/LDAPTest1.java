package ldap;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.naming.directory.*;
/**
 *
 * @author Prince.Zhang
 */
public class LDAPTest1 {
    private Hashtable getEnv() {
    	String root="dc=micmiu,dc=com";
    	String rootdn="cn=Manager,dc=micmiu,dc=com";
    	@SuppressWarnings("rawtypes")
        Hashtable ht = new Hashtable();
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ht.put(Context.PROVIDER_URL, "ldap://192.168.11.76/"+root);
        ht.put(Context.SECURITY_AUTHENTICATION, "simple");
        ht.put(Context.SECURITY_PRINCIPAL, rootdn);
        ht.put(Context.SECURITY_CREDENTIALS, "secret");    
        return ht;
    }
    private DirContext getContext() {
        DirContext dc = null;
        try {
            dc = new InitialDirContext(this.getEnv());
            System.out.println("Authentication Successful");
        } catch (javax.naming.AuthenticationException ex) {
            ex.printStackTrace();
            System.out.println("Authentication Failed");
        } catch (Exception x) {
            x.printStackTrace();
            System.out.println("Error!");
        }
        return dc;
    }
    private void print() throws NamingException {
        DirContext dc=this.getContext();
        String root = "dc=micmiu,dc=com";
        StringBuffer output = new StringBuffer();
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration enu = dc.search(root, "uid=*", ctrl);//root是入口，不能设置成空，网上很多源码都设为空的。
        while (enu.hasMore()) {
            SearchResult sr = (SearchResult) enu.next();
            Attributes ab = sr.getAttributes();
            NamingEnumeration values = ((BasicAttribute) ab.get("userPassword")).getAll();
            while (values.hasMore()) {
                if (output.length() > 0) {
                    output.append("|");
                }
                output.append(values.next().toString());
            }
        }
         System.out.println("The Password:" + output.toString());
       if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LDAPTest1 ldap=new LDAPTest1();
        try {
            ldap.print();
        } catch (NamingException ex) {
            Logger.getLogger(LDAPTest1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

