package com.proxyJingtai;

public class ProxyClass {

	public static void main(String[] args){
		//目标对象
		UserDao target=new UserDao();
		//代理对象
		UserDaoProxy proxy=new UserDaoProxy(target);
		proxy.save();
	}
}
