package com.lh.study.java.jdkConcurrent.concurrentMode.ImmutabilityMode;

/**
 * 类被final修饰保证类对象被创建无法被再次赋值
 * 且final修饰不可能有子类
 *
 * Java中的不变模式
 * String
 * Boolean
 * Byte
 * Character
 * Double
 * Float
 * Integer
 * Long
 * Short
 */
public final class Product {

    //确保无子类
    private final String no;

    //私有属性，不会被其他对象获取
    private final String name;

    //final属性保证不会被2次赋值
    private final double price;

    //创建对象时，必须制定数据，因为创建之后无法修改
    public Product(String no, String name, double price) {
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}
