package org.hero.threadpool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 * 1.main 线程 操作 资源类（资源类里面写具体的方法，类似于空调，里面有温度显示，和 增加 和 减少温度）
 * 2.判断  干活  通知
 * 3. 防止虚假唤醒机制
 */
class ShareClass {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+" " + num);
            //唤醒其他的线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+" " + num);
            //唤醒其他的线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


public class AwaitConditionDemo {
    public static void main(String[] args) {
        ShareClass shareClass = new ShareClass();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareClass.increment();
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareClass.decrement();
            }
        }, "BBB").start();
    }
}
