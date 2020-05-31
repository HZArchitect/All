package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park()   使得许可不可用
 * unpark()        使得许可可用
 * 这是一个比较底层的操作。使用的unsafe.park  系统级的东西
 *
 * 可以让当前线程进入一个挂起的状态和解除挂起状态。
 * 这个挂起原理类似于信号量获得许可来执行的操作。
 *
 * park() 能够响应中断，但不抛出异常
 * 中断响应的的结果是，park()函数的返回，可以从Thread.interrupted()得到中断标志
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread (String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in" + this.getName());
                //等待许可，阻塞。且当前线程持有锁u，其他线程无法获取
                LockSupport.park();
                System.out.println(this.getName() + "线程已经获取到了许可执行完毕。");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();

        // 先unpark或者先park，线程都是可以正常执行的
        LockSupport.unpark(t1);
        Thread.sleep(2000);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

}
