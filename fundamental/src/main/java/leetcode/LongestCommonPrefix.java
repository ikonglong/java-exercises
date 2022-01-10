package leetcode;

public class LongestCommonPrefix {
  public String longestCommonPrefix(String[] strs) {
    String shortest = strs[0];
    for (String s : strs) {
      if (s.length() < shortest.length()) {
        shortest = s;
      }
    }

    StringBuilder commonPrefix = new StringBuilder(shortest.length());
    for (int i = 0; i < shortest.length(); i++) {
      boolean common = true;
      for (String s : strs) {
        if (s.charAt(i) != shortest.charAt(i)) {
          common = false;
          break;
        }
      }

      if (common) {
        commonPrefix.append(shortest.charAt(i));
      } else {
        break;
      }
    }
    return commonPrefix.toString();
  }
}
