package org.hero.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    private volatile Map<String,Object> map =new HashMap<String,Object>();
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();


    public void put(String key,Object value){
        System.out.println("线程"+Thread.currentThread().getName()+"正在写入key="+key);
        rwlock.writeLock().lock();
        try {
            TimeUnit.MILLISECONDS.sleep(500);  //写入是原子性的，所以睡眠下，等待 结果
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
        } catch (Exception e) {
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
//不加锁的结果
//        线程0正在写入key=0
//        线程1正在写入key=1
//        线程2正在写入key=2
//        线程3正在写入key=3
//        线程4正在写入key=4
//        线程2正在读取
//        线程3正在读取
//        线程0正在读取
//        线程1正在读取
//        线程4正在读取
//        线程2读取完成key=null
//        线程4读取完成key=null
//        线程3读取完成key=null
//        线程0读取完成key=null
//        线程1读取完成key=null
//        线程4完成写入key=4
//        线程3完成写入key=3
//        线程0完成写入key=0
//        线程2完成写入key=2
//        线程1完成写入key=1