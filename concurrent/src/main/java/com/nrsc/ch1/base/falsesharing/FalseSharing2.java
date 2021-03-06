package com.nrsc.ch1.base.falsesharing;

/**
 * 类说明：伪共享1
 */
public class FalseSharing2 implements Runnable {
    //获取CPU逻辑处理器的个数
    public final static int NUM_THREADS =
            Runtime.getRuntime().availableProcessors();
    //循环次数
    public final static long ITERATIONS = 500L * 1000L * 1000L;

    //long型数组的下标
    private final int arrayIndex;

    //构造函数，用来初始化arrayIndex
    public FalseSharing2(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }


    /**
     * jdk8新特性，Contended注解避免false sharing
     * Restricted on user classpath
     * VM options: -XX:-RestrictContended
     */
    @sun.misc.Contended
    public final static class VolatileLongAnno {
        public volatile long value = 0L;
    }

    /*数组大小和CPU逻辑处理器的个数*/
    private static VolatileLongAnno[] longs = new VolatileLongAnno[NUM_THREADS];

    static {
        /*将数组初始化*/
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLongAnno();
        }
    }


    /***
     * 本类为Runnable类，该方法为Runnable的run()方法--- 访问数组
     */
    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }


    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException {
        /*创建和CPU数相同的线程*/
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing2(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        /*等待所有线程执行完成*/
        for (Thread t : threads) {
            t.join();
        }
    }


}
