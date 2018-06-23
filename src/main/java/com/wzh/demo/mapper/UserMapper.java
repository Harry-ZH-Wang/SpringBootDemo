package com.wzh.demo.mapper;

import java.util.List;

import com.wzh.demo.domain.UserBean;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

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
	public int addUser(@Param("user") UserBean user);

	/**
	 * 测试连接使用
	 * @param
	 * @return
	 */
	public String test();

	/**
	 * 测试连接使用
	 * @param
	 * @return
	 */
	public List<UserBean> selectTestList();

	public UserBean selectTest();

}
