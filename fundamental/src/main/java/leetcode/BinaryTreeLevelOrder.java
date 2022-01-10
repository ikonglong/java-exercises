package leetcode;

import java.util.*;

/**
 * 二叉树层序遍历。广度优先搜索
 */
public class BinaryTreeLevelOrder {
  public static void main(String[] args) {
    //
  }

  static class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }

      List<List<Integer>> result = new LinkedList<>();
      Queue<TreeNode> que = new LinkedList<>();
      que.offer(root);
      while (!que.isEmpty()) {
        List<Integer> level = new ArrayList<>(que.size());
        // 这里巧妙的地方在于预先保存队列大小到 levelSize。控制循环的次数等于 levelSize。
        // 如果 for 循环写成这样就不对了：for (int i = 1; i <= que.size(); i++) { que.offer(n.left); }
        // 因为循环内会添加新元素到队列，再次检测循环条件 i <= que.size() 时 size 已经是最新大小了。
        // 写成这样也不对，原因同上：while((n = que.poll()) != null) { ... }
        int levelSize = que.size();
        for (int i = 1; i <= levelSize; i++) {
          TreeNode n = que.poll();
          level.add(n.val);
          if (n.left != null) {
            que.offer(n.left);
          }
          if (n.right != null) {
            que.offer(n.right);
          }
        }
        result.add(level);
      }
      return result;
    }
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
