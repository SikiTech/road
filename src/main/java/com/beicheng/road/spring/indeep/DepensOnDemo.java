package com.beicheng.road.spring.indeep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Slf4j
@Configuration
public class DepensOnDemo {

    @Bean("bBean")
    @DependsOn("aBean")
    public Object bBean() {
        log.info("bBean init....");
        return new Object();
    }

    @Bean("aBean")
    public Object aBean() {
        log.info("aBean init....");
        return new Object();
    }
}
