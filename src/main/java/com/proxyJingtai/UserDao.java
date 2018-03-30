package com.proxyJingtai;
/**
 * 目标对象
 * 接口实现
 * @author pain
 *
 */
public class UserDao implements IUserDao{

	public void save() {
		System.out.println("目标对象保存执行。。。成功");
		
	}

}
