package com.nrsc.ch1.base.jmm.syn_study;

public class GeneralMethodDemo implements Runnable {

    //共享资源
    static int i = 0;

    /**
     * synchronized 修饰普通方法 --- 锁对象为当前对象，相当于this
     */
    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        //注意: synchronized 修饰的是普通方法，锁对象为方法所在的对象
        //因此这里只能new 一个GeneralMethodDemo对象
        GeneralMethodDemo generalMethodDemo = new GeneralMethodDemo();
        Thread t1 = new Thread(generalMethodDemo);
        Thread t2 = new Thread(generalMethodDemo);

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
