package com.lh.study.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * NIO服务端
 * 还无法运行
 */
public class EchoAIOServer {

    //线程池
    private ExecutorService tp = Executors.newCachedThreadPool();
    InetSocketAddress socketAddress = new InetSocketAddress(PORT);
    AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(socketAddress);

    private static final int PORT = 8000;

    public EchoAIOServer() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        EchoAIOServer aioServer = new EchoAIOServer();
        try {
            aioServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startServer() throws Exception {
        while (true) {
            server.accept(null, new Handler());
        }
    }


    class Handler implements CompletionHandler<AsynchronousSocketChannel, Object> {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(AsynchronousSocketChannel result, Object attachment) {
            System.out.println(Thread.currentThread().getName() + " is running.");

            Future<Integer> writeResult = null;

            try {
                buffer.clear();
                result.read(buffer).get(100, TimeUnit.SECONDS);
                buffer.flip();
                writeResult = result.write(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } finally {

                try {
                    server.accept(null, this);
                    writeResult.get();
                    result.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("failed " + exc);
        }
    }

}


