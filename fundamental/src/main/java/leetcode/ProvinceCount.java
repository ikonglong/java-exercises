package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class ProvinceCount {
  public static void main(String[] args) {
    SolutionBfs bfs = new SolutionBfs();

    int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
    int count = bfs.findCircleNum(isConnected);
    System.out.println("count: " + count);

    int[][] isConnected2 = {{1, 1, 0, 0}, {1, 1, 0, 1}, {0, 0, 1, 0}, {0, 1, 0, 1}};
    count = bfs.findCircleNum(isConnected2);
    System.out.println("count: " + count);

    SolutionDfs dfs = new SolutionDfs();
    count = dfs.findCircleNum(isConnected);
    System.out.println("count: " + count);
    count = dfs.findCircleNum(isConnected2);
    System.out.println("count: " + count);
  }

  static class SolutionBfs {
    public int findCircleNum(int[][] isConnected) {
      int cityCount = isConnected.length;
      boolean[] visited = new boolean[cityCount];
      int provinceCount = 0;
      Queue<Integer> que = new LinkedList<>();
      for (int i = 0; i < cityCount; i++) {
        if (!visited[i]) {
          // 逐行/层遍历
          que.offer(i);
          while (!que.isEmpty()) {
            int n = que.poll();
            visited[n] = true;
            // 广度优先搜索，搜索与节点 n 直接连接的所有节点，并把它们放入队列
            for (int k = 0; k < cityCount; k++) {
              if (isConnected[n][k] == 1 && !visited[k]) {
                que.offer(k);
              }
            }
          }
          provinceCount++;
        }
      }
      return provinceCount;
    }
  }

  static class SolutionDfs {
    public int findCircleNum(int[][] isConnected) {
      int cityCount = isConnected.length;
      boolean[] visited = new boolean[cityCount];
      int circles = 0;
      for (int i = 0; i < cityCount; i++) {
        if (!visited[i]) {
          // 找到一个连通分量，就把其中所有的节点标记为已访问
          dfs(isConnected, visited, cityCount, i);
          circles++;
        }
      }
      return circles;
    }

    /**
     * 这里的深度优先搜索，逐行遍历矩阵。对于一行数据，逐列检测数据项是否为 1 （即 row i、column j 代表的城市之间直接连接）， 且列索引代表的城市是否被访问过。 若为真，则跳到
     * j 行继续上述过程（递归调用 dfs 时将 j 传递给形参 i）。
     *
     * <p>可以这么理解，row i 的一维数组表示城市 i 与每个城市的连接状态。遍历该数组，检测 城市 i 与城市 j 是否连接。若有连接且城市 j 未被访问过，则遍历 row
     * j，检测城市 j 与每个城市的连接状态。
     *
     * <p>把二维数组的行看作水平的广度。
     *
     * @param isConnected 邻接矩阵，表示城市之间是否有直接连接
     * @param visited 数组，记录城市是否被访问过
     * @param cityCount
     * @param i 表示城市编号，从 0 开始
     */
    void dfs(int[][] isConnected, boolean[] visited, int cityCount, int i) {
      for (int j = 0; j < cityCount; j++) {
        if (isConnected[i][j] == 1 && !visited[j]) {
          visited[j] = true;
          dfs(isConnected, visited, cityCount, j);
        }
      }
    }
  }
}
