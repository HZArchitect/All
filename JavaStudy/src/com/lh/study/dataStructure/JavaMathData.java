package com.lh.study.dataStructure;

/**
 * 演示各种java中的数据写法
 */
public class JavaMathData {

    public static void main(String[] args) {
        System.out.println("maxInt:" + Integer.MAX_VALUE);
        System.out.println("9999e2:" + 9999e2);
        System.out.println("0xDada_Cafe:" + 0xDada_Cafe);
        System.out.println("0x1.fffffeP+127f:" + 0x1.fffffeP+127f);
        System.out.println("33e2:" + 33e2);
        System.out.println("0x1.fffep-12f:" + 0x1.fffep-12f);
        System.out.println("0x1.fffep-12f:" + 0x1.fffep-12f);
        System.out.println("0b1_1_1_0_1:" + 0b1_1_1_0_1);
        System.out.println("0xABC:" + 0xABC);
        //System.out.println("0b1_1_1_0_2:" + 0b1_1_1_0_2);
        int a = 0xABC;
        int b = 011;
        int c = 0b1111_1111_1111_1111_1111_1111_1111_1111;
        int d = (int)2147483649l;
        System.out.println("01:" + Integer.toBinaryString(-12));
        System.out.println("c:" + c);
        System.out.println("d:" + d);
        System.gc();

    }
}
