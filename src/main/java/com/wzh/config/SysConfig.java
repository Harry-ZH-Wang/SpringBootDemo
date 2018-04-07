package com.wzh.config;

import com.wzh.config.condition.SysMacCondition;
import com.wzh.config.condition.SysWinCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConfig {

    @Bean
    @Conditional(SysWinCondition.class)
    public Computer sysWin()
    {
        return new SysWin();
    }


    @Bean
    @Conditional(SysMacCondition.class)
    public Computer sysMac()
    {
        return new SysMac();
    }



}
