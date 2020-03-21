package com.nrsc.ch1.base.communication.simple;

/***
 * @author : Sun Chuan
 * @date : 2020/3/19 10:16
 * Description: 
 */
public class SimpleTest {

    public static void main(String[] args) {

        BreadProducerAndConsumer1 pc = new BreadProducerAndConsumer1();

        /***生产者线程*/
        new Thread(() -> {
            while (true) {
                pc.produceBread();
            }
        }).start();

        /***消费者线程*/
        new Thread(() -> {
            while (true) {
                pc.consumeBread();
            }
        }).start();
    }
}
