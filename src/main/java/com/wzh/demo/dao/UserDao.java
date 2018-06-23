package com.wzh.demo.dao;

import java.util.List;

import com.wzh.demo.domain.UserBean;

public interface UserDao {

	/**
	 * 根据姓名查找用户
	 * @param name
	 * @return
	 */
	public List<UserBean> selectUserByName(String name);

	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	public int addUser(UserBean user);

	/**
	 * 测试连接使用
	 * @param
	 * @return
	 */
	public String test();

	public List<UserBean> selectTestList();

	public UserBean selectTest();
}
