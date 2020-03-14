package com.nrsc.ch1.base.jmm.volatile_study;

/***
 * @author : Sun Chuan
 * @date : 2020/3/11 18:36
 * Description: 可见性问题， 一个线程对共享变量的修改，另一个线程不能立即得到最新值
 */
public class VisibilityProblem {
    /***共享变量*/
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        //线程1
        new Thread(() -> {
            System.out.println("线程1开始");
            while (flag) {
            }
            System.err.println("线程1结束");
        }).start();

        //睡一秒，保证线程1先运行
        Thread.sleep(1000);

        //线程2
        new Thread(() -> {
            System.out.println("本线程将flag改为: " + flag);
            flag = false;
        }).start();
    }
}
