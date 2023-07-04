package com.beicheng.road.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 擦除
 * @author robert
 */
public class GenericsErasureTypeDemo {

    public static void main(String[] args) {
        List<Object> objs = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();
        System.out.println(objs.getClass());
        System.out.println(ints.getClass());
        // Output:
        // class java.util.ArrayList
        // class java.util.ArrayList




    }
}
