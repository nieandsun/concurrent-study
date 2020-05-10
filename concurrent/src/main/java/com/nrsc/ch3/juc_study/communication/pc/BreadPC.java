package com.nrsc.ch3.juc_study.communication.pc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * @author : Sun Chuan
 * @date : 2020/3/19 9:48
 * Description: 
 */
@Slf4j
public class BreadPC {
    /***面包集合*/
    private int number = 0;
    private Lock lock = new ReentrantLock();  //生产者和消费者应该共用一把锁
    private Condition condition = lock.newCondition();//阻塞线程的条件

    /***生产者*/
    public synchronized void produceBread() {
        lock.lock();
        try {
            while (number >= 20) { //注意：多线程的判断不能用if---》有可能出现虚假唤醒问题
                //如果大于等于20箱，就等待  --- 如果这里为大于20的话，则20不会进入while，则会生产出21箱，所以这里应为>=
                condition.await();
            }
            //如果不到20箱就继续生产
            number++; //生产一箱
            log.warn("{}生产一箱面包，现有面包{}个", Thread.currentThread().getName(), number);
            //生产完,唤醒其他线程---> 这里被唤醒的线程可以是消费线程，因为我刚生产完，也可以是其他生产线程
            //但是此时还没有释放锁，要等当前线程把锁释放了，其他线程才能去抢锁
            condition.signalAll();
        } catch (Exception e) {
            log.error("生产者{},等待出错", Thread.currentThread().getName(), e);
        } finally {
            lock.unlock();
        }
    }


    /*** 消费者*/
    public void consumeBread() {

        lock.lock();
        try {
            //如果没有了就等待
            while (number <= 0) { //注意：多线程的判断不能用if---》有可能出现虚假唤醒问题
                condition.await();
            }
            //能走到这里说明i>0,所以进行消费
            number--; //消费一箱
            log.info("{}消费一个面包，现有面包{}个", Thread.currentThread().getName(), number);
            //消费完,通知其他线程
            condition.signalAll();
        } catch (Exception e) {
            log.error("消费者{},等待出错", Thread.currentThread().getName(), e);
        } finally {
            lock.unlock();
        }

    }
}
