package generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperTypeBoundDemo {

  public static void main(String[] args) {
    List<Number> numbers = new ArrayList<Number>();
    // append(numbers, 3); // Compile error, because Number is supertype of Integer.
    // How can we pass this collection of Numbers to the append() method above?

    // So what if we want to restrict the elements being added in the method is of type Integer,
    // whereas we also want to accept a collection of super types of Integer, since adding integers
    // to a collection of numbers is perfectly legal?
    append2(numbers, 3);

    List<Object> objects = new ArrayList<>();
    append2(objects, 3);
    objects.add("Four");
    System.out.println(objects);

    List<? super Manager> managers = new ArrayList<>();
    // managers.add(new Employee("Jack")); // Compile error
    // The type of the objects allowed to add is restricted to only Manager and its subtypes.
    managers.add(new Manager("Martin")); // Ok
    managers.add(new Executive("Bob")); // Ok
    // 跟 SubtypeBoundDemo 中编译失败的 add 操作进行对比
    System.out.println(managers);

    // You cannot get anything out from a type declared with a super wildcard except for a value of
    // type Object, which is a super type of every reference type.
    // 为什么不允许获取除 Object 类型之外的任何值呢？因为声明这个集合时使用的是 super wildcard，
    // 这样就无法确知实际从集合中获取的元素到底是什么类型，你有可能试图将一个 Double 对象赋值给一个 Integer 引用
    Object o = managers.get(0); // Ok
    //Manager m = managers.get(0); // Compile error
  }

  public static void append(Collection<Integer> integers, int n) {
    for (int i = 1; i <= n; i++) {
      integers.add(i);
    }
  }

  public static void append2(Collection<? super Integer> integers, int n) {
    for (int i = 1; i <= n; i++) {
      integers.add(i);
    }
  }
}
