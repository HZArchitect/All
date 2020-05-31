package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * 1、可重入
 * 2、可中断
 * 3、可限时
 * 4、可公平锁---实例加参数true，不加默认false不公平
 *
 *
 * 本段代码演示可重入
 */
public class ReenterLock implements Runnable {

    private ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0 ; j < 10000000 ; j++) {
            // 锁一次代表这个线程拿到一个许可，后面需要释放这个许可。锁几次就是几个许可，需要释放几次否则阻塞
            lock.lock();
            try {
                i++;
            } finally {
                // 写在finally里面万无一失，代码是否报错都会运行释放锁
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl = new ReenterLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();t2.start();
        // 当先主线程阻塞，等待t1运行结束再运行
        t1.join();t2.join();
        System.out.println(i);
    }
}
