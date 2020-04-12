package com.nrsc.ch1.base.jmm.syn_study.texing;

import lombok.extern.slf4j.Slf4j;

/***
 *  演示synchronized不可中断的思路    
 *   1.定义一个Runnable        
 *   2.在Runnable定义同步代码块        
 *   3.先开启一个线程来执行同步代码块,保证不退出同步代码块        
 *   4.后开启一个线程来执行同步代码块(阻塞状态)        
 *   5.停止第二个线程  --- 可以发现无法停止
 */
@Slf4j
public class SnyUninterruptibleDemoCopy {
    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 1. 定义一个Runnable
        Runnable run = () -> {
            // 2.在Runnable定义同步代码块
            synchronized (obj) {
                try {
                    String name = Thread.currentThread().getName();
                    log.info("线程{}进入同步代码块", name);
                    Thread.sleep(10000);  //保证不退出同步代码块
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 3. 先开启一个线程来执行同步代码块
        Thread t1 = new Thread(run);
        t1.start();
        Thread.sleep(1000);

        // 4. 后开启一个线程来执行同步代码块(阻塞状态)
        Thread t2 = new Thread(run);
        t2.start();

        // 5.停止第二个线程
        log.info("线程t2尝试停止线程前");
        //t2.interrupt();
        t2.stop(); //无论使用interrupt还是stop都不能把t2给中断
        log.info("线程t2尝试停止线程后");

        log.info("线程t1的状态:{}", t1.getState());
        log.info("线程t2的状态:{}", t2.getState());
    }
}
