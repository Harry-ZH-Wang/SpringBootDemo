package com.wzh.demo.controller;

import com.wzh.config.utils.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * <测试jar包打包方式配置文件读取>
 * <功能详细描述>
 *
 * @author wzh
 * @version 2018-06-29 22:42
 * @see [相关类/方法] (可选)
 **/
@Controller
@RequestMapping("/properties")
public class PropertiesController {

    @RequestMapping("/show.do")
    public String showInfo(Model model)
    {

        try {
            Properties p1 =  PropertiesUtils.readProperties("static/file","test.properties");
            model.addAttribute("name",p1.getProperty("test.name"));

            Properties p2 = PropertiesUtils.readProperties("static/file");
            model.addAttribute("age",p2.getProperty("test.age"));
            model.addAttribute("sex",p2.getProperty("test.sex"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return "test/properties";
    }
}
