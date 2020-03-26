package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierDemo2 {
    private final static int threadCount = 2;
    static CyclicBarrier barrier = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            //这里故意让后续线程晚一秒开启
            Thread.sleep(2000);
            exec.execute(() -> test(threadNum));
        }
        exec.shutdown();

    }

    private static void test(int threadNum) {
        try {
            log.info("线程{}已经准备就绪！", threadNum);
            barrier.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } catch (BrokenBarrierException e) {
            log.error("BrokenBarrierException", e);
        } catch (TimeoutException e) {
            log.error("TimeoutException", e);
        }

        log.info("线程{}继续运行！", threadNum);
    }
}
