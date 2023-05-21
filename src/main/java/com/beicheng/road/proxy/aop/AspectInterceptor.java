package com.beicheng.road.proxy.aop;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 切面拦截器
 * @author beicehng
 */
public class AspectInterceptor implements MethodInterceptor {

    private CglibAopContainer cglibAopContainer;

    public AspectInterceptor(CglibAopContainer cglibAopContainer) {
        this.cglibAopContainer = cglibAopContainer;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        Map<Class<?>, Map<CglibAopContainer.InterceptPoint, List<Method>>> methodRegistry = cglibAopContainer.getInterceptMethodRegistry();
        Map<CglibAopContainer.InterceptPoint, List<Method>> pointListMap = methodRegistry.get(o.getClass().getSuperclass());
        if (pointListMap == null) {
            return methodProxy.invokeSuper(o, args);
        }

        try {
            // before
            List<Method> before = pointListMap.get(CglibAopContainer.InterceptPoint.BEFORE);
            for (Method mt : before) {
                mt.invoke(null, o, method, args);
            }
            // execute
            result = methodProxy.invokeSuper(o, args);
            // after
            List<Method> after = pointListMap.get(CglibAopContainer.InterceptPoint.AFTER);
            for (Method mt : after) {
                mt.invoke(null, o, method, args, result);
            }
        } catch (Throwable e) {
            // exception
            List<Method> after = pointListMap.get(CglibAopContainer.InterceptPoint.EXCEPTION);
            for (Method mt : after) {
                mt.invoke(null, o, method, args, e);
            }
        }

        System.out.println(result);
        return result;
    }
}
