package com.webservice;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebParam;
import javax.xml.ws.Endpoint;
@SuppressWarnings("restriction")
@WebService(serviceName="MyService",targetNamespace="http://ws.itcast.cn/")
public class HelloService {

	@WebMethod(operationName="AliassayHello")
	@WebResult(name="Myreturn")
	public String sayHello(@WebParam(name="name") String name){
		return "hello:"+name;
	}
    @WebMethod(exclude=true) //当前方法不被发布出去
	public String sayGoodbye(String name){
		return "goodbye:"+name;
		
	}
	public static void main(String[] args){
		Endpoint.publish("http://127.0.0.1:8077/test.cm/", new HelloService());
		System.out.println("WebService 已经启动。。。。。。");
		
	}
}
