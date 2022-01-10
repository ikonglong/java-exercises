package leetcode;

import java.util.*;

/** 二叉树锯齿层序遍历。 */
public class BinaryTreeZigzag {
  public static void main(String[] args) {
    TreeNode v15 = new TreeNode(15);
    TreeNode v7 = new TreeNode(7);
    TreeNode v20 = new TreeNode(20);
    v20.left = v15;
    v20.right = v7;
    TreeNode v9 = new TreeNode(9);
    TreeNode v3 = new TreeNode(3);
    v3.left = v9;
    v3.right = v20;
    Solution s = new Solution();
    s.zigzagLevelOrder(v3);
  }

  static class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }

      List<List<Integer>> result = new LinkedList<>();
      Queue<TreeNode> que = new LinkedList<>();
      que.offer(root);
      boolean lToR = true;
      while (!que.isEmpty()) {
        Deque<Integer> level = new LinkedList<>();
        int levelSize = que.size();
        for (int i = 0; i < levelSize; i++) {
          TreeNode n = que.poll();
          if (lToR) {
            level.offerLast(n.val);
          } else {
            level.offerFirst(n.val);
          }

          if (n.left != null) {
            que.offer(n.left);
          }
          if (n.right != null) {
            que.offer(n.right);
          }
        }
        result.add((List<Integer>) level);
        lToR = !lToR;
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
