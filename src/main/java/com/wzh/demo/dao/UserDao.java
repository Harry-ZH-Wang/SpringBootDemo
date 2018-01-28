package com.wzh.demo.dao;

import java.util.List;

import com.wzh.demo.domain.UserBean;

public interface UserDao {

	public List<UserBean> selectUserByName(String name);
}
