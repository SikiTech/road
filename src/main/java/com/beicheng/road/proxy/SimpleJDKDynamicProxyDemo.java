package com.beicheng.road.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author beicheng
 */
public class SimpleJDKDynamicProxyDemo {

    interface IService {
        void sayHello();
    }

    static class RealService implements IService {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class SimpleInvocationHandler implements InvocationHandler {
        private Object realObject;

        public SimpleInvocationHandler(Object realObject) {
            this.realObject = realObject;
        }

        /**
         * 调用处理器件
         * @param proxy 当前代理对象
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("entering sayHello");
            Object ret = method.invoke(realObject, args);
            System.out.println("leaving sayHello");
            return ret;
        }
    }

    public static void main(String[] args) {
        RealService realService = new RealService();
        InvocationHandler handler = new SimpleInvocationHandler(realService);
        IService proxyInstance = (IService) Proxy.newProxyInstance(realService.getClass().getClassLoader(), new Class[]{IService.class}, handler);
        proxyInstance.sayHello();
    }
}
