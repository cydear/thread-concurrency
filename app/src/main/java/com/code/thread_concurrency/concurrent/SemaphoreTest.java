package com.code.thread_concurrency.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SemaphoreTest
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 5:32 PM
 * @Version: 1.0
 * @Description: 控制并发线程数量
 */
public class SemaphoreTest {
    /**
     * 最大允许并发的线程数
     */
    private static Semaphore semaphore = new Semaphore(10);
    /**
     * 线程池
     */
    private static ExecutorService executor = Executors.newFixedThreadPool(20);


    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread() + "" + finalI + " => 执行了");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread() + "" + finalI + " => 执行失败了");
                    }
                }
            });
        }
        executor.shutdown();
    }
}
