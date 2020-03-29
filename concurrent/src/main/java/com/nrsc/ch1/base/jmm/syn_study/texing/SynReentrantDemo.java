package com.nrsc.ch1.base.jmm.syn_study.texing;

import lombok.extern.slf4j.Slf4j;

/***
 * 可重入特性： 指的是同一个线程获得锁之后，可以直接再次获取该锁
 * 最常出现的场景： 递归
 * synchronized为可重入锁验证demo
 */
@Slf4j
public class SynReentrantDemo {

    public static void main(String[] args) {

        Runnable sellTicket = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    String name = Thread.currentThread().getName();
                    log.info("我是run，抢到锁的是{}", name);
                    test01();
                } //正常来说走出临界区（这个括号）才会释放锁，但是再没走出之前，又进入test01，
                //而test01需要和本方法一样的锁
                //如果不可重入的话，就将出现死锁了-->即test01方法等着释放锁，而run方法又不会释放锁
                //因此synchronized只有可以在不释放run方法的锁的情况下，又再次获得该锁才不会有问题
            }

            public void test01() {
                synchronized (this) {
                    String name = Thread.currentThread().getName();
                    log.info("我是test01，抢到锁的是{}", name);
                }
            }
        };
        new Thread(sellTicket).start();
        new Thread(sellTicket).start();
    }
}
