package com.nrsc.ch2.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class CountDownLatchDemo3 {

    /***
     * 继承了Callable的内部类
     */
    private static class Runner implements Callable<Integer> {
        private CountDownLatch judge;//裁判
        private CountDownLatch runner;//跑步者

        public Runner(CountDownLatch begin, CountDownLatch end) {
            super();
            this.judge = begin;
            this.runner = end;
        }

        @Override
        public Integer call() throws Exception {
            int score = new Random().nextInt(10);

            //------------------------------------------------------------
            //judge不进行countDown，所有的线程都会在这里阻塞住
            //------------------------------------------------------------
            log.info("线程-{}准备就绪", Thread.currentThread().getName());
            judge.await();

            log.info("线程-{}开始跑步", Thread.currentThread().getName());
            //进行跑步
            TimeUnit.MILLISECONDS.sleep(score);//跑步需要花的时间
            //运动员跑步完成
            runner.countDown();
            return score; //将执行时间返回
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int runnerNum = 8;
        CountDownLatch judge = new CountDownLatch(1);//裁判
        CountDownLatch runner = new CountDownLatch(runnerNum);//运动员

        //新建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(runnerNum);
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < runnerNum; i++) {
            //每个线程都开始运行了，但是都会运行到 judge.await();那里停住，等待judge的countDown方法
            Future<Integer> submit = executorService.submit(new Runner(judge, runner));
            list.add(submit);
        }

        /***
         * 注意，我故意把关闭线程池的操作放在这里了，仍然可以获取到想要的结果
         * 这是因为线程池并不会立即关闭，而是将任务执行完才会关闭
         */
        executorService.shutdown();


        //枪声响起
        judge.countDown(); //预备，开始，跑！！！！

        //等待所有的跑步者跑完
        runner.await();

        log.info("跑步结果：");
        //所有跑步者的跑步结果
        for (Future<Integer> future : list) {
            try {
                Integer res = future.get();
                System.out.print(res + " ");
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
