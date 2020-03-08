package com.nrsc.ch1.safeend;

/***
 * 如何安全中断线程 --- 继承Thread类的情况
 */
public class EndThread {

    private static class UseThread extends Thread {

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.err.println(threadName + " interrupt flag before=" + isInterrupted());
            //while(true){
            //while (!isInterrupted()) {
            while (!Thread.interrupted()) {

                System.out.println(threadName + " is running");
                System.out.println(threadName + "inner interrupt flag ="
                        + isInterrupted());
            }
            System.err.println(threadName + " interrupt flag after =" + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("endThread");
        endThread.start();
        Thread.sleep(1);
        endThread.interrupt();//中断线程，其实设置线程的标识位true
    }
}
