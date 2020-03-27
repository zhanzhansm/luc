package learn2;

/***************************************
 * @author:Alex Wang
 * @Date:2016/10/29 QQ:532500648
 * QQ交流群:286081824
 ***************************************/

import learn.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static learn2.CollectorsAction.menu;

public class CollectorsAction3 {

//           new Dish("pork", false, 800, Dish.Type.MEAT),
//            new Dish("beef", false, 700, Dish.Type.MEAT),
//            new Dish("chicken", false, 400, Dish.Type.MEAT),
//            new Dish("french fries", true, 530, Dish.Type.OTHER),
//            new Dish("rice", true, 350, Dish.Type.OTHER),
//            new Dish("season fruit", true, 120, Dish.Type.OTHER),
//            new Dish("pizza", true, 550, Dish.Type.OTHER),
//            new Dish("prawns", false, 300, Dish.Type.FISH),
//            new Dish("salmon", false, 450, Dish.Type.FISH));
    public static void main(String[] args) {
//        testPartitioningByWith();
//        testPartitioningByWithAndCollector();
        testPartitioningByWithPredicateAndCollector();
        testPartitioningByWithPredicate();
        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentiy();
        testReducingBinaryOperatorAndIdentiyAndFunction();
        testSummarizingDouble();
        testSummarizingLong();
        testSummarizingInt();
    }

    private static void testPartitioningByWithPredicate() {
        System.out.println("testPartitioningByWithPredicate");

        Map<Boolean, List<Dish>> collect = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Optional.of(collect).ifPresent(System.out::println);
        System.out.println("---------------------");

    }


    private static void testPartitioningByWithPredicateAndCollector() {
        System.out.println("testPartitioningByWithPredicateAndCollector");
        Map<String, Double> collect = menu.stream()
                .collect(Collectors.groupingBy(Dish::getName, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(collect).ifPresent(System.out::println);

        System.out.println("---------------------11");

    }

    private static void testReducingBinaryOperator() {
        System.out.println("testReducingBinaryOperator");
        menu.stream().collect(
                Collectors.reducing(
                        BinaryOperator.maxBy(
                                Comparator.comparingInt(Dish::getCalories)
                        )
                )
        ).ifPresent(System.out::println);
    }

    //map 后再执行 reducing
    private static void testReducingBinaryOperatorAndIdentiy() {
        System.out.println("testReducingBinaryOperatorAndIdentiy");
        Integer result = menu.stream()
                .map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));

        System.out.println(result);
        System.out.println("---------------------22");

    }

    //reducing 函数 传入 Function，替代map
    private static void testReducingBinaryOperatorAndIdentiyAndFunction() {
        System.out.println("testReducingBinaryOperatorAndIdentiyAndFunction");
        Integer result = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    private static void testSummarizingDouble() {
        System.out.println("testSummarizingDouble");
        Optional.of(menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);

        System.out.println("---------------------33");

    }

    private static void testSummarizingLong() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testSummarizingInt() {
        System.out.println("testSummarizingLong");
        Optional.of(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }
}