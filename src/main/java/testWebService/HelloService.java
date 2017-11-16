package testWebService;



import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
@WebService
public class HelloService {

	@WebMethod
	public String sayHello(String name){
		return "hello:"+name;
	}
	public String sayGoodbye(String name){
		return "goodbye:"+name;
		
	}
	public static void main(String[] args){
		Endpoint.publish("http://localhost:8077/Service/HelloService", new HelloService());
		System.out.println("WebService 已经启动。。。。。。");
		
	}
}
