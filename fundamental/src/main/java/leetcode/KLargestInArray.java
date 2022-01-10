package leetcode;

import java.util.Random;

public class KLargestInArray {
  public static void main(String[] args) {
    int[] nums = {3, 2, 1, 5, 6, 4};
    //System.out.println(new SolutionV1().findKthLargest(nums, 2));
    System.out.println(new SolutionV2().findKthLargest(new int[] {1, 2, 3, 4, 5}, 4));
  }

  static class SolutionV1 {

    Random rand = new Random();

    public int findKthLargest(int[] nums, int k) {
      if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
        return -1;
      }
      return quickSelect(nums, 0, nums.length - 1, k);
    }

    int quickSelect(int[] nums, int left, int right, int k) {
      if (left == right) return nums[left];
      int pivotIndex = left + rand.nextInt(right - left + 1);
      int dividingIndex = partition(nums, left, right, pivotIndex);
      if (k - 1 == dividingIndex) {
        return nums[dividingIndex];
      }
      if (k - 1 < dividingIndex) {
        return quickSelect(nums, left, dividingIndex - 1, k);
      } else {
        return quickSelect(nums, dividingIndex + 1, right, k);
      }
    }

    /**
     * 以 pivotIndex 位置的值为轴值，并为该值寻找合适的位置，使得其前面的值都大于它，其后的值都小于等于它。
     *
     * <p>返回分区后轴值的位置
     */
    int partition(int[] nums, int left, int right, int pivotIndex) {
      int pivot = nums[pivotIndex];
      // 为什么要交换 pivotIndex 和 right 位置的元素呢？
      // 因为选择的是轴值，而非轴值的位置。最终的目标是使得轴值之前的元素都大于它，之后的元素都小于等于它。
      // 而轴值的当前位置不一定是最终位置。为了方便移动元素，先将轴值交换值序列末尾。
      // 例如，输入 {3, 1, 5, 9, 7, 8}，如果选择 5 为轴值，则需将 9,7,8 移动到 5 之前。可是 5 前面
      // 只有两个位置，容不下 9,7,8。如果先将 5 交换到序列末尾移动起来就方便多
      swap(nums, right, pivotIndex);

      // 计算 pivot 的最终位置，并将大于它的元素放在该位置的左边，小于等于的元素放在其右边。
      // 指针 p 总是指向「第一个小于等于轴值的」位置（初始时除外）。不管 p 如何，指针 i 总是向后移动。
      // 若在 p 之后找到了「大于等于轴值的」元素（即 i 指向的元素），则交换 p 和 i 位置的元素，
      // 并让 p 再次指向「第一个小于等于轴值的」位置（即指向下一个位置）。
      int p = left;
      for (int i = left; i < right; i++) {
        // 如何让指针 p 停留在第一个小于等于轴值的位置呢？
        // 如果直接检测 if (nums[i] <= pivot) { ... p = i; }，能找到小于等于轴值的位置，
        // 但是无法让 p 停留在第一个位置，因为无法识别哪个是第一个。
        // 这里采用的办法挺巧妙。在当前循环中检测相反的条件 nums[i] > pivot。若为真，就让 p 指向下一个位置。
        // 在下一次循环中，i 等于 p，即 i 和 p 都指向当前元素；如果 nums[i] > pivot 为假，则什么都不做，
        // 就实现了让 p 停留在第一个小于等于轴值的位置。
        if (nums[i] > pivot) {
          if (i > p) {
            swap(nums, p, i);
          }
          p++;
        }
      }
      // 将轴值交换到它的位置上
      swap(nums, p, right);
      return p;
    }

    void swap(int[] a, int i, int j) {
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
    }
  }

  static class SolutionV2 {
    Random random = new Random();

    public int findKthLargest(int[] nums, int k) {
      // `第 k 个最大的`，反过来说就是第 len-k+1 最小的。转换成数组索引，就是 len-k+1
      return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] a, int l, int r, int index) {
      // 跟 V1 对比，这里没有添加这条语句。但逻辑也是正确的。
      // if (left == right) return a[left];
      int q = randomPartition(a, l, r);
      if (q == index) {
        return a[q];
      } else {
        return q < index ? quickSelect(a, q + 1, r, index) : quickSelect(a, l, q - 1, index);
      }
    }

    public int randomPartition(int[] a, int l, int r) {
      int pivotIndex = random.nextInt(r - l + 1) + l;
      swap(a, pivotIndex, r);
      return partition(a, l, r);
    }

    public int partition(int[] a, int l, int r) {
      int pivot = a[r], i = l - 1; // i 指向最后一个小于等于 pivot 的位置
      for (int j = l; j < r; ++j) {
        if (a[j] <= pivot) {
          swap(a, ++i, j);
        }
      }
      // 因为 i 指向最后一个小于等于 pivot 的位置，所以这里要加 1
      swap(a, i + 1, r);
      return i + 1;
    }

    public void swap(int[] a, int i, int j) {
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
    }
  }
}
