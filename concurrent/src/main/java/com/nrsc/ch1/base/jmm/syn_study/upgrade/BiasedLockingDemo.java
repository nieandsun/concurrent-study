package com.nrsc.ch1.base.jmm.syn_study.upgrade;
import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

/***
 * 始终为偏向锁
 */
public class BiasedLockingDemo {

    private static class MyThread extends Thread {
        //static修饰只会初始化一次
        static Object obj = new Object();

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                synchronized (obj) {
                    //打印锁对象的布局
                    Thread.sleep(1000);
                    System.out.println(ClassLayout.parseInstance(obj).toPrintable());
                }
            }
        }
    }

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
    }
}
