package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MergeRange {
  public static void main(String[] args) {
    int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
    SolutionV2 s = new SolutionV2();
    int[][] res = s.merge(intervals);
    System.out.println(res);
  }

  static class Solution {
    public int[][] merge(int[][] intervals) {
      if (intervals == null || intervals.length == 0) {
        return new int[0][2];
      }
      Arrays.sort(
          intervals,
          (o1, o2) -> {
            if (o1[0] == o2[0]) {
              return o1[1] - o2[1];
            } else {
              return o1[0] - o2[0];
            }
          });

      List<int[]> merged = new LinkedList<>();
      // i 是左指针，j 是右指针，upper 是目前已合并的区间的上界
      for (int i = 0; i < intervals.length; ) { // 注意，不能给 for 添加 i++，因为循环内部更新了 i
        int upper = intervals[i][1];
        int j = i + 1;
        while (j < intervals.length && intervals[j][0] <= upper) {
          upper = Math.max(upper, intervals[j][1]);
          j++;
        }
        merged.add(new int[] {intervals[i][0], upper});
        i = j; // 移动左指针到下一个区间的开始位置
      }
      return merged.toArray(new int[merged.size()][2]);
    }
  }

  static class SolutionV2 {
    public int[][] merge(int[][] intervals) {
      if (intervals == null || intervals.length == 0) {
        return new int[0][2];
      }
      Arrays.sort(
          intervals,
          (o1, o2) -> {
            if (o1[0] != o2[0]) {
              return o1[0] - o2[0];
            } else {
              return o1[1] - o2[1];
            }
          });
      List<int[]> merged = new LinkedList<>();
      for (int i = 0; i < intervals.length; i++) {
        int[] interval = intervals[i];
        if (merged.isEmpty() || interval[0] > merged.get(merged.size() - 1)[1]) {
          merged.add(interval);
        } else {
          merged.get(merged.size() - 1)[1] =
              Math.max(merged.get(merged.size() - 1)[1], interval[1]);
        }
      }
      return merged.toArray(new int[merged.size()][2]);
    }
  }
}
