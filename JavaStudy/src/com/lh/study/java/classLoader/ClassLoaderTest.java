package com.lh.study.java.classLoader;

/**
 * JVM自带的类加载器又分为三种
 *
 * 启动类加载器（bootstrap）、扩展类加载器、应用（系统）类加载器。
 * 其中启动类加载器负责加载$JAVA_HOME\jre\lib路径下的类（常见的比如rt.jar文件，比如java.lang.Object）；
 * 扩展类加载器负责加载$JAVA_HOME\jre\lib\ext路径下的类（比如com.sun.nio.zipfs.ZipUtils）；
 * 应用类加载器负责加载用户当前类路径下的类（通常是我们自己写的类或者第三方依赖包中的类）。
 *
 * 这三种类加载器有层级关系。应用类加载器的父级是扩展类加载器，扩展类加载器的父级是启动类加载器。
 * 为了说明这点，我们可以自己编写一个test类做测试。
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Object object = new Object();
        ClassLoader objLoader = object.getClass().getClassLoader();
        System.out.println("Object的类加载器是:" + objLoader);
        //System.out.println("Object的类加载器是:" + objLoader.getParent());

        Class zipClass = Class.forName("com.sun.nio.zipfs.ZipUtils");
        ClassLoader zipLoader = zipClass.getClassLoader();
        System.out.println("ZipUtils的类加载器是:" + zipLoader);
        System.out.println("ZipUtils加载器的父亲是:" + zipLoader.getParent());

        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        ClassLoader testLoader = classLoaderTest.getClass().getClassLoader();
        System.out.println("ClassLoaderTest的类加载器是:" + testLoader);
        System.out.println("ClassLoaderTest加载器的父亲是:" + testLoader.getParent());
    }

}
