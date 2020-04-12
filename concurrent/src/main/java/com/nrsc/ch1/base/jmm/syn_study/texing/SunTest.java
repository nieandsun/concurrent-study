package com.nrsc.ch1.base.jmm.syn_study.texing;

import java.util.concurrent.locks.LockSupport;

public class SunTest implements Runnable {


    @Override
    public void run() {
        System.out.println("enter");

        Thread thread = Thread.currentThread();
        LockSupport.park(thread);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("exit");

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SunTest());
        t1.start();
        Thread.sleep(2000);

        t1.interrupt();

        Thread.sleep(2000);
        LockSupport.unpark(t1);


    }
}
