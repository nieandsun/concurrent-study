package com.nrsc.ch1.base;

import com.nrsc.utils.SleepUtils;

public class JoinStudy {

    static class LiuNeng implements Runnable {

        public void run() {
            System.out.println("LiuNeng开始排队打饭.....");
            SleepUtils.second(2);//休眠2秒
            System.out.println(Thread.currentThread().getName()
                    + " LiuNeng打饭完成.");
        }
    }

    static class ZhaoSi implements Runnable {

        public void run() {
            SleepUtils.second(2);//休眠2秒
            System.out.println("ZhaoSi开始排队打饭.....");
            System.out.println(Thread.currentThread().getName()
                    + " ZhaoSi打饭完成.");
        }
    }

    public static void main(String[] args) throws Exception {
        //for (int i = 0; i < 3; i++) {
            //创建赵四线程
            ZhaoSi zhaoSi = new ZhaoSi();
            Thread zsThread = new Thread(zhaoSi);

            //创建刘能线程
            LiuNeng liuNeng = new LiuNeng();
            Thread lnThread = new Thread(liuNeng);

            //开启刘能线程
            lnThread.start();
            //刘能开始join --- 注意，只有先开启了线程，join才有效
            lnThread.join();

            //开启赵四线程，并join
            zsThread.start();
            zsThread.join();

            System.err.println("谢广坤开始排队打饭.....");
            SleepUtils.second(2);//让主线程休眠2秒
            System.err.println(Thread.currentThread().getName() + " 谢广坤打饭完成.");
        }
   // }
}
