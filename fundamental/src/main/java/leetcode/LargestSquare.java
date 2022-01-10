package leetcode;

public class LargestSquare {
  public static void main(String[] args) {
    //
  }

  static class Solution {
    public int maximalSquare(char[][] matrix) {
      if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return 0;
      }

      int rows = matrix.length, columns = matrix[0].length;
      int maxSide = 0;
      // dp 保存以 (i,j) 为右下角的正方形的最大边长
      int[][] dp = new int[rows][columns];
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          if (matrix[i][j] == '1') {
            if (i == 0 || j == 0) { // 如果位于矩阵的上边和左边
              dp[i][j] = 1; // 单个块组成正方形
            } else {
              // dp[i][j] 等于位置相邻（左边，上边，左上方）的三个元素的 dp 值的最小值加一
              // 公式证明：https://leetcode-cn.com/problems/count-square-submatrices-with-all-ones/solution/tong-ji-quan-wei-1-de-zheng-fang-xing-zi-ju-zhen-2/
              dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
            }
            maxSide = Math.max(maxSide, dp[i][j]);
          }
        }
      }
      return maxSide * maxSide;
    }
  }

  // todo 暴力算法
}
