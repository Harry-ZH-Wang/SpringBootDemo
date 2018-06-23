package com.wzh.config.framework.mapper;

import com.wzh.config.framework.domain.FtpBean;

import java.util.List;

/**
 * @author wzh
 * @create 2018-05-27 20:59
 * @desc ${初始化系统配置}
 **/
public interface FrameworkInitMapper {

    /**
     * 初始化ftp系统配置账信息
     * @return
     */
    public List<FtpBean> initFtpInfo();
}
