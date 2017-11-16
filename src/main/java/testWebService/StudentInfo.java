package testWebService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(targetNamespace="http://student.info/")
public class StudentInfo {

	@WebMethod
	public String  getName(){
		return new Student().getName();
	}
	@WebMethod
	public int getAge(){
		return new Student().getAge();
	}
	@WebMethod
	public String getStudentInfo(){
		return new Student().toString();
	}
	public static void main(String[] args){
		Endpoint.publish("http://localhost:8077/StudentInfo", new StudentInfo());
		System.out.println("学生信息服务端已启动.......");
	}
}
