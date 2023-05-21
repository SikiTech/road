# 代理

## 静态代理
- 变体：适配器模式和装饰模式


## 动态代理
### jkd
- 机制：代理类继承Proxy，并实现跟代理一样的接口。在做增强时需要调用代理类的方法
- 必须实现相同的接口
- InvocationHandler、Proxy
- 所有代理类的父类都是Proxy
- 步骤：
    1. 初始化目标对象
    2. 编写调用处理器，并实现InvocationHandler接口
    3. 用构造注入目标对象，代理增强都在拦截器中处理
    4. 使用Proxy创建代理类，并转化为目标接口的引用
    5. 调用目标接口引用的方法实现代理
```java
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
```

### cglib
- 机制：代理类继承被代理类，重写并做字节码增强
- 生成的代理类的父类都是被代理类
- 直接生成代理类
- 步骤：
    1. 编写方法拦截器，并实现MethodInterceptor接口
    2. 代理增强都在拦截器中处理
    3. 使用enhancer创建代理类
    4. 直接代理类的方法
```java
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
```


### ByteBuddy



## 手写AOP框架
设计目标：
1、实现通过@SimpleInject注入属性
2、实现日志切面before、after和异常切面exception方法
3、实现MethodInterceptor接口，作具体的拦截任务
4、获取@Aspect代理类，并通过Enhance创建动态代理对象，调用
@ServiceLogAspect 切面，做类似MethodInterceptor的增强
AOP容器，使用被@Aspect 标识被代理类，通过动态代理

## 总结
- 技巧提升：通过使用方法分析内部使用

## 参考
https://sourcegraph.com/github.com/swiftma/program-logic/-/blob/src/shuo/laoma/dynamic/c86/CGLibContainer.java




比较：
静态代理：
- 硬编码进代理对象

JDK动态代理：
- Proxy创建代理、必须实现接口
- Java SDK代理面向的是一组接口，它为这些接口动态创建了一个实现类

从实现的角度看，Java SDK代理面向的是一组接口，它为这些接口动态创建了一个实现类。接口的具体实现逻辑是
通过自定义的InvocationHandler实现的，这个实现是自定义的，也就是说，其背后都不一定有真正被代理的对象，
也可能有多个实际对象，根据情况动态选择。cglib代理面向的是一个具体的类，它动态创建了一个新类，继承了该类，重写了其方法。

从代理的角度看，Java SDK代理的是对象，需要先有一个实际对象，自定义的InvocationHandler引用该对象，
然后创建一个代理类和代理对象，客户端访问的是代理对象，代理对象最后再调用实际对象的方法；cglib代理的是类，创建的对象只有一个。




使用动态代理，可以编写通用的代理逻辑，用于各种类型的被代理对象，而不需要为每个被代理的类型都创建一个静态代理类


Q：为什么 jdk 动态代理只能通过接口去实现
A：因为Java中不支持多继承，而JDK的动态代理在创建代理对象时，默认让代理对象继承了Proxy类，所以JDK只能通过接口去实现动态代理。
