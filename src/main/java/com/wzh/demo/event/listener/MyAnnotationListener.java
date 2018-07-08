package com.wzh.demo.event.listener;

import com.wzh.demo.event.MyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 测试用注解监听器
 */
@Component
public class MyAnnotationListener {

    @EventListener
    public void listener1(MyEvent event)
    {
        System.out.println("注解事件监听-2："+ event.getMsg());
    }

    @EventListener
    @Async
    public void listener2(MyEvent event)
    {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("注解事件监听-1："+ event.getMsg());
    }
}
