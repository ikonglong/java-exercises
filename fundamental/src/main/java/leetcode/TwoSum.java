package leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
  public int[] twoSum(int[] nums, int target) {
    int len = nums.length;
    Map<Integer, Integer> map = new HashMap<>(len - 1);
    map.put(nums[0], 0);
    for (int i = 1; i < len; i++) {
      int another = target - nums[i];
      if (map.containsKey(another)) {
        return new int[] {map.get(another), i};
      }
      map.put(nums[i], i);
    }
    throw new IllegalArgumentException("No two sum");
  }
}
