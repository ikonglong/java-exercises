package generics;

import static java.lang.String.format;

public class WildcardGenerics {

  public static void main(String[] args) {
    WildcardGenerics.printBuddies(new Pair<>(new Employee("Peter"), new Employee("Bob")));
    // 下面这样代码骗过了编译器
    WildcardGenerics.printBuddies(new Pair<>(new Manager("Smith"), new Manager("Simon")));
    // Compile error
    // WildcardGenericType.printBuddies(new Pair<Manager>(new Manager("Smith"), new
    // Manager("Simon")));
    WildcardGenerics.printBuddies2(new Pair<>(new Manager("Smith"), new Manager("Simon")));

    Pair<Manager> managerBuddies = new Pair<>(new Manager("ceo"), new Manager("cfo"));
    Pair<? extends Manager> subtypeBoundBuddies2 = managerBuddies; // ok

    /**
     * `void setFirst(? extends Employee)` 方法是不可调用的，因为编译器只知道需要某个 Employee
     * 的子类型，但不知道具体是什么类型，所以它拒绝传递任何特定的类型，毕竟 ? 不能用来匹配。
     *
     * <p></>You cannot put anything into a type declared with an extends wildcard except for the
     * value null, which belongs to every reference type.
     * https://www.codejava.net/java-core/collections/generics-with-extends-and-super-wildcards-and-the-get-and-put-principle
     */

    // 是不是有些困惑，因为 Employee 和 Manager 对象都不能作为参数
    Pair<? extends Employee> subtypeBoundBuddies = managerBuddies; // ok
    // subtypeBoundBuddies.setFirst(new Employee("lowly-x")); // compile-time error
    // subtypeBoundBuddies.setFirst(new Manager("cho")); // compile-time error
    Employee first = subtypeBoundBuddies.first(); // ok

    /**
     * 带有超类型限定的通配符类型，可用于描述方法参数，但不可用于描述返回对象。
     *
     * <p>You cannot get anything out from a type declared with a super wildcard except for a value
     * of type Object, which is a super type of every reference type.
     */

    // 对于 `void setFirst(? super Manager)`，编译器不知道 setFirst 方法的确切类型，
    // 但是可以用任意的 Manager 或子类型的对象调用它，而不能用 Employee 对象调用。
    Pair<? super Manager> supertypeBoundBuddies = managerBuddies; // ok
    // supertypeBoundBuddies.setFirst(new Employee("lowlyX")); // compile-time error
    supertypeBoundBuddies.setFirst(new Manager("manager-x"));
    supertypeBoundBuddies.setFirst(new Executive("manager-executive"));
    // 对于 `? super Manager getFirst()`，如果被调用，返回的对象类型就不会得到保证，只能把它赋给一个 Object 引用
    // Manager manager = supertypeBoundBuddies.first(); // compile-time error
    Employee manager2 = supertypeBoundBuddies.first(); // ok

    // WildcardGenericType.printBuddies(supertypeBoundBuddies); // compile-time error
    WildcardGenerics.printBuddies2(supertypeBoundBuddies);
  }

  static void printBuddies(Pair<Employee> p) {
    System.out.println(format("%s and %s are buddies.", p.first, p.second));
  }

  static void printBuddies2(Pair<? extends Employee> p) {
    System.out.println(format("%s and %s are buddies.", p.first, p.second));
  }

  static class Pair<T extends Employee> {

    private T first;
    private T second;

    public Pair(T first, T second) {
      this.first = first;
      this.second = second;
    }

    void setFirst(T first) {
      this.first = first;
    }

    public T first() {
      return first;
    }

    public T second() {
      return second;
    }
  }
}
