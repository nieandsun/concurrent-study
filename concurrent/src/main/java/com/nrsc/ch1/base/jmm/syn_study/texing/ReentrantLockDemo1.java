package com.nrsc.ch1.base.jmm.syn_study.texing;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 可重入特性： 指的是同一个线程获得锁之后，可以直接再次获取该锁
 * 最常出现的场景： 递归
 *
 * ReentrantLock肯定也是可重入锁
 */
@Slf4j
public class ReentrantLockDemo1 {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        Runnable sellTicket = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    String name = Thread.currentThread().getName();
                    log.info("我是run，抢到锁的是{}", name);
                    test01();
                } finally {
                    lock.unlock();
                }
            }

            public void test01() {
                lock.lock();
                try {
                    String name = Thread.currentThread().getName();
                    log.info("我是test01，抢到锁的是{}", name);
                } finally {
                    lock.unlock();
                }
            }
        };
        new Thread(sellTicket).start();
        new Thread(sellTicket).start();
    }
}
