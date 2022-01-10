package typecast;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
  public static void main(String[] args) {
//    Map map = new HashMap<>();
//    List<Employee> employees = Lists.newArrayList(new Manager());
//    map.put("managers", employees);
//    List<Manager> managers = (List<Manager>) map.get("managers");
//    for (Manager m : managers) {
//      System.out.println(m);
//    }
  }

  public static class Manager extends Employee {}
}
