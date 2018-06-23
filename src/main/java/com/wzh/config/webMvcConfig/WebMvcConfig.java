package com.wzh.config.webMvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置类，这里只配置静态资源
 * 这里使用的@Configuration注解，注意不要用MVC的@EnableWebMvc,
 * @EnableWebMvc注解会使sping boot默认关于webmvc的配置都会失效
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 重写路径配置方法，添加自定义的路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/webStatic/** 访问都映射到classpath:/webStatic/ 目录下
        //registry.addResourceHandler("/webStatic/**").addResourceLocations("classpath:/webStatic/");

        //将所有/img/**访问路径映射到img1,img2,img这个文件夹实际是不存在的，这里只是做个路径映射
        //registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img1/","classpath:/img2/");

        //将所有/album1/**,/album2/**访问路径映射到img3,album这个文件夹实际是不存在的，这里只是做个路径映射
        //registry.addResourceHandler("/album1/**","/album2/**").addResourceLocations("classpath:/img3/");

    }

}
