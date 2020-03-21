package com.nrsc.ch1.base.communication.ABCABC;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author : Sun Chuan
 * @date : 2020/3/20 17:20
 * Description: 
 */
@Slf4j
@AllArgsConstructor
public class ABCABC implements Runnable {

    private String obj;
    //前一个线程需要释放，本线程需要wait的锁
    private Object prev;
    //本线程需要释放，下一个线程需要wait的锁
    private Object self;


    @Override
    public void run() {
        int i = 3;
        while (i > 0) {
            //为了在控制台好看到效果，我这里打印3轮
            synchronized (prev) { //抢前面线程的锁
                synchronized (self) {// 抢到自己应该释放的锁
                    System.out.println(obj);
                    i--;
                    self.notifyAll(); //唤醒其他线程抢self
                }//释放自己应该释放的锁

                try {
                    //走到这里本线程已经释放了自己应该释放的锁，接下来就需要让自己需要等待的锁进行等待就可以了
                    if (i > 0) { //我最开始没加这个条件，但是测试发现程序没停，其实分析一下就可以知道
                        //当前面i--使i=0了，其实该线程就已经完成3次打印了，就不需要再等前面的锁了
                        //因此这里加了该if判断
                        prev.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        Object lockC = new Object();
        //线程A需要等待C线程释放的锁，同时需要释放本线程该释放的锁A
        new Thread(new ABCABC("A", lockC, lockA)).start();
        Thread.sleep(1); //确保开始时A线程先执行

        //线程B需要等待A线程释放的锁，同时需要释放本线程该释放的锁B
        new Thread(new ABCABC("B", lockA, lockB)).start();
        Thread.sleep(1); //确保开始时B线程第2个执行

        //线程C需要等待B线程释放的锁，同时需要释放本线程该释放的锁C
        new Thread(new ABCABC("C", lockB, lockC)).start();

    }
}
