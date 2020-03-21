package com.nrsc.ch1.base.communication.multi;

import lombok.extern.slf4j.Slf4j;

/***
 * @author : Sun Chuan
 * @date : 2020/3/19 9:48
 * Description: 
 */
@Slf4j
public class BreadProducerAndConsumer2 {
    /***面包集合*/
    private int i = 0;

    /***
     * 生产者 ,注意这里锁是当前对象,即this
     */
    public synchronized void produceBread() {
        //如果大于等于20箱，就等待  --- 如果这里为大于20的话，则20不会进入while，则会生产出21箱，所以这里应为>=
        while (i >= 20) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.error("生产者{},等待出错", Thread.currentThread().getName(), e);
            }
        }

        //如果不到20箱就继续生产
        i++; //生产一箱
        log.warn("{}生产一箱面包，现有面包{}个", Thread.currentThread().getName(), i);
        //生产完,通知消费者进行消费
        this.notifyAll();
    }


    /***
     * 消费者
     */
    public synchronized void consumeBread() {

        //如果没有了就等待
        while (i <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.error("消费者{},等待出错", Thread.currentThread().getName(), e);
            }
        }
        //能走到这里说明i>0,所以进行消费
        i--; //消费一箱
        log.info("{}消费一个面包，现有面包{}个", Thread.currentThread().getName(), i);
        //消费完,通知生产者进行生产
        this.notifyAll();
    }
}
