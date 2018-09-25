package com.wzh.config.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * <websocket获取HttpSession>
 * <功能详细描述>
 * @author wzh
 * @version 2018-07-10 01:02
 * @see [相关类/方法] (可选)
 **/
public class GetHttpSessionConfigurator extends Configurator{

    Logger log = Logger.getLogger(GetHttpSessionConfigurator.class);

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,HandshakeRequest request, HandshakeResponse response) {

        HttpSession httpSession=(HttpSession) request.getHttpSession();
        try {

            sec.getUserProperties().put(HttpSession.class.getName(),httpSession);

        }catch (Exception e)
        {
            log.error("获取httpsession失败"+ e.getMessage(),e);
        }


    }
}
