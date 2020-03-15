package com.nrsc.ch1.base.falsesharing;

/**
 * 类说明：伪共享1
 */
public class FalseSharing1 implements Runnable {
    //获取CPU逻辑处理器的个数
    public final static int NUM_THREADS =
            Runtime.getRuntime().availableProcessors();
    //循环次数
    public final static long ITERATIONS = 500L * 1000L * 1000L;

    //long型数组的下标
    private final int arrayIndex;

    //构造函数，用来初始化arrayIndex
    public FalseSharing1(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }


    //内部类，里面有一个volatile修饰的变量
    // long padding避免false sharing
    // 按理说jdk7以后long padding应该被优化掉了，但是从测试结果看padding仍然起作用
    public final static class VolatileLongPadding {
       // public long p1, p2, p3, p4, p5, p6, p7;  //填充数据
        public volatile long value = 0L; //我们实际要用的数据
        volatile long q0, q1, q2, q3, q4, q5, q6; //填充数据
    }


//    //内部类，里面有一个volatile修饰的变量
//    // long padding避免false sharing
//    // 按理说jdk7以后long padding应该被优化掉了，但是从测试结果看padding仍然起作用
//    public final static class VolatileLongPadding {
//        public long p1, p2, p3, p4, p5, p6, p7;  //填充数据
//        public volatile long value = 0L; //我们实际要用的数据
//        //volatile long q0, q1, q2, q3, q4, q5, q6; //填充数据
//    }

    /*数组大小和CPU逻辑处理器的个数*/
    private static VolatileLongPadding[] longs = new VolatileLongPadding[NUM_THREADS];

    static {
        /*将数组初始化*/
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLongPadding();
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
            threads[i] = new Thread(new FalseSharing1(i));
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
