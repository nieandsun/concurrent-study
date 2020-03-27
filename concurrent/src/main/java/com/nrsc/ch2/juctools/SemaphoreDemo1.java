package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo1 {
    private final static int threadCount = 12;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {

                    semaphore.acquire(); //获取一个许可

                    //总共3个许可，而每次只需拿到一个许可就可以运行下面的方法
                    //也就是说同一时刻可以允许三个线程拿到许可，即并发数为3
                    test(threadNum);

                    semaphore.release(); //释放一个许可
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int i) throws InterruptedException {
        Thread.sleep(2000);
        log.info("threadNum:{}", i);
    }
}
