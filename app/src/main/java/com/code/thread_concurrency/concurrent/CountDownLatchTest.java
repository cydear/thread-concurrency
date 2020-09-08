package com.code.thread_concurrency.concurrent;

import android.util.TimeUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CountDownLatchTest
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 4:37 PM
 * @Version: 1.0
 * @Description: 等待多线程完成的CountDownLatch
 */
public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + "执行完毕");
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread() + "执行完成, count=>" + countDownLatch.getCount());
                }
            });
            t.setName("Thread-down-" + i);
            t.start();
        }
        try {
            countDownLatch.await();
            System.out.println("执行完成, count=>" + countDownLatch.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
