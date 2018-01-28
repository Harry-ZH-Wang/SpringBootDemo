package com.wzh.demo.service;

import java.util.List;

import com.wzh.demo.domain.UserBean;

public interface IUserService {
	
	public List<UserBean> selectUserByName(String name);

}
