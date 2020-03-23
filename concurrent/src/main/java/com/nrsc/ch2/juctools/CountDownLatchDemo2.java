package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo2 {
    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            new Thread(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception:", e);
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        //等其他线程11MILLISECONDS后，放行当前线程await后的其他操作
        countDownLatch.await(11, TimeUnit.MILLISECONDS);
        log.info("finish");

    }

    private static void test(int i) throws InterruptedException {
        Thread.sleep(10);
        log.info("threadNum:{}", i);
    }
}
