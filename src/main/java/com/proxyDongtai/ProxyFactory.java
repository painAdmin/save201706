package com.proxyDongtai;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
	// 维护一个目标对象
	private Object target;
	public ProxyFactory(Object target){
		this.target=target;
		
	}
	// 给目标对象生成 代理对象
	public Object getProxyInstance(){
		Object object=Proxy.newProxyInstance(
				       target.getClass().getClassLoader(),//当前目标对象类加载器
				       target.getClass().getInterfaces(),//目标对象实现的接口 
				       new InvocationHandler(){

						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							System.out.println("开始事务执行之前");
							//执行目标对象的方法
							Object returnValue=method.invoke(target, args);
							System.out.println("事务方法执行完成");
							return returnValue;
						}
				    	   
				       });
	    return object;
	}

}
