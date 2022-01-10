package leetcode;

import java.util.Arrays;

public class SubStrPermutationInclusion {
  public static void main(String[] args) {
    //    "ab"
    //    "eidboaoo"
    Solution s = new Solution();
    s.checkInclusion("ab", "eidboaoo");
  }

  // 滑动窗口
  static class Solution {
    public boolean checkInclusion(String s1, String s2) {
      if (s1 == null || s2 == null) {
        return false;
      }
      int n = s1.length(), m = s2.length();
      if (n > m) {
        return false;
      }

      int[] cnt1 = new int[26];
      int[] cnt2 = new int[26];
      for (int i = 0; i < n; i++) {
        ++cnt1[s1.charAt(i) - 'a'];
        ++cnt2[s2.charAt(i) - 'a'];
      }

      if (Arrays.equals(cnt1, cnt2)) {
        return true;
      }

      for (int i = n; i < s2.length(); i++) {
        int inIndex = s2.charAt(i) - 'a';
        int outIndex = s2.charAt(i - n) - 'a'; // 滑动窗口长度为 n，而非 26
        ++cnt2[inIndex]; // charAt(i) 滑入
        --cnt2[outIndex]; // charAt(i-n) 滑出
        // 只比较变化的部分可以吗？这将引入 bug，因为第一个 for 循环后很可能 cnt1 和 cnt2
        // 不同位置上的元素大于 0。如果只比较滑入和滑出的元素，这些位置上的值就没有参与比较
        // if (cnt1[inIndex] == cnt2[inIndex] && cnt1[outIndex] == cnt2[outIndex]) {
        //  return true;
        // }
        if (Arrays.equals(cnt1, cnt2)) {
          return true;
        }
      }
      return false;
    }
  }

  // 滑动窗口，优化
  static class SolutionV2 {
    public boolean checkInclusion(String s1, String s2) {
      if (s1 == null || s2 == null) {
        return false;
      }
      int n = s1.length(), m = s2.length();
      if (n > m) {
        return false;
      }

      int[] cnt = new int[26];
      for (int i = 0; i < n; i++) {
        --cnt[s1.charAt(i) - 'a'];
        ++cnt[s2.charAt(i) - 'a'];
      }

      int diffCnt = 0;
      for (int c : cnt) {
        if (c != 0) {
          diffCnt++;
        }
      }
      if (diffCnt == 0) {
        return true;
      }

      for (int i = n; i < s2.length(); i++) {
        int in = s2.charAt(i) - 'a';
        int out = s2.charAt(i - n) - 'a'; // 滑动窗口长度为 n，而非 26

        if (cnt[in] == 0) { // 如果 in 位置更新前是 0，则更新前 diff 计数中不包含该位置的不同
          ++diffCnt;
        }
        ++cnt[in];
        if (cnt[in] == 0) { // 如果更新前小于 0，更新后等于 0，则要计数要减一
          --diffCnt;
        }

        if (cnt[out] == 0) {
          ++diffCnt;
        }
        --cnt[out];
        if (cnt[out] == 0) { // 如果更新前大于 0，更新后等于 0，则要计数要减一
          --diffCnt;
        }

        if (diffCnt == 0) {
          return true;
        }
      }
      return false;
    }
  }

  // 双指针算法
  // 在保证 cnt 的值不为正的前提下，去考察是否存在一个区间，其长度恰好为 n。
  static class SolutionDualPtr {
    public boolean checkInclusion(String s1, String s2) {
      if (s1 == null || s2 == null) {
        return false;
      }
      int n = s1.length(), m = s2.length();
      if (n > m) {
        return false;
      }

      int[] cnt = new int[26]; // 保存子串中每个字符出现的次数，记为负数
      for (int i = 0; i < n; i++) {
        --cnt[s1.charAt(i) - 'a'];
      }

      // 初始时，left、right 都指向 0
      int left = 0;
      for (int right = 0; right < m; right++) {
        int x = s2.charAt(right) - 'a';
        ++cnt[x];
        while (cnt[x] > 0) {
          // 因为即将向右移动 left，所以先把 left 指向的字符的计数减一
          --cnt[s2.charAt(left) - 'a'];
          ++left;
        }
        if (right - left + 1 == n) {
          return true;
        }
      }
      return false;
    }
  }
}
