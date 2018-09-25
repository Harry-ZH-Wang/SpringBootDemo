package com.wzh.demo.service.impl;

import com.wzh.demo.service.SystemOutService;
import org.springframework.stereotype.Service;

/**
 * <简单的输出测试，没有功能>
 * <功能详细描述>
 * @author wzh
 * @version 2018-09-23 23:11
 * @see [相关类/方法] (可选)
 **/
@Service("systemOutService")
public class SystemOutServiceImp implements SystemOutService{

    @Override
    public void sysout() {
        System.out.println("调用systemOutService 成功");
    }
}
