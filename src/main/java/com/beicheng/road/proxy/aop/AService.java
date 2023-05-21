package com.beicheng.road.proxy.aop;

public class AService {

    @SimpleInject
    private BService bService;

    public void callB() {
        this.getbService().doWork();
    }

    public BService getbService() {
        return bService;
    }

    public void setbService(BService bService) {
        this.bService = bService;
    }
}
