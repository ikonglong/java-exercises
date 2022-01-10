package interview;

public class StrPermutation {
  public static void main(String[] args) {
    System.out.println("substring(0, 0): " + "abc".substring(0, 0));
    System.out.println("substring(1, 1): " + "abc".substring(1, 1));
  }

  static void permutation(String str) {
    permutation(str, "");
  }

  static void permutation(String str, String prefix) {
    if (str.length() == 0) {
      System.out.println(prefix);
    } else {
      for (int i = 0; i < str.length(); i++) {
        String rem = str.substring(0, i) + str.substring(i + 1);
        permutation(rem, prefix + str.charAt(i));
      }
    }
  }
}
