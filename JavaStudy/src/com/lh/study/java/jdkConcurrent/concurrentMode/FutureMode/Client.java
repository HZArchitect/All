package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode;

public class Client {

    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();

        // futureData的构建很慢，所以单独开一个线程运行
        new Thread() {
            @Override
            public void run() {
                //realData构造非常慢
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }.start();

        // 线程开启之后在后台运行，但是client方法会立即返回futureData
        return futureData;
    }

}
