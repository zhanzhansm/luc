package learn5;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/***************************************
 * @author:Alex Wang
 * @Date:2016/11/7 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class FutureInAction {
    public static void main(String[] args) throws InterruptedException {


        /**
         * 直接调接口，阻塞住了
         */
        String value = block(() -> {
            try {
                Thread.sleep(10000);
                return "I am finished.";
            } catch (InterruptedException e) {
                return "Error";
            }
        });
        System.out.println(value);

        /**
         * 多线程中的未来模式，发实现的方法交给另外一个线程处理，处理完毕后放到一个变量里面，并设置标识符，设计2个线程的通信，所以使用
         * AtomicBoolean，AtomicBoolean
         */
//       Future<String> future = invoke(() -> {
//            try {
//                Thread.sleep(5000);
//                return "I am finished.";
//            } catch (InterruptedException e) {
//                return "Error";
//            }
//        });
//        System.out.println(future.get());
//        System.out.println(future.get());
//        System.out.println(future.get());
//        //.....
//        //....
//        while (!future.isDone()) {
//            Thread.sleep(10);
//        }
//        System.out.println(future.get());

    }

    /**
     * 定义一个泛型方法，为了封装接口，返回接口的方法
     * @param callable
     * @param <T>
     * @return
     */
    private static <T> T block(Callable<T> callable) {
        return callable.action();
    }

    private static <T> Future<T> invoke(Callable<T> callable) {

        AtomicReference<T> result = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            T value = callable.action();
            result.set(value);
            finished.set(true);
        });
        t.start();

        Future<T> future = new Future<T>() {
            @Override
            public T get() {
                return result.get();
            }

            @Override
            public boolean isDone() {
                return finished.get();
            }
        };

        return future;
    }


    /**
     * 把结果放到泛型T里面
     * @param <T>
     */
    private interface Future<T> {

        T get();

        boolean isDone();
    }

    /**
     * 要做的事，返回的结果
     * @param <T>
     */
    private interface Callable<T> {
        T action();
    }
}
