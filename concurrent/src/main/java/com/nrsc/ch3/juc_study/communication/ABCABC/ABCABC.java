package com.nrsc.ch3.juc_study.communication.ABCABC;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * @author : Sun Chuan
 * @date : 2020/3/20 17:20
 * Description: 
 */
@Slf4j
public class ABCABC {

    static class PrintClass {
        //共用一把锁
        private Lock lock = new ReentrantLock();
        //三个不同的条件
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();

        private int flag = 1; //打印A时使用标志1,B->2, C->3

        public void printA() {
            lock.lock();
            try {
                //如果标识不为1，则利用条件c1将次线程阻塞
                while (flag != 1) {
                    c1.await();
                }
                //走到这里说明标识为1，打印A，并将标识为改为2，且将利用c2条件阻塞的线程唤醒
                flag = 2;
                log.info("A");
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void printB() {
            lock.lock();
            try {
                //如果标识不为2，则利用条件c2将次线程阻塞
                while (flag != 2) {
                    c2.await();
                }
                //走到这里说明标识为2，打印B，并将标识为改为3，且将利用c3条件阻塞的线程唤醒
                flag = 3;
                log.info("B");
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void printC() {
            lock.lock();
            try {
                //如果标识不为3，则利用条件c3将次线程阻塞
                while (flag != 3) {
                    c3.await();
                }
                //走到这里说明标识为2，打印B，并将标识为改为3，且将利用c3条件阻塞的线程唤醒
                flag = 1;
                log.info("C");
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        PrintClass printClass = new PrintClass();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                printClass.printA();
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                printClass.printB();
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                printClass.printC();
            }
        }, "线程C").start();
    }

}
