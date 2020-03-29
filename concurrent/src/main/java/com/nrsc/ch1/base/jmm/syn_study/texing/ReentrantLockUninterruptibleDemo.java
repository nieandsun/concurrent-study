package com.nrsc.ch1.base.jmm.syn_study.texing;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockUninterruptibleDemo {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 1. 定义一个Runnable
        Runnable run = () -> {
            // 2.对方法进行加锁
            lock.lock();
            try {
                String name = Thread.currentThread().getName();
                log.info("线程{}进入同步代码块", name);
                Thread.sleep(888888);  //保证不退出同步代码块
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
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
        //t2.interrupt(); //使用interrupt不可中断
        t2.stop(); //使用stop可中断
        log.info("线程t2尝试停止线程后");


        log.info("线程t1的状态:{}", t1.getState());
        log.info("线程t2的状态:{}", t2.getState());
    }
}