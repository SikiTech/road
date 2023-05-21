package com.beicheng.road.proxy.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 调用日志切面
 */
@Aspect(targetClass = {AService.class, BService.class})
public class LogAspect {

    public static void before(Object object, Method method, Object[] args) {
        System.out.println("entering " + method.getDeclaringClass().getSimpleName()
                + "::" + method.getName() + ", args: " + Arrays.toString(args));
    }

    public static void after(Object object, Method method, Object[] args, Object result) {
        System.out.println("leaving " + method.getDeclaringClass().getSimpleName()
                + "::" + method.getName() + ", result: " + result + "\n");
    }


}
