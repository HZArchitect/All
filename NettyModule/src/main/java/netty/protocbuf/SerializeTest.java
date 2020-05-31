package netty.protocbuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class SerializeTest {

    public static void main(String[] args) throws IOException {
        int a = 121000066;
        int b = 200;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(int2bytes(a));
        byteArrayOutputStream.write(int2bytes(b));

        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));
    }

    /**
     * 大端字节序列化
     * @param i
     * @return
     */
    public static byte[] int2bytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >> 3 * 8);
        bytes[1] = (byte) (i >> 2 * 8);
        bytes[2] = (byte) (i >> 1 * 8);
        bytes[3] = (byte) (i >> 0 * 8);
        System.out.println("int: " + i + " is: " + Arrays.toString(bytes));
        return bytes;
    }

    /**
     * 大端还原int
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes) {
        return (bytes[0] << 3 * 8) |
               (bytes[1] << 2 * 8) |
               (bytes[2] << 1 * 8) |
               (bytes[3] << 0 * 8);
    }

}
