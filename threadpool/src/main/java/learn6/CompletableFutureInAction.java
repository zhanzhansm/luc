package learn6;

import java.util.Random;
import java.util.concurrent.*;

/***************************************
 在future的功能上进行了增强CompletableFuture
 ***************************************/
public class CompletableFutureInAction {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //使用方式1. 工作的异步线程把结果放到CompletableFuture类中，通过调用future.complete(1000d)，获取
       /**\
        CompletableFuture<Double> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Thread.sleep(10000L);
                future.complete(1000d);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }

        }).start();

        System.out.println("..............");
        future.whenComplete((v, t) -> { //异步不堵塞 ，后执行
            System.out.println(v);
            t.printStackTrace();
        });
        System.out.println("先执行，在执行上面的东东");  //先执行

        ***/



        //执行方式2，上面的代码执行方式太不友好了，CompletableFuture直接来个异步方式，传入一个suppler函数，没有入参，只有返回值，
        //主线程的生命周期没有了，CompletableFuture他也就结束了


/**


        CompletableFuture<Double> future = CompletableFuture.supplyAsync(CompletableFutureInAction::get);
        future.whenComplete((v, t) -> {
            System.out.println(v);
            t.printStackTrace();
        });


         //也可以连在一起写，然后把处理结果在搞出来,调用get方法的时候会堵塞
         Double value = CompletableFuture.supplyAsync(CompletableFutureInAction::get)
         .whenComplete((v, t) -> System.out.println(">>>>" + v))
         .thenCompose(i -> CompletableFuture.supplyAsync(() -> i + 10))
         .get();
         System.out.println(value);

 */

        //执行方式3 lamda 表达式，直接用，改成多线程执行，ForkJoinPool.commonPool-worker-2" #12 daemon 默认是守护线程
        /**
        long start = System.currentTimeMillis();
        List<Double> doubles = Arrays.asList(random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble());
        System.out.println(doubles);
        List<CompletableFuture<Double>> futures = doubles
                .stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(10000L); //一个列表，每个都等待10秒，会疯的，用多线程解决，把执行的结果合并
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return d * 10d;
                }))
                .collect(toList());

        System.out.println("这里不会堵塞在执行，在遇见f.join() 这个东东，会堵塞，这个地方加.parallel() 不加都可以，因为" +
                "已经交给CompletableFuture来做了，打印虚拟机的线程池，可以看到多个线程同时工作");
        List<Double> collect = futures.stream().parallel().map(f -> f.join()).collect(toList());
        System.out.println(collect);
        System.out.println(start-System.currentTimeMillis());

         */


        //执行方式 表达守护线程的以上，调用线程main生命周期没了，守护线程的生命周期也就结束了
//        Executor executor = Executors.newFixedThreadPool(2, r -> {
//            Thread t = new Thread(r);
//            t.setDaemon(true);
//            return t;
//        });
//        executor.execute(() -> System.out.println("sfsdfsfs"));

    }

    private static double get() {
        try {
            Thread.sleep(10000L);
            double v = random.nextDouble();
            System.out.println(v);
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
