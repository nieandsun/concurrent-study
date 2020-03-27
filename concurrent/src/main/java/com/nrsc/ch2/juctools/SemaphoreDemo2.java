package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo2 {
    private final static int threadCount = 4;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {

                    semaphore.acquire(3); //获取3个许可

                    //总共3个许可，每次都全获取了
                    //也就是说同一时刻只允许一个线程拿到许可，即并发数为1
                    test(threadNum);

                    semaphore.release(3); //释放3个许可
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
