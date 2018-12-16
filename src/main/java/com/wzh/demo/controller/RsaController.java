package com.wzh.demo.controller;

import com.wzh.demo.domain.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <一句话功能描述>
 * <功能详细描述>
 * @author wzh
 * @version 2018-12-16 22:52
 * @see [相关类/方法] (可选)
 **/
@Controller
@RequestMapping("/rsa")
public class RsaController {

    @RequestMapping("/toRsaTest.do")
    public String showUserInfoByName(HttpServletRequest requset, HttpServletResponse response, Model mode)
    {


        return "/test/rsaTest";

    }
}
