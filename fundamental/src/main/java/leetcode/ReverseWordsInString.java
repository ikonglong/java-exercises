package leetcode;

import java.util.Stack;

public class ReverseWordsInString {
  public static void main(String[] args) {
    String s = "the sky is   blue";
    Solution so = new Solution();
    System.out.println(so.reverseWords(s));
    SolutionStack so2 = new SolutionStack();
    System.out.println(so2.reverseWords(s));
  }

  static class Solution {
    public String reverseWords(String s) {
      if (s == null || s.length() < 2) {
        return s;
      }

      StringBuilder sb = trimSpace(s);
      reverse(sb, 0, sb.length() - 1);
      reverseEachWord(sb);
      return sb.toString();
    }

    StringBuilder trimSpace(String s) {
      int left = 0, right = s.length() - 1;

      // 循环结束时，left 指向左边第一个非空字符
      while (left <= right && s.charAt(left) == ' ') {
        left++;
      }

      // 循环结束时，right 指向右第一个非空字符
      while (left <= right && s.charAt(right) == ' ') {
        right--;
      }

      StringBuilder sb = new StringBuilder(right - left + 1);
      // for (int i = left; i <= right; i++) {
      //   char c = s.charAt(i);
      while (left <= right) {
        char c = s.charAt(left);
        //        if (c == ' ') {
        //          if (sb.charAt(sb.length() - 1) != ' ') {
        //            sb.append(c);
        //          }
        //        } else {
        //          sb.append(c);
        //        }
        // 代码优化
        if (c != ' ') {
          sb.append(c);
        } else if (sb.charAt(sb.length() - 1) != ' ') {
          sb.append(c);
        }
        left++;
      }
      return sb;
    }

    void reverse(StringBuilder sb, int left, int right) {
      // int left = 0, right = sb.length() - 1;
      while (left < right) {
        char c = sb.charAt(left);
        sb.setCharAt(left++, sb.charAt(right));
        sb.setCharAt(right--, c);
      }
    }

    void reverseEachWord(StringBuilder sb) {
      int n = sb.length();
      int start = 0, end = 0;
      while (start < n) {
        // 遍历至单词的末尾
        while (end < n && sb.charAt(end) != ' ') {
          end++;
        }
        // 翻转单词
        reverse(sb, start, end - 1);
        // 移动 start 去查找下一个单词
        start = end + 1;
        ++end;
      }
    }
  }

  static class SolutionStack {
    public String reverseWords(String s) {
      if (s == null || s.length() < 2) {
        return s;
      }

      int left = 0, right = s.length() - 1;
      while (left <= right && s.charAt(left) == ' ') {
        ++left;
      }
      while (left <= right && s.charAt(right) == ' ') {
        --right;
      }

      Stack<StringBuilder> wordStack = new Stack<>();
      StringBuilder word = new StringBuilder(10);
      while (left <= right) {
        char c = s.charAt(left);
        if (c == ' ' && word.length() > 0) { // 连续多个空格如何处理
          wordStack.push(word);
          word = new StringBuilder(10);
        } else if (c != ' ') {
          word.append(c);
        }
        left++;
      }
      wordStack.push(word);

      StringBuilder res = new StringBuilder(wordStack.size() * 10);
      while (true) {
        res.append(wordStack.pop());
        if (wordStack.isEmpty()) {
          break;
        } else {
          res.append(" ");
        }
      }
      return res.toString();
    }
  }
}
