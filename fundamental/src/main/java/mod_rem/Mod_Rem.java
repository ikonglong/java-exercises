package mod_rem;

public class Mod_Rem {
  public static void main(String[] args) {
    System.out.println(3 / 2);
    System.out.println(3 % 2); // 直接舍去小数部分
    System.out.println(Math.floorMod(3, 2));
    System.out.println("---------------");
    System.out.println(-3 / 2);
    System.out.println(-3 % 2);
    System.out.println(Math.floorMod(-3, 2)); // 商是-2，余数是1
    System.out.println("---------------");
    System.out.println(3 / -2);
    System.out.println(3 % -2);
    System.out.println(Math.floorMod(3, -2)); // 商是-2，余数是1
  }
}
