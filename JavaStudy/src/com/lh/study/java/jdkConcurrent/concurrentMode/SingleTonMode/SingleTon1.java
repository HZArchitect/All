package com.lh.study.java.jdkConcurrent.concurrentMode.SingleTonMode;

/**
 * 缺点：这个类只有在第一次访问的时候会初始化。（多个线程同时首次访问也是安全的，因为类加载操作是线程安全的）
 * 所以何时产出实例不好控制,如何理解呢？
 * 正常情况getInstance()第一次访问的时候这个类会初始化，但是实际上是虚拟机在第一次访问这个类的时候初始化。
 */
public class SingleTon1 {

    //若这个类中还有其他字段，那么访问这个字段的时候这个类会初始化
    public static int STATUS = 1;


    //构造器必须为private，这样外部就不可以new这个对象
    private SingleTon1() {
        //看这个类何时被初始化
        System.out.println("SingleTon1 is create。");
    }

    //自己new自己，外部不可new。同时为private static对外不可见，全局只有一个实例
    private static SingleTon1 instance = new SingleTon1();

    //通过静态public方法获取实例对象
    public static SingleTon1 getInstance() {
        return instance;
    }

    //若当前系统不需要初始化这个类，那么以下操作则会将这个单例类初始化，若系统在意这一点就不建议使用此方式
    public static void main(String[] args) {
        System.out.println(SingleTon1.STATUS);
    }
}
