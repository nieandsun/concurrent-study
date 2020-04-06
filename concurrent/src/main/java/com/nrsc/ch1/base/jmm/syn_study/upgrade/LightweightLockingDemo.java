package com.nrsc.ch1.base.jmm.syn_study.upgrade;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

/****
 * 经过我测试，这样每次也都会是偏向锁
 */
public class LightweightLockingDemo {

    private static class MyRunnable implements Runnable {

        //static修饰只会初始化一次
        static Object obj = new Object();

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                synchronized (obj) {
                    //打印锁对象的布局
                    //Thread.sleep(1000);
                    System.out.println(ClassLayout.parseInstance(obj).toPrintable());
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MyRunnable r = new MyRunnable();

        new Thread(r, "t1").start();

                Thread.sleep(10000);

        new Thread(r, "t2").start();
    }
}
