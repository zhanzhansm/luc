package org.hero.threadpool.main;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore =new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            final int tempi= i;
            new Thread(() -> {
                try {
                    Integer sleepTime=new Random().nextInt(5);
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(sleepTime);
                    System.out.println("我詹有位置了"+tempi);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            },String.valueOf(i)).start();
        }
    }
}
