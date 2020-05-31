package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode;

/**
 * FutureData是RealData的包装
 */
public class FutureData implements Data {

    protected RealData realData = null;
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();// 当realData已经被注入，通知其他等待中的线程
    }

    @Override
    public synchronized String getResult() {
        // 当前线程get时候发现还未ready
        while (!isReady) {
            try {
                // 一直等待，直到RealData被注入
                wait();
            } catch (InterruptedException e) {}
        }
        return realData.result;//由realData实现
    }
}
