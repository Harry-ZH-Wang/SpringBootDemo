package com.wzh.demo.hessian.hessianService.service.Impl;

import com.wzh.demo.hessian.hessianService.service.SayHelloHessian;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <一句话功能描述>
 * <功能详细描述>
 * @author wzh
 * @version 2018-11-18 17:46
 * @see [相关类/方法] (可选)
 **/
@Service("sayHelloHessian")
public class SayHelloHessianImpl implements SayHelloHessian{

    private Logger log = Logger.getLogger(SayHelloHessianImpl.class);

    @Override
    public String SayHello(String msg) {

        log.info("-----------进入hessian方法,客户端参数："+ msg +"--------------");

        return "服务端返回：hello hessian";
    }
}
