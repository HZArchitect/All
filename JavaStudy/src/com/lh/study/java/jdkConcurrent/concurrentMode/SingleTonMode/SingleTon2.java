package com.lh.study.java.jdkConcurrent.concurrentMode.SingleTonMode;

/**
 * 2为1的改进版使用了延迟加载的方式
 * 只有首次调用getInstance()方法的时候才会初始化实例
 * 不会因为有其他字段导致类被提前初始化
 */
public class SingleTon2 {

    //构造器必须为private，这样外部就不可以new这个对象
    private SingleTon2() {
        //看这个类何时被初始化
        System.out.println("SingleTon2 is create。");
    }

    //和1不同，这个地方不是直接new而是null
    private static SingleTon2 instance = null;

    //多线程同时第一次访问该方法有可能创建出多个实例，所以需要上锁，但高并发情况下synchronized性能低
    public static synchronized SingleTon2 getInstance() {
        //确保只实例化一次，但多线程可能出错
        if (instance == null) {
            instance = new SingleTon2();
        }
        return instance;
    }
}

