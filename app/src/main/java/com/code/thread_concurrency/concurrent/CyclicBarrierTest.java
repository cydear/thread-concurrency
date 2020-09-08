package com.code.thread_concurrency.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CyclicBarrierTest
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 4:52 PM
 * @Version: 1.0
 * @Description: 可循环使用的屏障，执行屏障，当前线程阻塞，直到所有线程都到达屏障
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(Thread.currentThread() + " 执行完毕");
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread() + "执行完毕，count=>" + cyclicBarrier.getParties());
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setName("Thread-barrier-" + i);
            t.start();
        }
        try {
            cyclicBarrier.await();
            System.out.println("执行完毕，count=>" + cyclicBarrier.getParties());
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
