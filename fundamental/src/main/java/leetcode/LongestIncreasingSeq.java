package leetcode;

// 递增，但不要求连续（即差值总为 1）
// 最长上升/递增子序列，https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
public class LongestIncreasingSeq {
  static class SolutionDP {
    public int lengthOfLIS(int[] nums) {
      if (nums == null || nums.length == 0) {
        return 0;
      }
      int n = nums.length;
      // 存储 [0, i] 中以 nums[i] 结尾的最大递增子序列的长度
      int[] dp = new int[n];
      dp[0] = 1;
      int maxSeq = dp[0];
      for (int i = 1; i < n; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
          if (nums[i] > nums[j]) {
            dp[i] = Math.max(dp[i], dp[j] + 1);
          }
        }
        maxSeq = Math.max(maxSeq, dp[i]);
      }

      // 优化掉的逻辑
      //      int maxSeq = dp[0];
      //      for (int i = 1; i < n; i++) {
      //        maxSeq = Math.max(maxSeq, dp[i]);
      //      }

      return maxSeq;
    }
  }

  static class SolutionGreedy {
    public int lengthOfLIS(int[] nums) {
      if (nums == null || nums.length == 0) {
        return 0;
      }

      int n = nums.length;
      // tails[i] 就是长度为 i 的递增序列末尾元素的最小值
      // tails[i] 在 i 上是单调递增的。理解这点很重要，
      // 想想为什么 tails[i+1] 一定大于 tails[i]
      int[] tails = new int[n + 1];

      int maxLen = 1; // 当前最长递增序列的长度
      tails[maxLen] = nums[0];

      // 稍加推演就会发现，下面的程序填充 tails 数组时，总是从 1 到 n 逐项填充。
      // 也就是说，如果 tails[maxLen] 已填充，那么 tails[1..maxLen-1] 一定
      // 都已填入合适的值了。

      for (int i = 1; i < n; i++) {
        // 如果 nums[i] > 当前最长序列的末尾元素的最小值
        if (nums[i] > tails[maxLen]) {
          // 这里 maxLen 先自增 1，然后将 nums[i] 存入 tails[maxLen]。
          // 注意，将 nums[i] 存入了自增前的 maxLen 的下一个槽位
          tails[++maxLen] = nums[i];
        } else {
          // 二分查找 tails[i]，使得 tails[i-1] < nums[i] <= tails[i]
          // 这里写得很巧妙。二分查找时不断在较大的那一半查找满足 nums[i] > tails[x]
          // 的元素，这样找到的元素才是从 maxLen 位置向左第一个满足条件的元素。
          // 循环结束时，要么找到了（pos >= 1），要么未找到。
          // 未找到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
          int l = 1, r = maxLen, pos = 0;
          while (l <= r) {
            int mid = (l + r) >> 1;
            if (tails[mid] < nums[i]) {
              pos = mid;
              l = mid + 1;
            } else {
              r = mid - 1;
            }
          }
          tails[pos + 1] = nums[i];
        }
      }
      return maxLen;
    }
  }
}
