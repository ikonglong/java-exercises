package leetcode;

public class LongestContinuousIncreasingSeq {

  public static void main(String[] args) {
    SolutionDualPtr s = new SolutionDualPtr();
    System.out.println(s.findLengthOfLCIS(new int[] {1, 3, 5, 4, 7}));
  }

  // 动态规划算法
  static class SolutionDPV1 {
    public int findLengthOfLCIS(int[] nums) {
      if (nums == null || nums.length == 0) {
        return 0;
      }
      // 存储前 i 个元素中以 nums[i] 结尾的递增子序列的长度
      int[] dp = new int[nums.length];
      dp[0] = 1;
      int maxLIS = dp[0];
      for (int i = 1; i < nums.length; i++) {
        dp[i] = 1; // 先默认以 nums[i] 结尾的递增子序列的长度为 1
        if (nums[i] > nums[i - 1]) {
          dp[i] = dp[i - 1] + 1;
        }
        maxLIS = Math.max(maxLIS, dp[i]);
      }
      return maxLIS;
    }
  }

  // 优化。使用两个变量滚动代替 dp 数组
  static class SolutionDPV2 {
    public int findLengthOfLCIS(int[] nums) {
      if (nums == null || nums.length == 0) {
        return 0;
      }
      int prevDP = 1;
      int currDP = 0;
      int maxLIS = prevDP;
      for (int i = 1; i < nums.length; i++) {
        currDP = 1; // 先默认以 nums[i] 结尾的递增子序列的长度为 1
        if (nums[i] > nums[i - 1]) {
          currDP = prevDP + 1;
        }
        maxLIS = Math.max(maxLIS, currDP);
        prevDP = currDP;
      }
      return maxLIS;
    }
  }

  // 贪心算法
  // 参考：https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/solution/shuang-zhi-zhen-si-lu-qing-xi-dai-ma-jia-klgl/
  static class SolutionGreedy {
    public int findLengthOfLCIS(int[] nums) {
      int maxLIS = 0;
      int start = 0; // 递增子序列的开始索引
      // 循环内的逻辑确保了 [start, i] 总是一个递增子序列。
      // 每一次循环，要么让当前子序列长度加一，要么开始另一个子序列，并计算当前长度
      for (int i = 0; i < nums.length; i++) {
        if (i > 0 && nums[i] <= nums[i - 1]) {
          // Bad 按照我自己的想法，会在另一个序列开始的时候（就是这里）
          // 先计算前一个子序列的长度，再求 maxLIS。这个办法还有
          // 一个边界情形要处理好，就是假如最后一个子序列一直递增直到结束，
          // 程序就不会执行到这里，但仍需计算子序列的长度和 maxLIS。
          // 这样代码实现挺复杂且容易出错！
          start = i; // 另一个子序列开始的位置
        }

        // 跟我自己的想法相比，人家巧妙的地方在于，每一次循环都执行
        // 子序列长度和 maxLIS 的计算。
        // -----------------------------
        // 伪代码表示的逻辑：
        /*
        if (nums[i] <= nums[i-1]) {
          start = i;
        } else {
          i - start + 1 // 子序列长度加 1。实现比较隐晦，因为 i 递增了 1
          计算 maxLIS
        }
        */
        maxLIS = Math.max(maxLIS, i - start + 1);
      }
      return maxLIS;
    }
  }

  // 双指针算法
  // 参考：https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/solution/shuang-zhi-zhen-si-lu-qing-xi-dai-ma-jia-klgl/
  static class SolutionDualPtr {
    public int findLengthOfLCIS(int[] nums) {
      int maxSeq = 1;
      int left = 0, right = 1;
      // 外层循环负责将 left, right 移动到一个子序列的开头
      while (right < nums.length) {
        // 内层循环负责在一个子序列上移动 right 指针
        for (; right < nums.length && nums[right] > nums[right - 1]; right++) {}
        maxSeq = Math.max(maxSeq, right - left); // 不要加 1，因为 right 指向下一个序列的首元素了

        left = right;
        right++;
      }
      return maxSeq;
    }
  }
}
