package com.lh.study.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO服务端
 *
 */
public class EchoNIOServer {

    //用于记录读操作的时间戳，最后统计从读到回写一共花费的时间
    public static Map<Socket, Long> geym_time_stat = new HashMap<>(10240);

    //选择器
    private Selector selector;

    //线程池
    private ExecutorService tp = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        EchoNIOServer nioServer = new EchoNIOServer();

        try {
            nioServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();

        //创建并设置一个非阻塞server socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        //绑定服务器socket 到本地
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);
        serverSocketChannel.socket().bind(inetSocketAddress);
        //InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8000);

        //将server socket注册成选择器
        SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //选择已经准备好的事件。这个事件可以是读，写，连接
            selector.select();
            /*另一种方式
            if (selector.selectNow() == 0) {
                continue;
            }*/

            //获取已准备好的selectionKeys
            Set readyKeys = selector.selectedKeys();

            //遍历
            Iterator i = readyKeys.iterator();
            long end = 0;
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                //防止后面被重复处理
                i.remove();

                //根据事件的不同做不同的处理
                if (key.isAcceptable()) {
                    //为接受事件
                    doAccept(key);

                } else if (key.isValid() && key.isReadable()) {//这个key表示为读的事件
                    //获取这个selectionKey的socket
                    Socket socket = ((SocketChannel)key.channel()).socket();
                    //记录读准备好的时间
                    if(!geym_time_stat.containsKey(socket)) {
                        geym_time_stat.put(socket, System.currentTimeMillis());
                    }
                    doRead(key);

                } else if (key.isValid() && key.isWritable()) {
                    doWrite(key);
                    end = System.currentTimeMillis();
                    //获取这个selectionKey的socket
                    Socket socket = ((SocketChannel)key.channel()).socket();
                    //拿到塞进map的时间
                    long start = geym_time_stat.remove(socket);
                    System.out.println(((SocketChannel)key.channel()).socket().getRemoteSocketAddress() + " 用时:" + (end - start) + "ms");
                }

            }
        }
    }


    /**
     * 接受一个新的客户端并设置为读
     * @param selectionKey
     */
    private void doAccept(SelectionKey selectionKey) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        //客户端的channel
        SocketChannel clientChannel;

        try {
            //拿到客户端channel，配置成非阻塞模式
            clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);

            //将客户端的channel注册成读事件
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);

            EchoClient echoClient = new EchoClient();
            //给selectionKey加一个附件，后续可能会用到
            clientKey.attach(echoClient);

            //InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepte connection from " + clientChannel.socket().getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理写事件
     * @param key
     */
    private void doWrite(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        //取出附件
        EchoClient echoClient = (EchoClient)key.attachment();
        LinkedList<ByteBuffer> outPutQueue = echoClient.getOutPutQueue();

        ByteBuffer buffer = outPutQueue.getLast();

        try {
            int len = channel.write(buffer);
            if (len == -1) {
                disconnect(key);
                return;
            }

            if (buffer.remaining() == 0) {
                outPutQueue.removeLast();
            }

        } catch (IOException e) {
            e.printStackTrace();
            disconnect(key);
            return;
        }

        //写完了
        if (outPutQueue.size() == 0) {
            key.interestOps(SelectionKey.OP_READ);
        }

    }


    /**
     * 读事件
     * @param key
     */
    private void doRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(buffer);
            //读完了
            if (len < 0) {
                disconnect(key);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(key);
            return;
        }

        //读模式改写模式
        buffer.flip();
        //交给线程处理
        tp.execute(new HandleMsg(key, buffer));
    }


    private void disconnect(SelectionKey key) {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            Socket socket = channel.socket();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 客户端附加类
     * 存读写数据
     */
    class EchoClient {
        //从客户端读取的数据存入队列，写出的时候删除该条数据
        private LinkedList<ByteBuffer> outPutQueue;

        public EchoClient() {
            this.outPutQueue = new LinkedList<ByteBuffer> ();
        }

        // 获取输出队列
        public LinkedList<ByteBuffer> getOutPutQueue() {
            return outPutQueue;
        }

        // 每条数据塞最前面，取的时候取最后一个数据
        public void enQueue(ByteBuffer byteBuffer) {
            outPutQueue.addFirst(byteBuffer);
        }
    }


    class HandleMsg implements Runnable {
        SelectionKey selectionKey;
        ByteBuffer byteBuffer;

        public HandleMsg(SelectionKey selectionKey, ByteBuffer byteBuffer) {
            this.selectionKey = selectionKey;
            this.byteBuffer = byteBuffer;
        }

        @Override
        public void run() {
            //将附件拿出来
            EchoClient echoClient = (EchoClient)selectionKey.attachment();
            //放入client
            echoClient.enQueue(byteBuffer);

            //修改事件，读和写都感兴趣
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强迫selector立即返回
            selector.wakeup();
        }
    }

}


