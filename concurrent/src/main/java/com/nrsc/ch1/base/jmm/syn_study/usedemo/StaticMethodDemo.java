package com.nrsc.ch1.base.jmm.syn_study.usedemo;

public class StaticMethodDemo implements Runnable {
    //共享资源
    static int i = 0;

    /**
     * synchronized 修饰静态方法 --- 锁对象为StaticMethodDemo对应的class对象
     */
    public static synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //注意: synchronized 修饰的是静态方法，锁对象为方法所在对象的class对象，该对象在jvm虚拟机里只有一个，
        //因此这里你想new一个StaticMethodDemo对象，或者new两个都可以
        Thread t1 = new Thread(new StaticMethodDemo());
        Thread t2 = new Thread(new StaticMethodDemo());

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(i);
    }

}
