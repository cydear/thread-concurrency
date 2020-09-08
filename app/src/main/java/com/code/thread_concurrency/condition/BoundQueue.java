package com.code.thread_concurrency.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName BoundQueue
 * @Author: Lary.huang
 * @CreateDate: 2020/9/8 3:24 PM
 * @Version: 1.0
 * @Description: TODO
 */
public class BoundQueue<T> {
    private Object[] items;
    //添加下标，删除的下标和数组当前数量
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundQueue(int size) {
        items = new Object[size];
    }

    /**
     * 添加一个元素，如果数组满了，则添加线程进入等待状态，直到 空位
     *
     * @param t
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素，如果数组为空，则删除线程进入等待状态，直到添加新元素
     *
     * @return
     */
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object obj = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.notify();
            return (T) obj;
        } finally {
            lock.unlock();
        }
    }
}
