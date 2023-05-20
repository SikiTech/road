package com.beicheng.road.proxy;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 通用版cglib动态代理
 * @author beicheng
 */
public class SimpleCglibDynamicProxyDemoV2 {

    interface IServiceA {
        void sayHello();
    }

    static class AService implements IServiceA {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    interface IServiceB {
        void greet();
    }

    static class BService implements IServiceB {
        @Override
        public void greet() {
            System.out.println("hi");
        }
    }

    static class SimpleInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("entering " + method.getName());
            Object ret = methodProxy.invokeSuper(o, args);
            System.out.println("leaving " + method.getName());
            return ret;
        }
    }

    /**
     * 直接生成代理类
     * @param clz
     * @return
     */
    public static <T> T getProxyInstance(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallback(new SimpleInterceptor());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        IServiceA proxyInstance = getProxyInstance(AService.class);
        proxyInstance.sayHello();

        IServiceB proxyInstanceB = getProxyInstance(BService.class);
        proxyInstanceB.greet();
    }

}
