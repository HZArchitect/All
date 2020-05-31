package com.lh.study.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 普通且非常低效的客户端。
 */
public class EchoClient implements Runnable {
    private String ip;
    private int port;
    private String msg;
    private static final int sleep_time = 5000;

    public EchoClient(String ip, int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg;
    }

    @Override
    public void run() {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            client = new Socket();
            //建立一个连接
            client.connect(new InetSocketAddress(ip, port));
            writer = new PrintWriter(client.getOutputStream());

            for (int i = 0 ; i < msg.length() ; i++) {
                //将数据传输至服务端
                writer.print(msg.charAt(i));
                //发一个字符阻塞1s，模拟超低效客户端
                LockSupport.parkNanos(1000 * 1000 * 1000);
            }

            writer.println();
            writer.flush();

            //读取服务端回传的数据
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("msg from server: " + reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // 关闭资源
    }


    public static void main(String[] args) {
        String ip = "localhost";
        int port = 8000;
        String msg = "hello world";

        // 创建线程池用来处理客户端
        ExecutorService tp = Executors.newFixedThreadPool(10);

        EchoClient echoClient = new EchoClient(ip, port, msg);
        for (int i = 0 ; i < 10 ; i++) {
            tp.execute(echoClient);
        }
    }
}

