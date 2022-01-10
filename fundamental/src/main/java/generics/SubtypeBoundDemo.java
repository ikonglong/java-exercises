package generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubtypeBoundDemo {

  public static void main(String[] args) {
    List<Integer> numbers = new ArrayList<>(5);
    numbers.add(1);
    numbers.add(5);
    numbers.add(3);

    // With this method signature, we can only pass List<Number>,
    // not List<Double> nor List<Integer>.
    // sum(numbers); // Compile error

    sum2(numbers); // Ok

    List<? extends Number> subtypeBoundList = new ArrayList<>(5);

    // An important rule you need to keep in mind about the extends wildcard is that you cannot add
    // elements to the collection declared with the extends wildcard.
    // --------
    // The compiler will throw an error! Otherwise, we could add a double number to a collection
    // which is designed to accept only integer numbers. You got this rule?
    // subtypeBoundList.add(1);

    // You cannot add anything into a type declared with an extends wildcard, except null which
    // belongs to every reference type
    // 为什么不允许放入除了 null 的任何值呢？因为声明这个集合时使用的是 extends wildcard，
    // 这样就无法确知实际的集合元素是什么类型，你有可能试图将一个 Double 对象放入一个 Integer List
    subtypeBoundList.add(null); // Ok
  }

  public static double sum(Collection<Number> numbers) {
    double result = 0.0;
    for (Number num : numbers) result += num.doubleValue();
    return result;
  }

  public static double sum2(Collection<? extends Number> numbers) {
    double result = 0.0;
    for (Number num : numbers) result += num.doubleValue();
    return result;
  }
}
