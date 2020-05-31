package com.lh.study.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将文件映射至内存
 */
public class BufferMap {

    public static void bufferMap(String resource) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(resource, "rw");
        FileChannel fileChannel = raf.getChannel();
        //将文件映射到内存中
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,
                0, raf.length());

        while (mappedByteBuffer.hasRemaining()) {
            System.out.print((char)mappedByteBuffer.get());
        }

        //修改文件
        mappedByteBuffer.put(0, (byte)98);
        raf.close();
    }

}
