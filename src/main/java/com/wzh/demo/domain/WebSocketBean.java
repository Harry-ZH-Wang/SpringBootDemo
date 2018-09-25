package com.wzh.demo.domain;

import javax.websocket.Session;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <websocket信息对象>
 * <用于存储secket连接信息>
 * @author wzh
 * @version 2018-07-08 18:49
 * @see [相关类/方法] (可选)
 **/
public class WebSocketBean {

    /**
     * 连接session对象
     */
    private Session session;

    /**
     * 连接错误次数
     */
    private AtomicInteger erroerLinkCount = new AtomicInteger(0);

    public int getErroerLinkCount() {
        // 线程安全,以原子方式将当前值加1，注意：这里返回的是自增前的值
        return erroerLinkCount.getAndIncrement();
    }

    public void cleanErrorNum()
    {
        // 清空计数
        erroerLinkCount = new AtomicInteger(0);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "WebSocketBean{" +
                "session=" + session +
                ", erroerLinkCount=" + erroerLinkCount +
                '}';
    }
}
