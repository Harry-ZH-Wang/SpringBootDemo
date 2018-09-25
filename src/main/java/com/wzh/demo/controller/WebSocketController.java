package com.wzh.demo.controller;

import com.wzh.demo.websocket.handler.WebSocketHander;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * <websocket测试用MVC控制器>
 * <功能详细描述>
 * @author wzh
 * @version 2018-07-09 22:53
 * @see [相关类/方法] (可选)
 **/
@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    @Resource
    private WebSocketHander webSocketHander;

    @RequestMapping(value = "socket.do",method = RequestMethod.GET)
    public String toWebSocket(HttpSession session, Model model)
    {
        model.addAttribute("address","/javax/websocket");
        System.out.println("进入websocket");
        return "/test/webSocket";
    }

    // 跳转websocket界面
    @RequestMapping(value = "/spring/socket.do",method = RequestMethod.GET)
    public String toSpringWebSocket(HttpSession session, Model model)
    {
        model.addAttribute("address","/spring/websocket");
        System.out.println("进入websocket");
        return "/test/springWebSocket";
    }

    // 测试私信发送
    @RequestMapping(value = "/spring/socketById.do",method = RequestMethod.GET)
    public void toSpringWebSocketByid(HttpSession session, HttpServletRequest request, Model model)
    {
        String id = request.getParameter("id");
        webSocketHander.sendMessage(id,new TextMessage("测试指定人员发送"));

    }

    // 跳转stomp websocket 页面
    @RequestMapping(value = "/spring/stompSocket.do",method = RequestMethod.GET)
    public String toStompWebSocket(HttpSession session, HttpServletRequest request, Model model)
    {
        // 这里封装一个登录的用户组参数，模拟进入通讯后的简单初始化
        model.addAttribute("groupId","user_groupId");
        model.addAttribute("session_id",session.getId());
        System.out.println("跳转：" + session.getId());
        session.setAttribute("loginName",session.getId());
        return "/test/springWebSocketStomp";

    }
}
