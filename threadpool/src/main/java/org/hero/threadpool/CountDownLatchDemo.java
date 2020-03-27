package org.hero.threadpool;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    private static  CountDownLatch cdl= new CountDownLatch(5);
    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            new Thread(() -> {
                System.out.println("执行中");
                cdl.countDown();
            },String.valueOf(i)).start();
        }
        //try{ cdl.await(); }catch (Exception e){ e.printStackTrace(); }
        try{ cdl.await(); }catch (Exception e){ e.printStackTrace(); }
        System.out.println("执行完毕");
    }
}
