package com.wzh.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.wzh.demo.dao.UserDao;
import com.wzh.demo.domain.UserBean;
import com.wzh.demo.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService
{

	/**
	 * 编程事务对象
	 */
	private final TransactionTemplate transactionTemplate;

	/**
	 * 构造方法初始化事务模板
	 */
	public UserServiceImpl(@Qualifier("transactionManager") PlatformTransactionManager transactionManager)
	{
		this.transactionTemplate = new TransactionTemplate(transactionManager);

		//设置事务等级
		this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

		//设置连接超时时间 30seconds
		this.transactionTemplate.setTimeout(30);
	}

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

	@Override
	public int addUserByProgramming(final UserBean user) {

		//开启编程事务
        Integer i  = (Integer) transactionTemplate.execute(new TransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    int a1 = userDao.addUser(user);
                    Integer.valueOf("ABC");
                    int a2 = userDao.addUser(user);
                    return (a1 + a2);
                } catch (Exception e) {
                    //异常回滚
                    status.setRollbackOnly();
                    e.printStackTrace();

                }
                return 0;
            }
        });

		return i;
	}


}
