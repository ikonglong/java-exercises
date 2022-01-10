package leetcode;

import java.util.List;

public class TriangleMinPath {
  static class SolutionDPV1 {
    public int minimumTotal(List<List<Integer>> triangle) {
      int n = triangle.size();
      // 动态规划数组，存储 f[i][j]，即从顶点走到 (i,j) 的路径的最小和
      int[][] f = new int[n][n];
      // 最小规模的问题解
      f[0][0] = triangle.get(0).get(0); // 0 行

      for (int i = 1; i < n; i++) {
        // i 表示行索引，i 行有 i+1 列。
        // 因此 i 行的列索引 j 取值范围为 [0, i]

        // j = 0 时，只能从 i-1 行的最左侧（即 0 列）走下来
        f[i][0] = f[i - 1][0] + triangle.get(i).get(0);

        // 从顶点出发，遍历所有的走法，i 行的每一个元素都会被走过吗？todo 尝试证明
        for (int j = 1; j < i; j++) {
          f[i][j] = Math.min(f[i - 1][j], f[i - 1][j - 1]) + triangle.get(i).get(j);
        }

        // j = i 时，只能从 i-1 行的最右侧（即 i-1 列）走下来
        f[i][i] = f[i - 1][i - 1] + triangle.get(i).get(i);
      }

      // 在矩阵的最后一行中找到全局最小的 f[i][j]
      int min = f[n - 1][0];
      for (int i = 1; i <= n - 1; i++) {
        min = Math.min(min, f[n - 1][i]);
      }
      return min;
    }
  }

  // 将空间复杂度优化至 O(n)
  // 计算 f[i][j] 只需要 f[i-1][..]，不需要 i-1 行之前的数据。因此考虑创建一个两行矩阵 f[2][n]，
  // 根据行索引 i 的奇偶性计算 i 行对应的矩阵行（用于保存当前行的计算结果），那么就从 i-1 行对应的矩阵行
  // 读取前一行的计算结果
  static class SolutionDPV2 {
    public int minimumTotal(List<List<Integer>> triangle) {
      int n = triangle.size();
      // 动态规划数组，存储 f[i][j]，即从顶点走到 (i,j) 的路径的最小和
      int[][] f = new int[2][n];
      // 最小规模的问题解
      f[0][0] = triangle.get(0).get(0); // 0 行

      for (int i = 1; i < n; i++) {
        int prev = (i - 1) % 2;
        int curr = i % 2;

        // i 表示行索引，i 行有 i+1 列。
        // 因此 i 行的列索引 j 取值范围为 [0, i]

        // j = 0 时，只能从 i-1 行的最左侧（即 0 列）走下来
        f[curr][0] = f[prev][0] + triangle.get(i).get(0);

        // 从顶点出发，遍历所有的走法，i 行的每一个元素都会被走过吗？todo 尝试证明
        for (int j = 1; j < i; j++) {
          f[curr][j] = Math.min(f[prev][j], f[prev][j - 1]) + triangle.get(i).get(j);
        }

        // j = i 时，只能从 i-1 行的最右侧（即 i-1 列）走下来
        f[curr][i] = f[prev][i - 1] + triangle.get(i).get(i);
      }

      // 在矩阵的最后一行中找到全局最小的 f[i][j]
      int lastRow = (n - 1) % 2;
      int min = f[lastRow][0];
      for (int i = 1; i <= n - 1; i++) {
        min = Math.min(min, f[lastRow][i]);
      }
      return min;
    }
  }

  // 虽然 V2 的空间复杂度优化到 O(n)，但它使用了 2n 的存储空间。可继续优化，只使用长度为 n 的一维数组。
  // 使用二维数组时，前一行的计算结果存储在 i-1 行的 [0, i-1] 范围，[i-1, n-1] 范围都为空。注意，i 行
  // 总是比 i-1 行多一列。遍历 i 行时，j 的取值范围是 [0, i]，若递减 j 就从索引 i 开始向前逐项覆盖，
  // 而计算 f[i][j] 所需的前一行的计算结果刚好保存在 [i-1, 0] 范围。这样在当前行上产生的计算结果和前一行
  // 已经就绪的结果就可以滚动保存在同一个一维数组中。
  static class SolutionDPV3 {
    public int minimumTotal(List<List<Integer>> triangle) {
      int n = triangle.size();
      // 动态规划数组，存储 f[i][j]，即从顶点走到 (i,j) 的路径的最小和
      int[] f = new int[n];
      // 最小规模的问题解
      f[0] = triangle.get(0).get(0); // 0 行

      for (int i = 1; i < n; i++) {
        // 注意，f[x] 中的 x 是列索引，没有行索引了
        // i 行有 i+1 列。因此 i 行的列索引 j 取值范围为 [0, i]

        // j = i 时，只能从 i-1 行的最右侧（即 i-1 列）走下来
        f[i] = f[i - 1] + triangle.get(i).get(i);

        // 从顶点出发，遍历所有的走法，i 行的每一个元素都会被走过吗？todo 尝试证明
        for (int j = i-1; j > 0; j--) {
          f[j] = Math.min(f[j], f[j - 1]) + triangle.get(i).get(j);
        }

        // j = 0 时，只能从 i-1 行的最左侧（即 0 列）走下来
        f[0] = f[0] + triangle.get(i).get(0);
      }

      // 在矩阵的最后一行中找到全局最小的 f[i][j]
      int min = f[0];
      for (int i = 1; i <= n - 1; i++) {
        min = Math.min(min, f[i]);
      }
      return min;
    }
  }
}
