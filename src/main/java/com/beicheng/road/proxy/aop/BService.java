package com.beicheng.road.proxy.aop;

public class BService {
    public String doWork() {
        System.out.println("bb doWork");
        return "working";
    }

    public void error() {
        throw new RuntimeException();
    }
}
