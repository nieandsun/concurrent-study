package com.nrsc.ch4.threadlocal;

import org.junit.Test;

import java.lang.ref.WeakReference;

/***
 * 弱引用： 只要发生gc弱引用就会被回收
 */
public class WeakReferenceDemo {

    @Test
    public void test01() {

        WeakReference<byte[]> m = new WeakReference<>(new byte[1024 * 1024 * 10]);
        System.out.println("弱引用对象指向的对象" + m.get());

        System.gc();

        //只要发生gc弱引用对象指向的对象就会被回收
        System.out.println("发生gc后，弱引用对象指向的对象" + m.get());
    }


    @Test
    public void test02() {
        byte[] bytes = new byte[1024 * 1024 * 10];
        WeakReference<byte[]> m = new WeakReference<>(bytes);
        System.out.println("弱引用对象指向的对象" + m.get());

        System.gc();

        //只要发生gc弱引用对象指向的对象就会被回收
        System.out.println("发生gc后，弱引用对象指向的对象" + m.get());
    }
}
