package com.lh.study.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简单的服务端，客户端传过来什么数据就回写什么数据
 *
 * 此系统的问题，为每一个客户端提供一个线程，若客户端出现延时等异常，线程可能被长时间占用。
 * 因为数据的准备和读取都在这个线程中。此时如果客户端数量众多，可能会消耗大量系统资源。
 *
 * 解决方案：
 * -----非阻塞NIO
 * -----数据准备好以后再开始工作
 */
public class EchoServer {

    /**
     * 处理客户端数据的线程
     */
    static class HandleMsg implements Runnable {

        Socket clientSocket;

        HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                //从InputStream中读取客户端所发送的消息
                String str = null;
                //计时
                long start = System.currentTimeMillis();
                while ((str = is.readLine()) != null) {
                    os.println(str);
                }
                long end = System.currentTimeMillis();
                long used = end - start;
                System.out.println(clientSocket.getRemoteSocketAddress()+" has used " + used + "ms");

            } catch (IOException e) {e.printStackTrace();}
            finally {
                try {
                    if(is != null)is.close();
                    if(os != null)os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args) {
        ServerSocket server = null;
        Socket clientSocket = null;

        // 创建线程池用来装处理客户端的线程
        ExecutorService tp = Executors.newFixedThreadPool(10);

        try {
            server = new ServerSocket(8000);

            while (true) {
                //等待客户端连接，一旦有客户端连接
                clientSocket = server.accept();
                //打印日志
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                //创建线程交给线程池处理，将这个Socket传给逻辑处理线程
                tp.execute(new HandleMsg(clientSocket));
            }

        } catch (IOException e) {e.printStackTrace();}

    }



}
