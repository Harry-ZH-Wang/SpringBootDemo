package com.wzh.config.framework.frameworkInit;

import com.wzh.config.framework.domain.FtpBean;
import com.wzh.config.framework.service.InitFrameWorkConstantService;
import com.wzh.config.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author wzh
 * @create 2018-05-21 23:58
 * @desc ${系统加载时初始化常量}
 **/
@Component
public class InitConstant  {

    private static Logger log = Logger.getLogger(InitConstant.class);

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
     * FTP初始化开关，0表示打开
      */
    private static final String FTP_SWITCH_OPEN = "0";

    /**
     * 初始化ftp账号信息对象
     */
    @PostConstruct
    public void initFtpInfo()
    {
        ftpInfoMap = new HashMap<String, Object>();

        Properties prop = PropertiesUtils.readProperties("config/properties","framework.properties");

        //如果取不到值就默认打开
        String ftpSwitch = prop.getProperty("config.ftp.switch");
        log.info("ftp开关为：" + ftpSwitch);

        if(FTP_SWITCH_OPEN.equals(ftpSwitch))
        {
            List<FtpBean> ftpList = initFrameWorkConstantService.initFtpInfo();
            if (!ftpList.isEmpty()) {
                for (FtpBean bean : ftpList) {
                    //键值对方式存放，key为ftp的别名，方便取
                    ftpInfoMap.put(bean.getFtpName(), bean);
                }
            }
        }
    }



}
