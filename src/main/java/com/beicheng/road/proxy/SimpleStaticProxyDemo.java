package com.beicheng.road.proxy;

/**
 * 静态代理
 * @author beicheng
 */
public class SimpleStaticProxyDemo {

    interface IService {
        void sayHello();
    }

    static class RealService implements IService {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class TraceProxyService implements IService {
        private IService targetService;

        public TraceProxyService(IService targetService) {
            this.targetService = targetService;
        }

        @Override
        public void sayHello() {
            System.out.println("entering sayHello");
            targetService.sayHello();
            System.out.println("leaving sayHello");
        }
    }

    public static void main(String[] args) {
        RealService realService = new RealService();
        TraceProxyService proxyService = new TraceProxyService(realService);
        proxyService.sayHello();
    }
}
