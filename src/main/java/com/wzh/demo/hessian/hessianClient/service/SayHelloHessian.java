package com.wzh.demo.hessian.hessianClient.service;

/**
 * <客户端只需要接口对象>
 * <客户端只需要接口对象，在HessianProxyFactoryUtils工具类中会为这个对象创建代理对象>
 * @author wzh
 * @version 2018-11-18 17:24
 * @see [相关类/方法] (可选)
 **/
public interface SayHelloHessian {

    /**
     *  接口测试方法
     * @return
     */
    public String SayHello(String msg);

}
