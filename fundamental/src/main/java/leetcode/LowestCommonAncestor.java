package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LowestCommonAncestor {
  public static void main(String[] args) {
    //
  }

  // 先搞清楚一个问题，应该根据节点值还是节点引用判断是否是公共节点
  // 我觉得应该根据引用，而非值。否则就得要求输入的数据中不能有值相同的不同节点

  // 递归算法。根据引用判断是否是公共节点
  static class SolutionRecur {

    private TreeNode ans;

    public SolutionRecur() {
      this.ans = null;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      dfs(root, p, q);
      return this.ans;
    }

    /**
     * 整个函数难懂的地方在于，它有两个输出：<br>
     * 1. 传入的 tree 是否包含 p 或 q <br>
     * 2. 找到深度最深的公共祖先
     *
     * <p>为什么不是检测传入的 tree 是否包含 p 和 q 呢？ 因为有两种简单的 tree：null, one_node。<br>
     * 若 tree 只有一个节点，它只可能包含 p 或 q。 一个可能同时包含 p 和 q 的树，总是可以分解为只包含 p 或 q 的子树。
     */
    boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) return false;
      boolean lson = dfs(root.left, p, q);
      boolean rson = dfs(root.right, p, q);
      if ((lson && rson) || ((root == p || root == q) && (lson || rson))) {
        this.ans = root;
      }
      return lson || rson || (root == p || root == q);
    }
  }

  // 递归算法。根据节点值判断是否是公共节点，不推荐。
  @Deprecated
  static class SolutionRecur2 {

    private TreeNode ans;

    public SolutionRecur2() {
      this.ans = null;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      dfs(root, p, q);
      return this.ans;
    }

    /**
     * 整个函数难懂的地方在于，它有两个输出：<br>
     * 1. 传入的 tree 是否包含 p 或 q <br>
     * 2. 找到深度最深的公共祖先
     *
     * <p>为什么不是检测传入的 tree 是否包含 p 和 q 呢？ 因为有两种简单的 tree：null, one_node。<br>
     * 若 tree 只有一个节点，它只可能包含 p 或 q。 一个可能同时包含 p 和 q 的树，总是可以分解为只包含 p 或 q 的子树。
     */
    boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) return false;
      boolean lson = dfs(root.left, p, q);
      boolean rson = dfs(root.right, p, q);
      if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
        this.ans = root;
      }
      return lson || rson || (root.val == p.val || root.val == q.val);
    }
  }

  // 预存节点到父节点的映射
  static class SolutionByPrestoreParent {
    private Map<TreeNode, TreeNode> childToParent;

    public SolutionByPrestoreParent() {
      this.childToParent = new HashMap<>();
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) return null;

      childToParentByDfs(root);

      TreeNode px = p;
      Set<TreeNode> visited = new HashSet<>();
      while (px != null) {
        visited.add(px);
        px = childToParent.get(px);
      }

      px = q;
      while (px != null) {
        if (visited.contains(px)) {
          return px;
        }
        px = childToParent.get(px);
      }
      return null;
    }

    void childToParentByDfs(TreeNode root) {
      if (root.left != null) {
        childToParent.put(root.left, root);
        childToParentByDfs(root.left);
      }
      if (root.right != null) {
        childToParent.put(root.right, root);
        childToParentByDfs(root.right);
      }
      // root.left is null or root.right is null
    }
  }

  @Deprecated
  static class SolutionByPrestoreParent2 {
    private Map<Integer, TreeNode> childValToParent;

    public SolutionByPrestoreParent2() {
      this.childValToParent = new HashMap<>();
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null) return null;

      childToParentByDfs(root);

      TreeNode px = p;
      Set<Integer> visited = new HashSet<>();
      while (px != null) {
        visited.add(px.val);
        px = childValToParent.get(px.val);
      }

      px = q;
      while (px != null) {
        if (visited.contains(px.val)) {
          return px;
        }
        px = childValToParent.get(px.val);
      }
      return null;
    }

    void childToParentByDfs(TreeNode root) {
      if (root.left != null) {
        childValToParent.put(root.left.val, root);
        childToParentByDfs(root.left);
      }
      if (root.right != null) {
        childValToParent.put(root.right.val, root);
        childToParentByDfs(root.right);
      }
      // root.left is null or root.right is null
    }
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
