package com.nrsc.ch1.base.jmm.volatile_study;

import java.util.ArrayList;
import java.util.List;

/***
 * @author : Sun Chuan
 * @date : 2020/3/11 18:50
 * Description:  原子性问题 volatile无法保证原子性
 */
public class AtomicityProblem {

    /***共享变量*/
    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {

        Runnable increment = () -> {
            for (int i = 0; i < 1000; i++) {
                num++;
            }
        };

        List<Thread> threads = new ArrayList<>();

        //10个线程各执行1000次num++
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(increment);
            t.start();
            threads.add(t);
        }

        //确保10个线程都走完
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("10个线程执行后的结果为:" + num);
    }
}
