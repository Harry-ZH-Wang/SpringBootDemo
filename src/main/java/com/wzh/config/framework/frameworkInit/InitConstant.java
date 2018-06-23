package com.wzh.config.framework.frameworkInit;

import com.wzh.config.framework.domain.FtpBean;
import com.wzh.config.framework.service.InitFrameWorkConstantService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzh
 * @create 2018-05-21 23:58
 * @desc ${系统加载时初始化常量}
 **/
@Component
public class InitConstant  {

    /**
     * ftp账号信息对象，静态，加载到内存中
     */
    private static Map<String,Object> ftpInfoMap;

    public static Map<String, Object> getFtpInfoMap() {
        return ftpInfoMap;
    }

    @Resource
    @Qualifier(value = "initFrameWorkConstantService")
    private InitFrameWorkConstantService initFrameWorkConstantService;

    /**
     * 初始化ftp账号信息对象
     */
    @PostConstruct
    public void initFtpInfo()
    {
        ftpInfoMap = new HashMap<String, Object>();

        List<FtpBean> ftpList = initFrameWorkConstantService.initFtpInfo();
        if(!ftpList.isEmpty())
        {
            for (FtpBean bean : ftpList)
            {
                //键值对方式存放，key为ftp的别名，方便取
                ftpInfoMap.put(bean.getFtpName(),bean);
            }
        }
    }



}
