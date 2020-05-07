package com.nrsc.ch4.threadlocal;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.io.IOException;

/***
 * 强引用：只要有引用在即使发生GC也回收不了
 */
public class StrongReferenceDemo {

    @ToString
    @AllArgsConstructor
    static class M {
        private String name;

        @Override
        protected void finalize() throws Throwable {
            System.out.println("m被回收了。。。");
        }
    }

    /***
     * 强引用：只要强引用还在，即使发生OOM，也无法通过GC回收堆中的具体对象
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        M m = new M("yoyo");

        //gc
        System.gc();
        System.err.println(m);

        System.in.read();
    }

    /***
     * 将强引用置为null，才可以将对象进行回收
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        M m = new M("yoyo");

        //gc
        System.gc();

        m = null;
        System.gc();
        System.in.read();
    }
}
