# 代理

## 静态代理
- 变体：适配器模式和装饰模式


## 动态代理
### jkd
- 必须实现相同的接口
- InvocationHandler、Proxy
- 所有代理类的父类都是Proxy
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
- 生成的代理类的父类都是被代理类
- 直接生成代理类
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




比较：
静态代理：硬编码进代理对象
JDK动态代理：Proxy创建代理、必须实现接口


使用动态代理，可以编写通用的代理逻辑，用于各种类型的被代理对象，而不需要为每个被代理的类型都创建一个静态代理类


Q：为什么 jdk 动态代理只能通过接口去实现
A：因为Java中不支持多继承，而JDK的动态代理在创建代理对象时，默认让代理对象继承了Proxy类，所以JDK只能通过接口去实现动态代理。
