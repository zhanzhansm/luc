package learn6;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/13 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class CompletableFutureInAction4 {

    public static void main(String[] args) throws InterruptedException {

        //thenApply handle 2个参数一个带异常 一个不带异常。
        /*CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> Integer.sum(i, 10))
                .whenComplete((v, t) -> System.out.println(v));*/

/*        CompletableFuture.supplyAsync(() -> 1)
                .handle((v, t) -> Integer.sum(v, 10))
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(System.out::println);*/

//        CompletableFuture.supplyAsync(() -> 1)
//                .thenApply(i -> Integer.sum(i, 10))  //提供一个函数继续处理，有入参和返回值
//                .thenAccept(System.out::println);  //提供一个消费，有入参，没有返回值，结束

/**
 *
   //thenCompose Function<? super T, ? extends CompletionStage<U>> fn) 入参和返回的值，PECE 原则: Producer Extends, Consumer Super

        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> 10 * i))
                .thenAccept(System.out::println);
 */

// 2个异步的东西执行完毕后，在执行异步的操作
        /**

        CompletableFuture.supplyAsync(() -> 1)
                .thenCombine(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> r1 + r2) //需要提供一个函数
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0d), (r1, r2) -> { //需要提供一个消费者
                    System.out.println(r1);
                    System.out.println(r2);
                    System.out.println(r1 + r2);
                });

         */

        Thread.sleep(1000L);
    }
}
