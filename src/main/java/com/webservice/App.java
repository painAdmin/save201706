package com.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class App {

	public static void main(String[] args) throws MalformedURLException{
		URL wsdURL=new URL("http://127.0.0.1:8077/test.cm/");
		Service s=Service.create(wsdURL, new QName("http://ws.itcast.cn/","MyServiceService"));
		HelloService hs=s.getPort(new QName("http://ws.itcast.cn/","MyServicePort"),HelloService.class);
		String ret=hs.sayHello("hanwei");
		System.out.println(ret);
	}
}
