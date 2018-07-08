package com.wzh.demo.domain;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * <注解读取配置文件测试类>
 * <功能详细描述>
 * @author wzh
 * @version 2018-06-30 18:55
 * @see [相关类/方法] (可选)
 **/
@Component
@PropertySource(value = "classpath:static/file/test.properties",encoding = "utf-8")
@ConfigurationProperties(prefix="test")
public class PropertiesTestBean1 {

    //在application.properties中的文件，直接使用@Value读取即可，applicarion的读取优先级最高

    private String name;

    private String sex;

    private Long age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PropertiesTestBean{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
