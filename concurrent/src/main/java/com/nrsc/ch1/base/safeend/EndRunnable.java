package com.nrsc.ch1.base.safeend;

/**
 * 类说明：实现接口Runnable的线程如何中断
 */
public class EndRunnable {

    private static class UseRunnable implements Runnable {

        @Override
        public void run() {

            System.err.println(Thread.currentThread().getName()
                    + " interrupt flag before ==" + Thread.currentThread().isInterrupted());

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName()
                        + " I am implements Runnable.==" + Thread.currentThread().isInterrupted());
            }
            System.err.println(Thread.currentThread().getName()
                    + " interrupt flag after ==" + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRunnable useRunnable = new UseRunnable();
        Thread endThread = new Thread(useRunnable, "endThread");
        endThread.start();
        Thread.sleep(20);
        endThread.interrupt();
    }

}
