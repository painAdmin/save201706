package com.proxyDongtai;

public class ProxyTest {

	public static void main(String[] args){
		// 目标对象 生成
		IUserDao  target=new UserDao();
		IUserDao  proxy=(IUserDao)new ProxyFactory(target).getProxyInstance();
		
	}
}
