package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class CountDownLatchDemo1 {
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
                } finally { //放在finally块里保证线程无论怎样都会执行countDown方法
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();

        //可以想到finish肯定会在上面的线程都运行完才执行
        log.info("finish");

    }

    private static void test(int i) throws InterruptedException {
        Thread.sleep(100);
        log.info("threadNum:{}", i);
        Thread.sleep(100);
    }
}
