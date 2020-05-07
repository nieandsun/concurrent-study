package com.nrsc.ch4.threadlocal;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/***
 * @author : Sun Chuan
 * @date : 2020/5/6 11:22
 * Description: 
 */
public class PhantomReferenceDemo {

    @ToString
    @AllArgsConstructor
    static class M {
        private String name;

        @Override
        protected void finalize() throws Throwable {
            System.out.println("m被回收了。。。");
        }
    }

    public static final List<Object> LIST = new ArrayList<>();
    public static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<M> phantomReference = new PhantomReference<>(new M("yoyo"), QUEUE);
       // System.out.println(phantomReference.get());
        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1204 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                /**虚引用永远拿不到值*/
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends M> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("虚引用被JVM回收了-----" + poll);
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
