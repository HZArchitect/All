package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode.JDKFuture;

import java.util.concurrent.Callable;

/**
 * Callable是JDK提供的
 */
public class RealData implements Callable<String> {

    private String para;

    public RealData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < 10 ; i++) {
            sb.append(para + ":" + i + '\t');
            try {
                // 每次停止1毫秒，模拟很慢的过程
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        return sb.toString();
    }
}
