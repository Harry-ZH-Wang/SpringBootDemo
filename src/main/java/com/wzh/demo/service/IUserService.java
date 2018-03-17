package com.wzh.demo.service;

import java.util.List;

import com.wzh.demo.domain.UserBean;

public interface IUserService {
	
	public List<UserBean> selectUserByName(String name);

	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	public int addUser(UserBean user);

	/**
	 *
	 * @param user
	 * @return
	 */
	public int addUserByProgramming(UserBean user);

}
