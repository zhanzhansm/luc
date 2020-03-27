package org.hero.threadpool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cb= new CyclicBarrier(7,() ->{
            System.out.println("收集完毕");
        });
        for (int i = 0; i < 7; i++) {
            final int tempi= i;
            new Thread(() -> {
                System.out.println("开始"+tempi);
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}
