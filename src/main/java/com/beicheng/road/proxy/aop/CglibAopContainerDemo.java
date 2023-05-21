package com.beicheng.road.proxy.aop;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现一个自定义的AOP容器
 * @author beicheng
 */
public class CglibAopContainerDemo {

    public static void main(String[] args) {
        CglibAopContainer aopContainer = new CglibAopContainer();
        AService aService = aopContainer.getInstance(AService.class);
        aService.getbService().doWork();
    }

}
