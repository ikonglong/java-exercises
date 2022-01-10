package leetcode;

public class TradeStocks {

  public static void main(String[] args) {
    //
  }

  static class Solution {
    public int maxProfit(int[] prices) {
      int maxProfit = 0;
      for (int i = 0; i < prices.length - 1; i++) {
        for (int j = i + 1; j < prices.length; j++) {
          int profit = prices[j] - prices[i];
          if (profit > maxProfit) {
            maxProfit = profit;
          }
        }
      }
      return maxProfit;
    }
  }

  static class Solution2 {
    public int maxProfit(int[] prices) {
      int maxProfit = 0;
      int minPrice = prices[0]; // 买入的价格
      for (int i = 1; i < prices.length; i++) {
        int price = prices[i];
        if (price < minPrice) {
          minPrice = price;
        } else if (price - minPrice > maxProfit) {
          maxProfit = price - minPrice;
        }
      }
      return maxProfit;
    }
  }
}
