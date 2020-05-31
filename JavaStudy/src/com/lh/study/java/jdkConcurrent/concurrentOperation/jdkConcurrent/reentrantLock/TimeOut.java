package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock原理
 * 1、CAS  核心：通过cas拿锁。判断这个锁是否能够改变状态，可以则获取，不行则再次重试
 * 2、等待队列   等待队列来维护要获取锁的线程
 * 3、park()    利用LockSupport让线程挂起，可以运行则从等待队列中让这个线程释放unpark()
 *
 *
 * 1、可重入 2、可中断 3、可限时 4、公平锁
 * 本段代码演示可限时
 *
 *
 *
 * 4、公平锁，ReentranLock构造器可以有一个boolean参数.true则为公平锁
 * 公平锁可保证线程先到先得。正常情况下线程竞争锁是不公平的，可能导致饥饿。公平锁不会。
 * 但是公平锁性能更差，因为要维护先到先得。没有特殊需求不用这个功能。
 */
public class TimeOut implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            } else {
                System.out.println("get lock failed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 若不判断会抛出异常，当线程没有持有当前锁的时候unlock会报错
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        TimeOut timeOut = new TimeOut();
        Thread t1 = new Thread(timeOut);
        Thread t2 = new Thread(timeOut);
        t1.start();t2.start();

    }
}
