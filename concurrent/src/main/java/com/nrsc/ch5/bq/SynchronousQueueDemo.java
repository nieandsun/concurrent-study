package com.nrsc.ch5.bq;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SynchronousQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<String> b1 = new ArrayBlockingQueue<>(3,true);

        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                log.info("线程{}往队列中插入数据", Thread.currentThread().getName());
                blockingQueue.put("1");

                log.info("线程{}往队列中插入数据", Thread.currentThread().getName());
                blockingQueue.put("2");

                log.info("线程{}往队列中插入数据", Thread.currentThread().getName());
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("线程{}从队列中取出数据{}", Thread.currentThread().getName(), blockingQueue.take());

                TimeUnit.SECONDS.sleep(2);
                log.info("线程{}从队列中取出数据{}", Thread.currentThread().getName(), blockingQueue.take());

                TimeUnit.SECONDS.sleep(2);
                log.info("线程{}从队列中取出数据{}", Thread.currentThread().getName(), blockingQueue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

    }
}
