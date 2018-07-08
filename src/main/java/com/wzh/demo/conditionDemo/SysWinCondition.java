package com.wzh.demo.conditionDemo;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 条件条件初始化bean测试
 */
public class SysWinCondition implements Condition{


    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata)
    {
        //获取上下文环境对象,可以对某些配置做操作
        Environment env = conditionContext.getEnvironment();

        Contants.system = env.getProperty("os.name");
        //return true;
        System.out.println(env.getProperty("os.name"));
        return env.getProperty("os.name").contains("window");
    }
}
