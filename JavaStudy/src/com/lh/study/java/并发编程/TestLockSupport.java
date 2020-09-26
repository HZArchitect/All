package com.lh.study.java.并发编程;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 26; i++) {
                    LockSupport.unpark(t2);
                    System.out.println(i);
                    LockSupport.park();
                }
            }
        }, "t1");

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (char c = 'A';c <= 'Z';c++){
                    LockSupport.unpark(t1);
                    System.out.println(c);
                    LockSupport.park();
                }
            }
        }, "t2");

        t1.start();
        t2.start();

    }
}
