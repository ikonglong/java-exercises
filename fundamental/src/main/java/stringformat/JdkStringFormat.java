package stringformat;

public class JdkStringFormat {

  public static void main(String[] args) {
    System.out.println(String.format("name: %s, title: %s", "Peter", "engineer", "x"));
    System.out.println(String.format("name: %s, title: %s", "Peter"));
  }
}
