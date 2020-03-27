package org.hero.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class ShareResource implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("call开始执行");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("call执行完毕");

        return 1024;
    }
}

public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask= new FutureTask<>(new ShareResource());
        new Thread(futureTask,"AAA").start();
        System.out.println("main在执行");
        Integer returnValue=futureTask.get();
        System.out.println(returnValue);

    }
}
