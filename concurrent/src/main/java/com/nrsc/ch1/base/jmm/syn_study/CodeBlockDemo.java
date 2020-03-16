package com.nrsc.ch1.base.jmm.syn_study;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

/***
 * synchronized修饰非静态代码块 ,无法修饰静态代码块
 */
public class CodeBlockDemo {

//////////////////////////////////////利用this当锁/////////////////////////////////
    private static class CodeBlockDemo1 implements Runnable {

        //共享资源
        static int i = 0;

        public void increase() {

            //利用this即当前对象当锁，锁住下面的代码块
            synchronized (this) {
                i++;
            }
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    @Test
    public void CodeBlockDemo1Test() throws InterruptedException {
        //注意: synchronized的锁使用的是this，即代码块所在的对象，因此
        //因此这里只能new 一个CodeBlockDemo1对象
        CodeBlockDemo1 codeBlockDemo1 = new CodeBlockDemo1();
        Thread t1 = new Thread(codeBlockDemo1);
        Thread t2 = new Thread(codeBlockDemo1);

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(codeBlockDemo1.i);
        System.out.println(CodeBlockDemo1.i);

    }
//////////////////////////////////利用this当锁/////////////////////////////////

///////////////////////////////利用某个对象的class当锁//////////////////////////
    /****
     * 利用某个对象的class类型作为锁
     */
    private static class CodeBlockDemo2 implements Runnable {

        //共享资源
        static int i = 0;

        public void increase() {

            //这可使用的锁就丰富了，本类的class对象、
            //甚至像我这样直接Integer对象的class对象都可以作为唯一锁
            synchronized (Integer.class) {
                i++;
            }
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    @Test
    public void CodeBlockDemo2Test() throws InterruptedException {
        //注意: synchronized的锁使用的是某个对象的class对象作为锁，class对象在JVM里都是唯一的
        //因此这里你想new一个CodeBlockDemo2对象，或者new两个都可以
        Thread t1 = new Thread(new CodeBlockDemo2());
        Thread t2 = new Thread(new CodeBlockDemo2());

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(CodeBlockDemo2.i);

    }
///////////////////////////////利用某个对象的class当锁//////////////////////////

//////////////////////////、////利用某个成员对象当锁1////////////////////////////

    /****
     * 利用某个成员对象作为锁 ---- 成员对象在对像里实例化的情况
     */
    private static class CodeBlockDemo3 implements Runnable {

        //某个成员对象
        private Object lock = new Object();

        //共享资源
        static int i = 0;

        public void increase() {

            //利用成员对象作为锁
            synchronized (lock) {
                i++;
            }
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    @Test
    public void CodeBlockDemo3Test() throws InterruptedException {
        //注意: synchronized的锁使用的是某个成员对象，
        //因此这里只能new 一个CodeBlockDemo3对象
        CodeBlockDemo3 codeBlockDemo3 = new CodeBlockDemo3();
        Thread t1 = new Thread(codeBlockDemo3);
        Thread t2 = new Thread(codeBlockDemo3);

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(CodeBlockDemo3.i);

    }
/////////////////////////////////////利用某个成员对象当锁1//////////////////////////


/////////////////////////////////////利用某个成员对象的当锁2////////////////////////

    /****
     * 利用某个成员对象作为锁 ---- new对象时传入锁的情况
     */
    @Setter
    @Getter
    private static class CodeBlockDemo4 implements Runnable {

        //某个成员对象
        private Object lock;

        //共享资源
        static int i = 0;

        public void increase() {

            //利用成员对象作为锁
            synchronized (lock) {
                i++;
            }
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    @Test
    public void CodeBlockDemo4Test() throws InterruptedException {
        //注意: synchronized的锁使用的是某个成员对象，
        //因此这里只能new 一个CodeBlockDemo3对象

        Object lock = new Object();

        CodeBlockDemo4 blockDemo4_1 = new CodeBlockDemo4();
        CodeBlockDemo4 blockDemo4_2 = new CodeBlockDemo4();
        blockDemo4_1.setLock(lock);
        blockDemo4_2.setLock(lock);
        Thread t1 = new Thread(blockDemo4_1);
        Thread t2 = new Thread(blockDemo4_2);

        t1.start();
        t2.start();

        //保证线程t1,t2执行完
        t1.join();
        t2.join();

        System.out.println(CodeBlockDemo4.i);

    }
/////////////////////////////////////利用某个成员对象的当锁2//////////////////////////

}
