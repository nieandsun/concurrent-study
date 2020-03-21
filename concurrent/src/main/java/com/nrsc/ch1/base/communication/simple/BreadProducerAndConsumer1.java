package com.nrsc.ch1.base.communication.simple;

import lombok.extern.slf4j.Slf4j;

/***
 * @author : Sun Chuan
 * @date : 2020/3/19 9:48
 * Description: 
 */
@Slf4j
public class BreadProducerAndConsumer1 {
    /***面包集合*/
    private int i = 0;

    /***
     * 生产者 ,注意这里锁是当前对象,即this
     */
    public synchronized void produceBread() {
        while (i >= 20) {
            //如果有就等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.error("生产者等待出错", e);
            }
        }

        //如果没有,就生产
        i++; //生产一个
        log.warn("{}生产一个面包，现有面包{}个", Thread.currentThread().getName(), i);
        //生产完,通知消费者进行消费
        this.notifyAll();
    }


    /***
     * 消费者
     */
    public synchronized void consumeBread() {

        //如果没有就等待
        while (i <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.error("消费者等待出错", e);
            }
        }
        i--; //消费一个
        log.info("{}消费一个面包，现有面包{}个", Thread.currentThread().getName(), i);
        //消费完,通知生产者进行生产
        this.notifyAll();

    }
}
