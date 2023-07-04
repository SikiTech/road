package com.beicheng.road.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robert
 */
public class GenericsTypeBoundDemo {

    /**
     * 泛型类型无法向上转型。
     * Integer 继承了 Object；ArrayList 继承了 List；但是 List<Interger> 却并非继承了 List<Object>。
     * 这是因为，泛型类并没有自己独有的 Class 类对象。比如：并不存在 List<Object>.class 或是 List<Interger>.class，Java 编译器会将二者都视为 List.class。
     */
    private void upLimit() {
        List<Integer> list = new ArrayList<>();
        //List<Object> list2 = list; // Erorr
    }

    /**
     * 用泛型更通用的代码
     */
    private static <T extends Comparable<T>> T max(T... elements) {
        T max = elements[0];
        for (T element : elements) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(max(1.2, 4.4, 7.8));
        System.out.println(max("Jim", "Rose", "Musk"));
    }
}
