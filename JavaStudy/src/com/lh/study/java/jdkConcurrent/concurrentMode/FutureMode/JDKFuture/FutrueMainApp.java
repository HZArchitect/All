package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode.JDKFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutrueMainApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //构造FutrueTask
        FutureTask<String> futureTask = new FutureTask<String>(new RealData("Jdk"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);// 执行线程，调用call方法

        System.out.println("执行完毕");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        //如果call()没有执行完，会等待线程执行完毕
        System.out.println("数据为：" + futureTask.get());
        executorService.shutdown();
    }
}
