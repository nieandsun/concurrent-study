package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SemaphoreDemo4 {
    private final static int threadCount = 520;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {

                try {
                    //5秒内尝试获取1个许可
                    if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
                        test(threadNum);
                        semaphore.release(); //释放3个许可
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int i) {
        try {
            Thread.sleep(1000); //这里即使注释掉也是一样的效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("threadNum:{}", i);
    }
}

