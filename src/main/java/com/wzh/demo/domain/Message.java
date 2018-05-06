package com.wzh.demo.domain;

/**
 * @author wzh
 * @create 2018-05-01 23:06
 * @desc 消息对象，AJAX使用
 **/
public class Message {
    /**
     * 处理成功 true, 失败，false
     */
    private boolean status;

    /**
     * 返回的消息内容
     */
    private String information;
    public Message() {
        super();
    }
    public Message(boolean status, String information) {
        super();
        this.status = status;
        this.information = information;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", information='" + information + '\'' +
                '}';
    }
}
