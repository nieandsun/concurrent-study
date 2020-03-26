package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierDemo3 {
    private final static int threadCount = 5;

    private static Map<String, Integer> concurrentHashMap = new ConcurrentHashMap();

    static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        MapUtils.verbosePrint(System.out, "前几个线程准备的数据", concurrentHashMap);
    });

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;

            //这里故意让后续线程晚一秒开启
            Thread.sleep(1000);
            exec.execute(() -> test(threadNum));
        }
        exec.shutdown();

    }

    private static void test(int threadNum) {
        try {

            //模拟进行部分准备工作,并将准备的结果放入到Map容器
            int random = new Random().nextInt(10);
            log.info("线程{}准备的数据-{}",Thread.currentThread().getName(),random);
            concurrentHashMap.put(Thread.currentThread().getName(), random);

            log.info("线程{}已经准备就绪！", threadNum);
            barrier.await();

        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } catch (BrokenBarrierException e) {
            log.error("BrokenBarrierException", e);
        }

        log.info("线程{}继续运行！", threadNum);
    }
}
