package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * Semaphore
 * 共享锁：运行多个线程同时共享临界区。若设置1，相当于上锁。
 * 让多线程操作资源分配更合理
 */
public class SemapDemo implements Runnable{
    Semaphore semap = new Semaphore(5);

    @Override
    public void run() {
        try {
            // 获得许可，相当于得到一把锁。若设置数量，则可获得多个许可
            semap.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + "： is Done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semap.release();
        }
    }

    public static void main(String[] args) {
        // 这个类可以创建线程池
        ExecutorService servie = Executors.newFixedThreadPool(20);
        SemapDemo semapDemo = new SemapDemo();
        for (int i = 0 ; i < 20 ; i++) {
            servie.submit(semapDemo);
        }
    }
}
