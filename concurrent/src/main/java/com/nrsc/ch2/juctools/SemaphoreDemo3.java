package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo3 {
    private final static int threadCount = 520;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {

                if (semaphore.tryAcquire()) { //尝试获取1个许可
                    //总共3个许可，而每次只需拿到一个许可就可以运行下面的方法
                    //也就是说同一时刻可以允许三个线程拿到许可，即并发数为3
                    test(threadNum);
                    semaphore.release(); //释放3个许可
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int i) {
        try {
            Thread.sleep(1); //这里即使注释掉也是一样的效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("threadNum:{}", i);
    }
}

