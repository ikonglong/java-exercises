package leetcode;

import java.util.*;

public class ThreeSumV1 {
  public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> result = new HashSet<>();
    for (int i = 0; i < nums.length - 2; i++) {
      twoSum(nums, i + 1, 0 - nums[i], nums[i], result);
    }
    return new ArrayList<>(result);
  }

  public void twoSum(int[] nums, int start, int target, int firstNum, Set<List<Integer>> result) {
    int subArrayLen = nums.length - start;
    Map<Integer, Integer> map = new HashMap<>(subArrayLen - 1);
    map.put(nums[start], start);
    for (int i = start + 1; i < nums.length; i++) {
      int another = target - nums[i];
      if (map.containsKey(another)) {
        List<Integer> triple = new ThreeNum();
        triple.add(firstNum);
        triple.add(another);
        triple.add(nums[i]);
        result.add(triple);
      }
      map.put(nums[i], i);
    }
  }

  static class ThreeNum extends ArrayList<Integer> {
    ThreeNum() {
      super(3);
    }

    @Override
    public boolean equals(Object that) {
      if (that == null) return false;
      if (!(that instanceof ThreeNum)) return false;
      ThreeNum thatObj = (ThreeNum) that;
      return this.contains(thatObj.get(0)) && this.contains(thatObj.get(1));
    }

    @Override
    public int hashCode() {
      this.sort((o1, o2) -> o1 - o2);
      return super.hashCode();
    }
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

    ThreeNum list1 = new ThreeNum();
    list1.add(0);
    list1.add(-1);
    list1.add(1);
    ThreeNum list2 = new ThreeNum();
    list2.add(-1);
    list2.add(1);
    list2.add(0);
    System.out.println("list1 hashCode: " + list1.hashCode());
    System.out.println("list2 hashCode: " + list2.hashCode());
  }
}
