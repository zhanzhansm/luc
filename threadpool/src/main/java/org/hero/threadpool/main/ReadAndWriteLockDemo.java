package org.hero.threadpool.main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String,Object> map =new HashMap<String,Object>();
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();


    public void put(String key,Object value){
        System.out.println("线程"+Thread.currentThread().getName()+"正在写入key="+key);
        rwlock.writeLock().lock();
        try {
            TimeUnit.MILLISECONDS.sleep(300);  //写入是原子性的，所以睡眠下，等待 结果
            map.put(key,value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwlock.writeLock().unlock();
        }
        System.out.println("线程"+Thread.currentThread().getName()+"完成写入key="+key);
    }

    public Object get(String key){
        System.out.println("线程"+Thread.currentThread().getName()+"正在读取");
        rwlock.readLock().lock();
        try {
            TimeUnit.MILLISECONDS.sleep(300);  //写入是原子性的，所以睡眠下，等待 结果
            Object object= map.get(key);
            System.out.println("线程"+Thread.currentThread().getName()+"读取完成key="+object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwlock.readLock().unlock();
        }
        return null;
    }
}

public class ReadAndWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache =new MyCache();
        //写入
        for (int i = 0; i < 5; i++) {
            final  int tempi=i;
            new Thread(() -> {
                myCache.put(tempi+"",tempi+"");
            },String.valueOf(i)).start();
        }
        //读取
        for (int i = 0; i < 5; i++) {
            final  int tempi=i;
            new Thread(() -> {
                myCache.get(tempi+"");
            },String.valueOf(i)).start();
        }

    }
}
