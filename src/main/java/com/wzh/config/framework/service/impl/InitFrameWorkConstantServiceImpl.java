package com.wzh.config.framework.service.impl;

import com.wzh.config.framework.dao.InitFrameWorkConstantDao;
import com.wzh.config.framework.domain.FtpBean;
import com.wzh.config.framework.service.InitFrameWorkConstantService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzh
 * @create 2018-05-27 23:08
 * @desc ${初始化系统配置}
 **/
@Service("initFrameWorkConstantService")
public class InitFrameWorkConstantServiceImpl implements InitFrameWorkConstantService{

    @Resource
    @Qualifier(value = "initFrameWorkConstantDao")
    private InitFrameWorkConstantDao initFrameWorkConstantDao;

    @Override
    public List<FtpBean> initFtpInfo() {
        return initFrameWorkConstantDao.initFtpInfo();
    }
}
