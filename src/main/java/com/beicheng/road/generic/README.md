

为什么要采用泛型


泛型的好处：
1、写出更通用程序
2、类型更安全，编译器提前预知问题
3、可读性更强




> 泛型类型无法向上转型。Integer 继承了 Object；ArrayList 继承了 List；但是 List<Interger> 
> 却并非继承了 List<Object>。这是因为，泛型类并没有自己独有的 Class 类对象。比如：并不存在 List<Object>.class 
> 或是 List<Interger>.class，Java 编译器会将二者都视为 List.class。
  


参考：
https://dunwu.github.io/javacore/pages/33a820/#%E4%B8%BA%E4%BB%80%E4%B9%88%E9%9C%80%E8%A6%81%E6%B3%9B%E5%9E%8B


Q：为什么采用伪泛型？
A：故 A 项目要适配 B2 lib，必要要把 Java 升级到 Java 5。
那现在我们再回过头来想想，Java 版本迭代都从 1.0 到 5.0了，有多少的开源框架，有多少项目，如果为了引入泛型，强行让开发者修改代码。这种情况，各位同学。自行脑补。估计数以万计的开发者拿着刀，在堵 Java 语言架构师的门吧。


