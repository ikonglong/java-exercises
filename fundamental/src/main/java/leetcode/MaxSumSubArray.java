package leetcode;

public class MaxSumSubArray {
  public static void main(String[] args) {
    SolutionDP sDP = new SolutionDP();
    System.out.println(sDP.maxSubArray(new int[] {-1, -2, -3, -4, -5}));
    System.out.println(sDP.maxSubArray(new int[] {-1, -2, -3, 1, 3}));

    SolutionDC sDC = new SolutionDC();
    System.out.println(sDC.maxSubArray(new int[] {1, 2, 3, 0, 0, 0, 5, 4}));
  }

  // 动态规划
  static class SolutionDP {
    public int maxSubArray(int[] nums) {
      int prevSum = 0;
      int maxSum = nums[0];
      // int maxSum = 0; // Bug. 如果 nums 里全是负数，结果就不正确
      for (int x : nums) {
        //        if (prevSum + x > x) {
        //          prevSum = prevSum + x;
        //        } else {
        //          prevSum = x;
        //        }
        prevSum = Math.max(prevSum + x, x);
        maxSum = Math.max(maxSum, prevSum);
      }
      return maxSum;
    }
  }

  // 分治算法（divide-and-conquer）
  // 参考资源：
  // https://leetcode-cn.com/problems/maximum-subarray/solution/zui-da-zi-xu-he-by-leetcode-solution/
  // https://leetcode-cn.com/problems/maximum-subarray/solution/divide-and-conquer-fen-zhi-suan-fa-she-j-4sbv/
  static class SolutionDC {
    public int maxSubArray(int[] nums) {
      return maxSubArrayDC(nums, 0, nums.length - 1).maxSubSum;
    }

    Status maxSubArrayDC(int[] nums, int l, int r) {
      if (l == r) { // 递归基本条件：区间只包含一个元素
        return new Status(nums[l], nums[l], nums[l], nums[l]);
      }

      int m = (l + r) >> 1;
      Status leftSub = maxSubArrayDC(nums, l, m);
      Status rightSub = maxSubArrayDC(nums, m + 1, r);
      Status wholeStatus =
          new Status(
              leftSub.sum + rightSub.sum,
              // 这里不太好理解。处理的特殊情形是开始于当前区间左边界的最大子序列跨越了左、右两个子区间
              // 「跨越」在这里的意思是，左右两个子区间的这两个子序列的衔接部分不包含负数。注意，衔接部分
              // 全部是 0 也是跨越。反过来说，「不跨越」就是这两个子序列的衔接部分包含有负数
              Math.max(leftSub.startingAtLSum, leftSub.sum + rightSub.startingAtLSum),
              // 处理的特殊情形是结束于当前区间右边界的最大子序列跨越了左、右两个子区间
              Math.max(rightSub.endingAtRSum, leftSub.endingAtRSum + rightSub.sum),
              Math.max(
                  Math.max(leftSub.maxSubSum, rightSub.maxSubSum),
                  leftSub.endingAtRSum + rightSub.startingAtLSum));
      return wholeStatus;
    }

    static class Status {
      int sum; // 区间总和
      int startingAtLSum; // 开始于区间左边界的子序列的最大和
      int endingAtRSum; // 结束于区间右边界的子序列的最大和
      int maxSubSum; // 区间内子序列的最大和

      public Status(int sum, int startingAtLSum, int endingAtRSum, int maxSubSum) {
        this.sum = sum;
        this.startingAtLSum = startingAtLSum;
        this.endingAtRSum = endingAtRSum;
        this.maxSubSum = maxSubSum;
      }
    }
  }
}
