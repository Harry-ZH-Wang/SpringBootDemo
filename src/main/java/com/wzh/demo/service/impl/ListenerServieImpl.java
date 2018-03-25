package com.wzh.demo.service.impl;

import com.wzh.demo.event.MyEvent;
import com.wzh.demo.service.ListenerTestService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service("listenerService")
public class ListenerServieImpl implements ListenerTestService{

    /**
     *  上下文对象
     */
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void publish(String msg) {

        //通过上下文对象发布监听
        applicationContext.publishEvent(new MyEvent(this,msg));
        System.out.println(msg);

    }

    public void test()
    {
        System.out.println("ces");
    }
}
