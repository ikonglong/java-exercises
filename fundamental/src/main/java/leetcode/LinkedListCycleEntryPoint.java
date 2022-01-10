package leetcode;

import java.util.HashSet;
import java.util.Set;

public class LinkedListCycleEntryPoint {
  public static void main(String[] args) {
    // [3,2,0,-4], entry point index: 1
    ListNode aN4 = new ListNode(-4);
    ListNode aN3 = new ListNode(0, aN4);
    ListNode aN2 = new ListNode(2, aN3);
    ListNode aN1 = new ListNode(3, aN2);
    aN4.next = aN2;
    SolutionBug solution1 = new SolutionBug();
    System.out.println(solution1.detectCycle(aN1));
  }

  // 动手前需要先想清楚的问题：
  // 1. 节点的标识符是什么？节点值能作为标识符吗？返回什么作为入口节点的标识符
  // 2. 当再次走到入口节点时，如何判断是再次？

  // 快慢指针
  static class Solution1 {
    public ListNode detectCycle(ListNode head) {
      ListNode slow = head, fast = head;
      while (fast != null) {
        slow = slow.next;
        if (fast.next != null) {
          fast = fast.next.next;
        } else {
          return null;
        }

        if (slow == fast) {
          ListNode p = head;
          while (p != slow) {
            p = p.next;
            slow = slow.next;
          }
          return p;
        }
      }
      return null;
    }
  }

  // 快慢指针
  static class SolutionBug {
    public ListNode detectCycle(ListNode head) {
      ListNode slow = head, fast = head;
      // slow != fast 条件放在 while 条件中是 bug，
      // head 不同就直接退出循环了
      while (slow != null && fast != null && slow != fast) {
        slow = slow.next;
        fast = fast.next;
        if (fast != null) {
          fast = fast.next;
        }
      }

      if (fast == null) { // 无环
        return fast;
      } else { // 有环
        ListNode p = head;
        // p 和 slow 相遇的点就是入环点
        while (p != slow) {
          p = p.next;
          slow = slow.next;
        }
        return p;
      }
    }
  }

  static class Solution2 {
    public ListNode detectCycle(ListNode head) {
      Set<ListNode> visited = new HashSet<>();
      ListNode pos = head;
      while (pos != null) {
        if (visited.contains(pos)) {
          return pos; // 找到了环的入口点
        } else {
          visited.add(pos);
        }
        pos = pos.next;
      }
      return null;
    }
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }

    public String toString() {
      return "v:" + val + ", next:" + next.val;
    }
  }
}
