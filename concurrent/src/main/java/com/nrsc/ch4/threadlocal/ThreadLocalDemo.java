package com.nrsc.ch4.threadlocal;

import java.io.IOException;

/***
 * @author : Sun Chuan
 * @date : 2020/5/6 11:32
 * Description: 
 */
public class ThreadLocalDemo {
    static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws IOException {
        //在主线程里set值
        tl.set("1111");

        new Thread(() -> {
            //其他线程里get不到主线程的值
            String s = tl.get();
            System.out.println("非主线程：" + s);
        }).start();


        // 主线程可以获取到主线程设置的值
        // --> 即当前线程可以从tl里获取到当前线程设置的值，无法获取到其他线程设置的值
        // --> 其他线程也获取不到当前线程设置的值
        System.out.println("主线程：" + tl.get());

        //清除当前线程的value值 ---> help GC
        tl.remove();
        //将tl1值为null，清除tl1与ThreadLocal对象之间的强引用 ---> help GC
        tl = null;

        System.in.read();
    }
}
