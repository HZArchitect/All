package com.lh.study.java.jdkConcurrent.concurrentOperation.jdkConcurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 用法和重入锁类似。
 * 读读不互斥。
 * 读阻塞写，写也会阻塞读。
 * 写写互斥。
 */
public class ReadWriteLock {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();


}
