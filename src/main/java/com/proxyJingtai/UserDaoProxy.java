package com.proxyJingtai;

public class UserDaoProxy implements IUserDao{

	// 保存目标对象
	private IUserDao target;
	public UserDaoProxy(IUserDao target){
		this.target=target;
	}
	
	public void save() {
		System.out.println("开始执行 事务....");
		target.save();
		System.out.println("提交事务");
		
	}

}
