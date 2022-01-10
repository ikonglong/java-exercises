package leetcode;

public class TradeStocksV2 {
  public static void main(String[] args) {
    Solution1 s = new Solution1();
    //    System.out.println(s.maxProfit(new int[] {15, 4, 13, 1, 4, 6, 9}));
    //    System.out.println(s.maxProfit(new int[] {1, 2, 3, 4, 5}));
    System.out.println(s.maxProfit(new int[] {2, 4, 1}));
  }

  // 动态规划
  // 参考资源：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/tan-xin-suan-fa-by-liweiwei1419-2/
  static class SolutionDPV1 {
    public int maxProfit(int[] prices) {
      int total = 0;
      if (prices == null || prices.length < 2) {
        return total;
      }

      // 数组中的元素表示第 i 天状态为 j ，手上的现金
      int[][] dp = new int[prices.length][2];

      // 初始化第一行
      dp[0][0] = 0; // 第 0 天状态为持有现金，则手上现金为 0
      dp[0][1] = -prices[0]; // 第 0 天状态为持有股票，则手上现金为 -prices[0]

      for (int i = 1; i < prices.length; i++) {
        // 注意！
        // 持有现金、持有股票 是状态，而非要进行的操作
        // 今天状态为持有现金：
        //   若昨天状态为持有现金，则今天进行的操作是什么都不做
        //   若昨天状态为持有股票，则今天进行的操作是卖出股票
        // ------------
        // 今天状态为持有股票：
        //   若昨天状态为持有现金，则今天进行的操作是买入股票
        //   若昨天状态为持有股票，则今天进行的操作是什么都不做。
        dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
        dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
      }
      return dp[prices.length - 1][0];
    }
  }

  // 动态规划。将 V1 中的二维数组分成两个的一维数组
  static class SolutionDPV2 {
    public int maxProfit(int[] prices) {
      int total = 0;
      if (prices.length < 2) {
        return 0;
      }

      // cash 数组记录「状态为持有现金时」每天的现金
      int[] cash = new int[prices.length];
      // stock 数组记录「状态为持有股票时」每天的现金
      int[] stock = new int[prices.length];
      cash[0] = 0; // 第 0 天持有现金，现金为 0
      stock[0] = -prices[0]; // 第 0 天持有股票，现金为 -prices[0]

      for (int i = 1; i < prices.length; i++) {
        cash[i] = Math.max(cash[i - 1], stock[i - 1] + prices[i]);
        stock[i] = Math.max(cash[i - 1] - prices[i], stock[i - 1]);
      }
      return cash[prices.length - 1];
    }
  }

  // 动态规划。考虑优化空间复杂度。
  // 在 SolutionDPV1 中，for 循环处理当前行时，只需参考上一行，每一行就 2 个值，
  // 因此可以考虑使用「滚动变量」（「滚动数组」技巧）。
  static class SolutionDPV3 {
    public int maxProfit(int[] prices) {
      if (prices == null || prices.length < 2) {
        return 0;
      }

      // 简洁且可读性好
      // 当天的现金
      int cash = 0; // 第 0 天持有现金，现金为 0
      int stock = -prices[0]; // 第 0 天持有股票，现金为 -prices[0]

      // 前一天的现金。
      int preCash = cash; // 前一天持有现金时，手上的现金
      int preStock = stock; // 前一天持有股票是，手上的股票
      for (int i = 1; i < prices.length; i++) {
        cash = Math.max(preCash, preStock + prices[i]);
        stock = Math.max(preCash - prices[i], preStock);

        // 在下一次循环开始前，向前移动 prev 指针
        preCash = cash;
        preStock = stock;
      }

      // 更简洁，但可读性差
      // int cash = 0; // 第 0 天持有现金，现金为 0
      // int stock = -prices[0]; // 第 0 天持有股票，现金为 -prices[0]
      // for (int i = 1; i < prices.length; i++) {
      //   cash = Math.max(cash, stock + prices[i]);
      //   stock = Math.max(cash - prices[i], stock);
      // }

      return cash;
    }
  }

  // 把所有价格爬坡时的利润加在一起
  // 其实就是贪心算法
  static class Solution {
    public int maxProfit(int[] prices) {
      int total = 0;
      for (int i = 1; i < prices.length; i++) {
        //        if (prices[i] > prices[i - 1]) {
        //          total += (prices[i] - prices[i - 1]);
        //        }
        total += Math.max(0, prices[i] - prices[i - 1]);
      }
      return total;
    }
  }

  // 找到每一次价格爬坡的最低值，最高值，算出这次的最高利润，再累加
  // 结果正确，但实现比前一个版本复杂，且难以理解。不推荐
  static class Solution1 {
    public int maxProfit(int[] prices) {
      int total = 0;
      int minPrice = prices[0];
      for (int i = 1; i < prices.length; i++) {
        if (prices[i] <= prices[i - 1]) {
          total += prices[i - 1] - minPrice;
          minPrice = prices[i];
        } else if (i == prices.length - 1) {
          total += prices[i] - minPrice;
        }
      }
      return total;
    }
  }
}
