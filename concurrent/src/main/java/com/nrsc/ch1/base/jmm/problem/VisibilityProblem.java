package com.nrsc.ch1.base.jmm.problem;

/***
 * @author : Sun Chuan
 * @date : 2020/3/11 18:36
 * Description: 可见性问题， 一个线程对共享变量的修改，另一个线程不能立即得到最新值
 */
public class VisibilityProblem {
    /***共享变量*/
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        //线程1
        new Thread(() -> {
            while (flag) {
            }
        }).start();

        //睡一秒，保证线程1先运行
        Thread.sleep(1000);

        //线程2
        new Thread(() -> {
            flag = false;
            System.err.println("本线程已经将flag改为了: " + flag);
        }).start();
    }
}
