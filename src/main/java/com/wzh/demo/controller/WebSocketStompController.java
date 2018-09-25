package com.wzh.demo.controller;

import com.alibaba.fastjson.JSON;
import com.wzh.demo.domain.WebSocketUserAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <STOMP websocket controller>
 * <功能详细描述>
 * @author wzh
 * @version 2018-08-29 22:58
 * @see [相关类/方法] (可选)
 **/
@Controller
public class WebSocketStompController {

    Logger log = Logger.getLogger(WebSocketStompController.class);

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 实例化Controller的时候，注入SimpMessagingTemplate
     * @param messagingTemplate
     */
    @Autowired
    public WebSocketStompController(SimpMessagingTemplate messagingTemplate)
    {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 发送广播消息，所有订阅了此路径的用户都会收到此消息
     * 这里做个restful风格，其实无所谓，根据项目实际情况进行配置
     * restful风格的接口，在springMVC中，我们使用@PathVariable注解，
     * 在websocket stomp接口中，restful要使用@DestinationVariable
     * @param groupId
     * @param json
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/sendChatMsg/{groupId}")
    @SendTo("/topicTest/hello")
    public Map<String, Object> sendChatMsg(@DestinationVariable(value = "groupId") String groupId, String json,
        StompHeaderAccessor headerAccessor)
    {
        // 这里拿到的user对象是在WebSocketChannelInterceptor拦截器中绑定上的对象
        WebSocketUserAuthentication user =(WebSocketUserAuthentication)headerAccessor.getUser();
        log.info("公告controller 中获取用户登录令牌：" + user.getName());
        log.info("公告拿到客户端传递分组参数:" + groupId);
        
        // 这里拿到的json 字符串，其实可以自动绑定到对象上
        System.out.println("公告获取客户端传递过来的JSON 字符串：" + json);
        Map msg = (Map) JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("msg", "公告服务器收到客户端请求，发送广播消息:"+ msg.get("msg"));
        return data;
        
    }

    /**
     * 发送私信消息，只是想简单的用websocket向服务器请求资源而已，
     * 然后服务器你就把资源给我就行了，别的用户就不用你广播推送了，简单点，就是我请求，你就推送给我
     * 如果一个帐号打开了多个浏览器窗口，也就是打开了多个websocket session通道，
     * 这时，spring webscoket默认会把消息推送到同一个帐号不同的session，
     * 可以利用broadcast = false把避免推送到所有的session中
     * @param json
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/sendChatMsgByOwn")
    @SendToUser(value = "/userTest/own")
    public Map<String, Object> sendChatMsgByOwn(String json,
        StompHeaderAccessor headerAccessor)
    {
        // 这里拿到的user对象是在WebSocketChannelInterceptor拦截器中绑定上的对象
        WebSocketUserAuthentication user = (WebSocketUserAuthentication)headerAccessor.getUser();

        log.info("SendToUser controller 中获取用户登录令牌：" + user.getName()
                + " socketId:" + headerAccessor.getSessionId());

        // 这里拿到的json 字符串，其实可以自动绑定到对象上
        System.out.println("SendToUser获取客户端传递过来的JSON 字符串：" + json);

        Map msg = (Map)JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("msg", "SendToUser服务器收到客户端请求，发送私信消息:" + msg.get("msg"));
        
        return data;
    }

    /**
     * 根据ID 把消息推送给指定用户
     * 1. 这里用了 @SendToUser 和 返回值 其意义是可以在发送成功后回执给发送放其信息发送成功
     * 2. 非必须，如果实际业务不需要关心此，可以不用@SendToUser注解，方法返回值为void
     * 3. 这里接收人的参数是用restful风格带过来了，websocket把参数带到后台的方式很多，除了url路径，
     *    还可以在header中封装用@Header或者@Headers去取等多种方式
     * @param accountId 消息接收人ID
     * @param json 消息JSON字符串
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/sendChatMsgById/{accountId}")
    @SendToUser(value = "/userTest/callBack")
    public Map<String, Object> sendChatMsgById(
        @DestinationVariable(value = "accountId") String accountId, String json,
        StompHeaderAccessor headerAccessor)
    {
        Map msg = (Map)JSON.parse(json);
        Map<String, Object> data = new HashMap<String, Object>();

        // 这里拿到的user对象是在WebSocketChannelInterceptor拦截器中绑定上的对象
        WebSocketUserAuthentication user = (WebSocketUserAuthentication)headerAccessor.getUser();

        log.info("SendToUser controller 中获取用户登录令牌：" + user.getName()
                + " socketId:" + headerAccessor.getSessionId());

        // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
        // 如果服务端要将消息发送给特定的某一个用户，
        // 可以使用SimpleMessageTemplate的convertAndSendToUser方法(第一个参数是用户的登陆名username)
        String address = "/userTest/callBack";
        messagingTemplate.convertAndSendToUser(accountId, address, msg.get("msg"));

        data.put("msg", "callBack 消息已推送，消息内容：" + msg.get("msg"));
        return data;
    }

}
