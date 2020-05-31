package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒数检查器
 * 主线程可以等待到所有的线程全部完工再执行。
 */
public class CountDownLatchDemo implements Runnable {
    // 设置检查器10个
    private static CountDownLatch end = new CountDownLatch(10);
    @Override
    public void run() {
        try {
            // 模拟线程检查任务
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(Thread.currentThread().getId() + ":check complete");
            // 设置当前线程完毕
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0 ; i < 10 ; i++) {
            service.submit(demo);
        }
        //等待检查，当设置的检查器全部countDown主线程才可以继续执行下去
        end.await();
        //发射火箭
        System.out.println("Fire!");
        service.shutdown();
    }
}
