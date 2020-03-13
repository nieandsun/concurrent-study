package com.nrsc.ch1.base.jmm.problem;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/***
 * @author : Sun Chuan
 * @date : 2020/3/12 17:21
 * Description: 有序性问题
 */
@JCStressTest
/***
 * r.r1可能出现的结果
 */
@Outcome(id = {"1", "4"}, expect = Expect.ACCEPTABLE, desc = "ok")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "danger")
@State
public class OrderProblem1 {
    int num = 0;
    boolean ready = false;

    /***
     * 线程1 执行的代码
     * @param r
     */
    @Actor
    public void actor1(I_Result r) {
        if (ready) {
            r.r1 = num + num;
        } else {
            r.r1 = 1;
        }
    }

    /***
     * 线程2 执行的代码
     * @param r
     */
    @Actor
    public void actor2(I_Result r) {
        num = 2;
        ready = true;
    }
}
