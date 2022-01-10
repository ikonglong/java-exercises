package leetcode;

import java.util.*;

public class ThreeSumV2 {
  public List<List<Integer>> threeSum(int[] nums) {
    if (nums == null || nums.length <= 2) return Collections.emptyList();

    Arrays.sort(nums);
    List<List<Integer>> result = new LinkedList<>();
    for (int i = 0; i < nums.length - 2; i++) {
      if (nums[i] > 0) break; // 如果三个数中的第一个大于 0，则在剩余数列中肯定不存在两个数与这个数的和为 0
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      twoSum(nums, i + 1, -nums[i], nums[i], result);
    }
    return result;
  }

  void twoSum(int[] nums, int start, int sum, int firstOne, List<List<Integer>> result) {
    int left = start;
    int right = nums.length - 1;
    do {
      int s = nums[left] + nums[right];
      if (s == sum) {
        List<Integer> triple = new ArrayList<>(3);
        triple.add(firstOne);
        triple.add(nums[left]);
        triple.add(nums[right]);
        result.add(triple);

        // 首先得增加 left，减小 right
        left++;
        right--;
        // 还要能去除重复，比如: [-2, -1, -1, -1, 3, 3, 3]
        // i = 0, left = 1, right = 6。需要排除重复的 [-2, -1, 3]
        while (left < right && nums[left] == nums[left - 1]) left++;
        while (left < right && nums[right] == nums[right + 1]) right--;
      } else if (s < sum) {
        left++;
      } else { // s > sum
        right--;
      }
    } while (left < right);
  }

  public static void main(String[] args) {
    ThreeSumV1 obj = new ThreeSumV1();
    for (List<Integer> l : obj.threeSum(new int[] {-1, 0, 1, 2, -1, -4})) {
      System.out.println(Arrays.toString(l.toArray()));
    }
    System.out.println("========================");
    for (List<Integer> l : obj.threeSum(new int[] {-1, 0, 1})) {
      System.out.println(Arrays.toString(l.toArray()));
    }

    ThreeSumV1.ThreeNum list1 = new ThreeSumV1.ThreeNum();
    list1.add(0);
    list1.add(-1);
    list1.add(1);
    ThreeSumV1.ThreeNum list2 = new ThreeSumV1.ThreeNum();
    list2.add(-1);
    list2.add(1);
    list2.add(0);
    System.out.println("list1 hashCode: " + list1.hashCode());
    System.out.println("list2 hashCode: " + list2.hashCode());
  }
}
