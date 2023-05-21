package com.beicheng.road.proxy.aop;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 异常切面
 */
@Aspect(targetClass = {BService.class})
public class ExceptionAspect {

    public static void exception(Object object, Method method, Object[] args, Throwable e) {
        System.err.println("exception when calling: " + method.getName()
                + "," + e);
    }
}
