package ldap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class LdapUtils {

    /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }  
  
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new BASE64Encoder()).encodeBuffer(key).replace("\r", "").replace("\n", "");  
    } 
	public static void main(String[] args){
		String srcPath="C:\\Users\\pain\\Desktop\\src.txt";
		String writerPath="C:\\Users\\pain\\Desktop\\1501Crl.crl";
//		LdapUtils ldap=new LdapUtils();
//		byte[] buf=ldap.readFile(srcPath);
//		try {
//			byte[] src=decryptBASE64(new String(buf));
//		System.out.println(new BigInteger(src).toString(16));
////			boolean flag=ldap.writeByteToFile(src, writerPath);
////			if(flag){
////				System.out.println("写入完成");
////			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			System.out.println(new String(decryptBASE64("NzM2Njk0NTIwQ0YxODFBNg=="),"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public byte[] readFile(String path){
		File file=new File(path);

	   try {
		   
	    if(!file.exists()){
	    	return new byte[1];
	    }
		FileInputStream fis=new FileInputStream(file);
		FileChannel fileChannel=fis.getChannel();
		ByteBuffer buffer=ByteBuffer.allocate((int)fileChannel.size());
		while((fileChannel.read(buffer))>0){

		}

		fileChannel.close();
		fis.close();
		
		return buffer.array();
	   } catch (Exception e) {
			e.printStackTrace();
	   }
	   return null;
	}
	public boolean writeByteToFile(byte[] bytes,String path){
		File file=new File(path);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
		  FileOutputStream fos=new FileOutputStream(file);
		  fos.write(bytes);
		  fos.flush();
		  fos.close();
		  return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void writerFile(String src,String path){
		File file=new File(path);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
			pw.println(src);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
