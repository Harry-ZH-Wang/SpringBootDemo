package com.wzh.demo.service.impl;

import com.wzh.demo.conditionDemo.Computer;
import com.wzh.demo.service.ConditionService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("conditionService")
public class ConditionServiceImpl implements ConditionService {

    /**
     *  上下文对象
     */
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void computerPrice() {
        Computer com = applicationContext.getBean(Computer.class);
        System.out.println(com.price());
    }
}
