package com.lh.study.java.classLoader;

/**
 * 一个类进行加载的大概过程是这样的，首先会从缓存中查找是否加载过该类，如果有，则直接使用，
 * 如果没有则会委托给父类加载器，父类加载器会继续委托给父类加载器，
 * 直到启动类加载器（也即bootstrap ClassLoader），如果启动类加载器没有找到，
 * 则会由扩展类加载器进行查找，如果仍未找到，
 * 才会由应用类加载器进行查找。这种机制有一个好处就是可以保证类仅仅被加载一次。
 *
 * 为了说明这一点我们编写个简单例子验证下，先建个包名为java.lang，然后再新建个类叫String。
 * 然后再里面写个main方法，然后启动。如下启动时就报错了。
 * 因为启动程序会默认查找String.class对象，首先会由启动类加载器查找，结果找到了，因为JDK自带该类，在rt.jar文件中。
 * 但该类中并无main方法，所以会报错。也就是说在当前条件下，我们自己写的这个类是不会得到加载的。
 */
//public class String {
//
//    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//    }
//
//}
