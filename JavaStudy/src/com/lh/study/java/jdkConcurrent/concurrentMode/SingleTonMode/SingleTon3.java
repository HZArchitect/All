package com.lh.study.java.jdkConcurrent.concurrentMode.SingleTonMode;

/**
 * 此方式为终极版，使用了延迟加载，同时不会因为其他无关字段导致提前创建实例
 */
public class SingleTon3 {

    //构造器必须为private，这样外部就不可以new这个对象
    private SingleTon3() {
        //看这个类何时被初始化
        System.out.println("SingleTon3 is create。");
    }

    //只有当访问了这个静态内部类的时候实例才会初始化，而只有getInstance会访问。同时类加载是线程安全的操作
    private static class SingletonHolder {
        private static SingleTon3 instance = new SingleTon3();

    }

    public static SingleTon3 getInstance() {
        return SingletonHolder.instance;
    }

}
