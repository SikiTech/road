package com.beicheng.road.proxy.aop;

import org.springframework.cglib.proxy.Enhancer;

import javax.annotation.PostConstruct;
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
public class CglibAopContainer {

    enum InterceptPoint {
        BEFORE, AFTER, EXCEPTION
    }

    /**
     * 切面
     */
    private Class<?>[] aspects = new Class<?>[]{LogAspect.class, ExceptionAspect.class};
    /**
     * 切点 key：类 value：<切点，切面方法集>
     */
    public Map<Class<?>, Map<InterceptPoint, List<Method>>> interceptMethodRegistry = new HashMap<>();

    /**
     * 初始化
     */
    {
        init();
    }

    private void init() {
        for (Class<?> aspect : aspects) {
            Aspect annotation = aspect.getAnnotation(Aspect.class);
            if (annotation == null) {
                continue;
            }
            // 仅以方法名匹配，可优化成类型匹配
            Method[] interceptMethods = aspect.getDeclaredMethods();
            Class<?>[] targetClass = annotation.targetClass();
            for (Class<?> cls : targetClass) {
                for (Method method : interceptMethods) {
                    registry(cls, method);
                }
            }
        }
    }

    private void registry(Class<?> cls, Method method) {
        for (InterceptPoint em :InterceptPoint.values()){
            // 名称匹配
            if (em.name().equalsIgnoreCase(method.getName())) {
                Map<InterceptPoint, List<Method>> pointListMap = interceptMethodRegistry.get(cls);
                if (pointListMap == null) {
                    pointListMap = new HashMap<>(6);
                }

                List<Method> methods = pointListMap.get(em);
                if (methods == null) {
                    methods = new ArrayList<>();
                }
                methods.add(method);
                pointListMap.put(em, methods);
                interceptMethodRegistry.put(cls, pointListMap);
            }
        }
    }

    public Map<Class<?>, Map<InterceptPoint, List<Method>>> getInterceptMethodRegistry() {
        return interceptMethodRegistry;
    }

    /**
     * 创建对象
     * @param cls 目标类型
     * @return
     */
    private <T> T createInstance(Class<T> cls) throws IllegalAccessException, InstantiationException {
        if (!interceptMethodRegistry.containsKey(cls)) {
            return cls.newInstance();
        }

        // 通过动态代理创建对象

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new AspectInterceptor(this));
        return (T)enhancer.create();
    }

    /**
     * 获取对象
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> cls) {
        try {
            T instance = this.createInstance(cls);
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                boolean present = field.isAnnotationPresent(SimpleInject.class);
                if (!present) {
                    continue;
                }
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Class<?> fieldType = field.getType();
                Object attr = this.createInstance(fieldType);
                field.set(instance, attr);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
