package com.nrsc.ch1.base.jmm.volatile_study;


/***
 * @author : Sun Chuan
 * @date : 2020/3/12 9:26
 * Description: 有序性问题 --- 很难复现。。。。
 */

public class OrderProblem {
    private static int x, y;
    private static volatile int a, b;
    private static int num;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            num++;
            x = 0;
            y = 0;

            Thread t1 = new Thread(() -> {
                a = x;
                y = 1;
            });


            Thread t2 = new Thread(() -> {
                b = y;
                x = 1;
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("a===" + a + ",b===" + b);

            if (a == 1 && b == 1) {
                System.err.println("num为:" + num);
                System.err.println("=======================");
                break;
            }
        }

    }
}
