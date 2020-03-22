package com.nrsc.ch2.cas;

import java.util.ArrayList;
import java.util.List;

public class CasDemo {

    //共享资源
    static int i = 0;

    public static void increase() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            for (int j = 0; j < 1000; j++) {
                increase();
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Thread thread = new Thread(r);
            threads.add(thread);
            thread.start();
        }

        //确保前面10个线程都走完
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(i);
    }
}
