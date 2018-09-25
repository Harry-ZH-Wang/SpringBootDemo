package com.wzh.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wzh.config.utils.BeanHeader;
import com.wzh.demo.service.SystemOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzh.demo.domain.UserBean;
import com.wzh.demo.service.IUserService;

@Controller
@RequestMapping("/user")
public class userTestController {

	@Autowired
	@Qualifier(value="userService")
	private IUserService userServiceImpl;
	
	@RequestMapping("/userInfo.do")
	public String showUserInfoByName(HttpServletRequest requset,HttpServletResponse response,Model mode)
	{
		//这里名字写死，然后只取一个，方便测试
		UserBean user = userServiceImpl.selectUserByName("张三").get(0);
		mode.addAttribute("user",user);

		return "/test/userInfo";
		
	}

	@RequestMapping("/img.do")
	public String showImg(HttpServletRequest request,Model model)
	{
		request.getSession();
		model.addAttribute("imgName","test.png");
		return "/test/img";
	}

	@RequestMapping("/sysout.do")
	public String sysout(HttpServletRequest request,Model model)
	{
		SystemOutService service = BeanHeader.getBean("systemOutService");
		service.sysout();
		return "";
	}
}
