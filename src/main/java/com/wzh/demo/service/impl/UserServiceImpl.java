package com.wzh.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.wzh.demo.dao.UserDao;
import com.wzh.demo.domain.UserBean;
import com.wzh.demo.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService
{

	@Autowired
	@Qualifier(value="userDao")
	private UserDao userDao;

	@Override
	public List<UserBean> selectUserByName(String name) {
		// TODO Auto-generated method stub
		return userDao.selectUserByName(name);
	}

	@Override
	@Transactional
	public int addUser(UserBean user) {
		//这里做注解事务的测试，调用两次add方法，然后报错一次，看事务是否成功
		int  a1 = userDao.addUser(user);
		Integer.valueOf("ABC");
		int a2 = userDao.addUser(user);
		return (a1 + a2);
	}


}
