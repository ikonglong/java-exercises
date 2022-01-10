package leetcode;

public class MergeTwoSortedList {
  public static void main(String[] args) {}

  // 使用循环替代递归
  static class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      // 设定一个哨兵节点，它的下一个节点是合并后的链表的头节点，方便我们返回
      ListNode preHead = new ListNode(-1);

      ListNode prev = preHead;
      ListNode lA = l1;
      ListNode lB = l2;
      while (lA != null && lB != null) {
        if (lA.val < lB.val) {
          prev.next = lA;
          lA = lA.next;
        } else {
          prev.next = lB;
          lB = lB.next;
        }
        prev = prev.next; // 容易忘
      }

      // 循环终止时 l1 和 l2 中最多只有一个还有未合并的元素，直接将未合并的部分链接在新链表末尾即可
      // 存在 lA 和 lB 同时为空的情形吗？不存在，因为每次循环只能从其中一个链表取节点
      prev.next = (lA == null ? lB : lA);

      return preHead.next;
    }
  }

  // 递归
  static class Solution2 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      if (l1 == null) {
        return l2;
      }
      if (l2 == null) {
        return l1;
      }

      ListNode head = null; // 要合并的两个链表中剩余节点的头节点
      if (l1.val < l2.val) {
        head = l1;
        head.next = mergeTwoLists(l1.next, l2);
      } else {
        head = l2;
        head.next = mergeTwoLists(l1, l2.next);
      }

      // 不那么好的写法，因为修改了传入的参数，很容易引入诡异的 bug
      /*
      if (l1.val < l2.val) {
        head = l1;
        l1 = l1.next;
      } else {
        head = l2;
        l2 = l2.next;
      }
      head.next = mergeTwoLists(l1, l2);
      */

      return head;
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
