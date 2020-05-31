package com.lh.study.java.jdkConcurrent.concurrentMode.FutureMode;

public class FutureMainApp {
    public static void main(String[] args) {
        Client client = new Client();
        //会立即获取到返回数据，没有setRealData的futureData
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            // 这里干点别的事用了一些时间。用sleep模拟，期间等待realData被构建
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        // 打印真实数据
        System.out.println("真实数据 = " + data.getResult());
    }
}
