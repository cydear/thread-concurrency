package com.code.thread_concurrency.concurrent;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BankWaterService
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 5:00 PM
 * @Version: 1.0
 * @Description: TODO
 */
public class BankWaterService implements Runnable {
    /**
     * 创建4个屏障，处理完成后执行当前类的run方法
     */
    private CyclicBarrier barrier = new CyclicBarrier(4);
    /**
     * 创建线程数量为4的线程池
     */
    private Executor executor = Executors.newFixedThreadPool(4);
    /**
     * 总sheet数量为4
     */
    private int sheetCount = 4;
    /**
     * 存储每个sheet计算的值
     */
    private ConcurrentHashMap<String, Integer> sheetCountMap = new ConcurrentHashMap<>();

    public void count() {
        for (int i = 0; i < sheetCount; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sheetCountMap.put(Thread.currentThread().getName(), 1);
                    try {
                        barrier.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> sheetEntry : sheetCountMap.entrySet()) {
            result += sheetEntry.getValue();
        }
        System.out.println("总和=>" + result);
    }

    public static void main(String[] args) {
        BankWaterService service = new BankWaterService();
        service.count();
        try {
            service.barrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(service).start();
    }
}
