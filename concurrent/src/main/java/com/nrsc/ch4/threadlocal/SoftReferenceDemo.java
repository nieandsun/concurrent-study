package com.nrsc.ch4.threadlocal;

import java.lang.ref.SoftReference;

/***
 *
 * 软引用: 运行内存够用时不会被回收，运行内存不够时会被自动回收
 *  配置堆内存大小为： -Xmx20M ---> 最大堆内存设置为20M
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println("软引用对象指向的对象："+m.get());

        System.gc();

        //堆内存够用的情况下,gc后通过软引用指向的对象不会被回收
        System.out.println("堆内存够用时，发生GC后，软引用指向的对象：" + m.get());

        //再分配一个数组(强引用), heap将装不下,这时候系统会进行一次垃圾回收,
        //此时堆内存将不够用，就会把通过软引用指向的对象给gc掉
        byte[] b = new byte[1024 * 1024 * 11];
        System.out.println("堆内存不够用时，软引用指向的对象："+m.get());

        System.gc();//即使再次gc也无法将m指向的SoftReference对象回收掉
        System.out.println("软引用指向的对象被gc后，SoftReference对象：" +m); //需要手动的将m置为null才能将其回收
    }
}
