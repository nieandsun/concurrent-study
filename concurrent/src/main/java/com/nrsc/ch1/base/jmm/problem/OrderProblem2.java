package com.nrsc.ch1.base.jmm.problem;


import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;
import org.openjdk.jcstress.infra.results.I_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

/***
 * @author : Sun Chuan
 * @date : 2020/3/12 9:26
 * Description: 有序性问题
 */

@JCStressTest
/***
 * r.r1 和 r.r2可能出现的结果
 */
@Outcome(id = {"0, 1", "1, 0","1,1"}, expect = ACCEPTABLE, desc = "ok")
@Outcome(id = "0, 0", expect = ACCEPTABLE_INTERESTING, desc = "danger")
@State
public class OrderProblem2 {

    int x, y;

    /****
     * 线程1 执行的代码
     * @param r
     */
    @Actor
    public void actor1(II_Result r) {
        x = 1;
        r.r2 = y;
    }


    /****
     * 线程2 执行的代码
     * @param r
     */
    @Actor
    public void actor2(II_Result r) {
        y = 1;
        r.r1 = x;
    }
}
