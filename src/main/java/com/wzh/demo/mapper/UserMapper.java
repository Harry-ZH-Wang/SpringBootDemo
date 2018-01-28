package com.wzh.demo.mapper;

import java.util.List;

import com.wzh.demo.domain.UserBean;


public interface UserMapper {
	
	public List<UserBean> selectUserByName(String name);

}
