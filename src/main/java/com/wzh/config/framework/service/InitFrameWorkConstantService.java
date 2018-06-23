package com.wzh.config.framework.service;

import com.wzh.config.framework.domain.FtpBean;

import java.util.List;

/**
 * @author wzh
 * @create 2018-05-27 23:06
 * @desc ${初始化系统配置}
 **/
public interface InitFrameWorkConstantService {

    /**
     * 初始化ftp系统配置账信息
     * @return
     */
    public List<FtpBean> initFtpInfo();
}
