package leetcode;

public class SquareRoot {
  static class Solution {
    public int mySqrt(int x) {
      int l = 0, r = x, ans = -1;
      while (l <= r) {
        int mid = (l + r) / 2;
        // 若不转型为 long，x 为 2147395599 时，运行时间超出时间限制
        if ((long) mid * mid <= x) {
          // 先把 mid 作为答案暂存。下次循环可能得到一个更接近的答案，也可能结束循环。
          // 如果循环结束，就把最近的答案作为结果返回
          ans = mid;
          l = mid + 1;
        } else {
          r = mid - 1;
        }
      }
      return ans;
    }
  }

  static class SolutionV2 {
    public int mySqrt(int x) {
      if (x == 0) {
        return 0;
      }
      int ans = (int) Math.exp(0.5 * Math.log(x));
      // 找到平方值更接近 x 的那个
      // 注意，这里若不强制转型为 long，计算结果会不正确。input:2147483647, out:46341, expected:46340
      return (long) (ans + 1) * (ans + 1) <= x ? ans + 1 : ans;
    }
  }
}
