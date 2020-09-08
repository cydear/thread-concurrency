package com.code.thread_concurrency.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ExchangerTest
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 5:22 PM
 * @Version: 1.0
 * @Description: 线程间交换数据
 */
public class ExchangerTest {
    private static Exchanger<String> changer = new Exchanger<>();
    private static Executor executor = Executors.newFixedThreadPool(2);
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String A = "银行流水A";
                try {
                    changer.exchange(A);
                    System.out.println("A执行了exchange方法");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String B = "银行流水B";
                try {
                    TimeUnit.SECONDS.sleep(5);
                    String A = changer.exchange(B);
                    System.out.println("A和B数据是否一致:" + A.equals(B) + " , A的数据是=>" + A + " , B的数据是=>" + B);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
