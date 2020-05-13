package com.nrsc.ch6.tp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolDemo {
    /***
     * 自定义拒绝策略
     */
    RejectedExecutionHandler selfRejectedHandler = (r, pool) -> {
        //我们的项目，如果将log的级别配置为warn或error级别就会发邮件+微信进行通知
        //其实线程池的阻塞队列一般都会配的相对比较大，所以基本不会让任务被拒绝掉 -- 自己的经验
        log.warn("方法{}被线程池{}拒绝了,请做及时补偿处理", r.toString(), pool);
    };


    //定义线程组名称，在jstack问题排查时，非常有帮助
    private final String namePrefix = "Contract==";
    private final AtomicInteger nextId = new AtomicInteger(1);

    /***
     * 自定义线程工厂
     */
    ThreadFactory threadFactory = (r) -> {
        Thread thread = new Thread(r);
        thread.setName(namePrefix + nextId.getAndIncrement());
        return thread;
    };

    /***
     * 使用自定义的线程工厂 和 拒绝策略
     * 实际生产中变量一般写在配置文件里
     */
    ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(
            3, 5, 3, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3),
            threadFactory,
            selfRejectedHandler);


    /***
     *  使用JDK提供的线程工厂 和 拒绝策略
     */
    ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(
            3, 5, 3, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    /***
     * 自定义的线程工厂 和 拒绝策略 测试
     */
    @Test
    public void test1() {
        for (int i = 1; i <= 10; i++) {
            threadPoolExecutor1.execute(() -> {
                log.info("当前线程为{}", Thread.currentThread().getName());
            });
        }
    }

    /***
     * 使用JDK提供的线程工厂 和 拒绝策略
     */
    @Test
    public void test2() {
        for (int i = 1; i <= 10; i++) {
            threadPoolExecutor2.execute(() -> {
                log.info("当前线程为{}", Thread.currentThread().getName());
            });
        }
    }
}
