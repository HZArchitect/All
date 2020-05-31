package com.lh.study.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 本代码演示NIO复制文件
 *
 * 重点理解Buffer的3个重要参数
 * position:当前缓冲区的位置，写模式下将从下一个位置写数据，读模式将从此位置后读取数据。
 * capactiy:缓存区总容量上限。
 * limit:缓冲区实际上限。读模式没读满，就显示上次写入的数据量相等。
 *
 */
public class NioCopyFile {

    public static void nioCopyFile(String resource, String destination)
            throws IOException {
        //读取文件流
        FileInputStream fis = new FileInputStream(resource);
        //写入文件流
        FileOutputStream fos = new FileOutputStream(destination);
        //读文件通道
        FileChannel readChannel = fis.getChannel();
        //写文件通道
        FileChannel writeChannel = fos.getChannel();
        //分配缓存大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            buffer.clear();
            //读入1K数据
            int len = readChannel.read(buffer);
            //-1为读完了
            if (len == -1) {
                break;
            }
            //翻转，重置标志位，清除指针进入写状态
            buffer.flip();
            //写入文件
            writeChannel.write(buffer);
        }

        //关闭通道
        readChannel.close();
        writeChannel.close();
    }

    public static void main(String[] args) throws IOException {
        String resource = "C:\\Users\\pc\\Desktop\\账号.txt";
        String destination = "C:\\Users\\pc\\Desktop\\new.txt";

        NioCopyFile.nioCopyFile(resource, destination);
    }

}
