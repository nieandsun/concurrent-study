package com.nrsc.ch1.base.communication.multi;

/***
 * @author : Sun Chuan
 * @date : 2020/3/19 10:36
 * Description: 
 */
public class MultiTest {


    public static void main(String[] args) throws InterruptedException {

        BreadProducerAndConsumer2 pc = new BreadProducerAndConsumer2();

        /***
         * 不睡眠几秒，效果不是很好，
         * 因此我在
         *  生产者线程里睡了9秒 --- 因为我觉得生产面包的时间应该长 ☻☻☻
         *  消费者线程里睡了6秒  --- 因为我觉得买面包的时间应该快  ☻☻☻
         */

        //生产者线程
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                //每个线程都不停的生产
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.produceBread();
                }
            }, "生产者" + i).start();
        }


        //消费者线程
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                //每个线程都不停的消费
                while (true) {
                    try {
                        Thread.sleep(9);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pc.consumeBread();
                }
            }, "消费者" + i).start();
        }
    }

}
