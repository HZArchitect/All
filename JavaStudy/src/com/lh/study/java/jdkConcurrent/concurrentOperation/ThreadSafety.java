package com.lh.study.java.jdkConcurrent.concurrentOperation;

/**
 * 并发编程的三个概念
 * 1、原子性
 * 2、可见性
 * 3、有序性
 */
public class ThreadSafety {

    // 原子性测试
    private static int i = 0;

    /**
     * 原子性一个操作是不可中断的。即使是多个线程一起执行，一个操作一旦开始，就不会被其他线程干扰。
     *
     */
    private void atomistic() {

        // 创建10条线程
        for (int k = 0 ; k < 100 ; k++) {
            Thread addIThread = new Thread(){
                // 让i自增100
                public void run() {
                    //System.out.println(Thread.currentThread() + "is run");
                    for (int j = 0 ; j < 1000 ; j++) {
                        i++;
                        Thread.yield();
                    }
                }
            };
            addIThread.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);

    }


    /**
     * 实现效果难以触发。
     * 原理为：CPU可能会对代码优化而进行指令重排。
     * 线程1调write，2调read。有可能write方法因指令重排导致乱序从而导致a=1还未执行，所以结果i=1，而不是想要的2.
     */
    private void order() {
        final OrderExample orderExample = new OrderExample();

        for (int i = 0 ; i < 10000 ; i++) {
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    orderExample.writer();
                }
            };

            Thread thread2 = new Thread() {
                @Override
                public void run() {
                    orderExample.read();
                }
            };

            thread1.start();
            thread2.start();
        }

    }


    /**
     * 这段代码永远不会停止，因为线程run方法读取stop值之后
     * 就存起来了，调其他方法修改之后while方法并不知情，所以导致run方法中的while循环
     * 永远无法停止。
     * 给stop变量加上volatile保证每次使用都读取一次可解。
     */
    private void visuable() throws InterruptedException{
        VisuableExample visuableExample = new VisuableExample();
        visuableExample.start();

        Thread.sleep(1000);
        visuableExample.stopIt();
        Thread.sleep(1000);

        System.out.println("now application is finish!");
        System.out.println("stop is:" + visuableExample.getStop());
    }


    public static void main(String[] ars) throws InterruptedException {
        ThreadSafety threadSafety = new ThreadSafety();
        // 原子性测试
        //threadSafety.atomistic();

        // 有序性测试
        //threadSafety.order();

        // 可见性测试
        threadSafety.visuable();
    }

}

/**
 * 有序性测试类。cpu对于执行顺序不影响代码结果的代码段有可能会进行指令重排
 * 而在多线程场景中指令重排可能会导致奇怪的错误。
 */
class OrderExample {
    // 有序性测试
    int a = 0;
    boolean flag = false;

    public void writer(){
        a = 1;
        flag = true;
    }

    public void read(){
        if (flag) {
            int i = a + 1;
            if (i == 2) {
                return;
            }
            System.out.println(i);
        }
    }

}


/**
 * 可见性是指当一个线程修改了某一个共享变量的值，其他线程是否能够立即知道这个修改。
 * 成因：
 * --编译器优化
 * --硬件优化（如写吸收、批操作）
 *
 */
class VisuableExample extends Thread{
    //private boolean stop;
    private volatile boolean stop;

    @Override
    public void run() {
        long i = 0;
        while (!stop) {
             i++;
        }
        System.out.println("Thread is run over, and i is:" + i);
    }

    public boolean getStop() {
        return stop;
    }

    public void stopIt() {
        stop = true;
    }
}
