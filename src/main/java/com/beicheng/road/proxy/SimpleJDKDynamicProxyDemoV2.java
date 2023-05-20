package com.beicheng.road.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 通用版JDK动态代理
 * @author beicheng
 */
public class SimpleJDKDynamicProxyDemoV2 {

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
            System.out.println("entering " + method.getName());
            Object ret = method.invoke(realObject, args);
            System.out.println("leaving " + method.getName());
            return ret;
        }
    }

    /**
     * 获取代理的实例
     * @param clz 由于泛型在运行期间会作擦除，这里额外传如类型信息
     * @param realObject 目标类型
     * @param <T> 这里用泛型T来指代目标类型提升通用性
     * @return
     */
    public static <T> T getProxyInstance(Class<T> clz, T realObject) {
        InvocationHandler handler = new SimpleInvocationHandler(realObject);
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, handler);
    }

    public static void main(String[] args) {
        IServiceA aService = new AService();
        IServiceA proxyInstance = getProxyInstance(IServiceA.class, aService);
        proxyInstance.sayHello();

        IServiceB bService = new BService();
        IServiceB proxyInstanceB = getProxyInstance(IServiceB.class, bService);
        proxyInstanceB.greet();
    }

}
