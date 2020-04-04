package com.nrsc.ch3.juc_study;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo1 {

    private static int i = 0;
    private static Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        };

        new Thread(r).start();

        Thread.sleep(1000);

        new Thread(r).start();

        LockSupport.park();
    }
}
