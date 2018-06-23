package com.wzh.config.framework.dao;

import com.wzh.config.framework.domain.FtpBean;

import java.util.List;

/**
 * @author wzh
 * @create 2018-05-27 22:56
 * @desc ${初始化系统常量}
 **/
public interface InitFrameWorkConstantDao {

    /**
     * 初始化ftp系统配置账信息
     * @return
     */
    public List<FtpBean> initFtpInfo();
}
