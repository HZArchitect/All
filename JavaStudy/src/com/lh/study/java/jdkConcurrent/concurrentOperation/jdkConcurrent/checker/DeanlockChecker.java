package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent.checker;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeanlockChecker {

    public static void check() {
        Thread t = new Thread(new DeadlockCheck());
        t.setDaemon(true);
        t.start();
    }
}

class DeadlockCheck implements Runnable {
    public final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();

    @Override
    public void run() {
        while (true) {
            // 找出所有发生死锁的线程
            long [] deadlockedThreadIds = mbean.findDeadlockedThreads();
            // 不为null则代表存在死锁线程
            if (deadlockedThreadIds != null) {
                // 根据id找出线程信息
                ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
                //返回所有活动线程的堆栈跟踪映射。映射键是线程，
                // 每个映射值是一个表示堆栈转储的StackTraceElement对应线程的数组。
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    for (int i = 0 ; i < threadInfos.length ; i++) {
                        if (t.getId() == threadInfos[i].getThreadId()) {
                            t.interrupt();
                        }
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}

        }
    }
}
