package com.nrsc.ch2.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Slf4j
public class FutureTaskDemo1 {

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int num = 0;
            log.info("开始进行计算。。。");
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                num++;
            }
            return num;
        }
    }


    public static void main(String[] args) throws Exception {

        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());

        new Thread(futureTask).start(); //-----//

        Thread.sleep(1000);
        log.info("do something in main");

        //在Callable中的方法运行完之前 这里会一直阻塞，直到Callable运行完
        //然后就可以从future里获取到结果了
        Integer result = futureTask.get();
        log.info("result: {}", result);
    }
}
