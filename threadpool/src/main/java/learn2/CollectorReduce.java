package learn2;

import learn.Dish;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/***************************************

 ***************************************/
public class CollectorReduce {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        //简单获取数量
        long count = menu.stream().filter(d -> d.isVegetarian()).count();

        //通过Collectors来获取
        Long collect = menu.stream().filter(d -> d.isVegetarian()).collect(Collectors.counting());

        //Integer::max 也是2个入参，一个返回值


        //public static int max(int a, int b)


        Optional<Integer> maxCalories = menu.stream().map(Dish::getCalories).reduce(Integer::max);
//        Optional<Integer> maxCalories10 = menu.stream().map(Dish::getCalories).reduce((e,k)->  {
//            return Integer.max(e,k);
//        });
        //通过函数 BinaryOperator 来搞定reduce
        List<Dish> menu1=null;

        //Strudent tearcher Houser tv
        //if else

       //List<Dish> dishes = Optional.ofNullable(Strudent).map(e-> e.tharr).map(e -> ee).orElse("dedoul").e
        Optional<Dish> maxCalories1 = menu.stream().reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2);
        maxCalories1.ifPresent(System.out::println);

        System.out.println("-----------1");

        Optional<Dish> maxCaloriesCollect = menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        maxCaloriesCollect.ifPresent(System.out::println);
        maxCaloriesCollect.ifPresent(System.out::println);


        //处理完后，在进行函数处理
        Integer collect1 = menu.stream().collect(Collectors.collectingAndThen(toList(), t -> t.size()));


        Map<Dish.Type, List<Dish>> collect2 = menu.stream().collect(Collectors.groupingBy(Dish::getType));

        Map<Dish.Type, Double> collect3 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));



    }
}
