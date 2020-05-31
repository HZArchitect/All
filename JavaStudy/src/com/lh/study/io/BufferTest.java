package com.lh.study.io;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 重点理解Buffer的3个重要参数
 * position:当前缓冲区的位置，写模式下将从下一个位置写数据，读模式将从此位置后读取数据。
 * capacity:缓存区总容量上限。
 * limit:缓冲区实际上限。读模式没读满，就显示上次写入的数据量相等。
 *
 * rewind()  ---position置0，清除标志位
 * clear() ---position置0，limit设置成capacity大小，清除标志位
 * flip() ---limit设置成position所在位置，position置0，清除标志位。通常在读写转换时使用
 *
 */
public class BufferTest {

    public static void main(String[] args) throws InterruptedException {
        //设置缓冲区大小15字节
        ByteBuffer buffer = ByteBuffer.allocate(15);

        System.out.println("limit=" + buffer.limit() + " capacity=" + buffer.capacity()
        + " position=" + buffer.position());

        for (int i = 0 ; i < 10 ; i++) {
            //存入一个字节，循环10次
            buffer.put((byte)i);
        }

        //此时缓存中已经存入了10个字节
        System.out.println("limit=" + buffer.limit() + " capacity=" + buffer.capacity()
                + " position=" + buffer.position());

        //重置position到0，清除标志位mark，limit设置到position位置，通常在读写转换时使用
        buffer.flip();

        System.out.println("limit=" + buffer.limit() + " capacity=" + buffer.capacity()
                + " position=" + buffer.position());

        for (int i = 0 ; i < 5 ; i++) {
            //取出一个字节，循环5次
            System.out.println(buffer.get());        System.out.println("limit=" + buffer.limit() + " capacity=" + buffer.capacity()
                    + " position=" + buffer.position());
        }

        //再次重置position
        buffer.flip();

        System.out.println("limit=" + buffer.limit() + " capacity=" + buffer.capacity()
                + " position=" + buffer.position());

        //最大堆内存
        System.out.println("Xmx=" + Runtime.getRuntime().maxMemory()/1024.0/1024 + "M");

        //空闲堆内存
        System.out.println("free mem=" + Runtime.getRuntime().freeMemory()/1024.0/1024 + "M");

        //总堆内存
        System.out.println("total mem=" + Runtime.getRuntime().totalMemory()/1024.0/1024 + "M");

        System.out.println(Charset.defaultCharset().name());

        //占20m内存
        byte[] 占内存 = new byte[25 * 1024 *1024];

        //最大堆内存
        System.out.println("Xmx=" + Runtime.getRuntime().maxMemory()/1024.0/1024 + "M");

        //空闲堆内存
        System.out.println("free mem=" + Runtime.getRuntime().freeMemory()/1024.0/1024 + "M");

        //总堆内存
        System.out.println("total mem=" + Runtime.getRuntime().totalMemory()/1024.0/1024 + "M");

        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();


        //程序不停   测试-XX:-PrintClassHistogram 参数   打印运行中程序类信息
//        while (true) {
//            Thread.sleep(100);
//        }

    }
}

class a {

}


class b {

}