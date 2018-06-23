package com.wzh.config.framework.dao.impl;

import com.wzh.config.framework.dao.InitFrameWorkConstantDao;
import com.wzh.config.framework.domain.FtpBean;
import com.wzh.config.framework.mapper.FrameworkInitMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzh
 * @create 2018-05-27 22:57
 * @desc ${初始化系统配置}
 **/
@Repository("initFrameWorkConstantDao")
public class InitFrameWorkConstantDaoImpl implements InitFrameWorkConstantDao {

    @Resource
    private FrameworkInitMapper frameworkInitMapper;

    @Override
    public List<FtpBean> initFtpInfo() {
        return frameworkInitMapper.initFtpInfo();
    }
}
