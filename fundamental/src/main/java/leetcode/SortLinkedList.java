package leetcode;

public class SortLinkedList {
  public static void main(String[] args) {
    //
  }

  // 自顶向下归并排序
  static class Solution1 {
    public ListNode sortList(ListNode head) {
      // tail 指向链表最后一个节点的下一个节点，因此这里为 null
      return sortList(head, null);
    }

    // 递归方法
    // head, tail 等价于数组归并排序时的 start, end
    // 子链表包含 head，但不包含 tail
    ListNode sortList(ListNode head, ListNode tail) {
      // 递归终止条件：子链表为空或只有一个节点
      if (head == null) {
        return head;
      }
      if (head.next == tail) { // 只包含一个节点。注意，子链表不包含 tail
        head.next = null; // 意图是？
        return head;
      }

      // 通过快、慢指针找到链表中点。快指针每次移动 2 步，慢指针每次移动 1 步。
      // 当快指针到达尾部时，慢指针就是中点
      ListNode slow = head, fast = head;
      while (fast != tail) { // 很容易写错：fast != null
        slow = slow.next;
        fast = fast.next;
        if (fast != tail) { // 很容易写错：fast != null
          fast = fast.next;
        }
      }

      ListNode mid = slow;
      ListNode head1 = sortList(head, mid);
      ListNode head2 = sortList(mid, tail);
      return merge(head1, head2);
    }

    ListNode merge(ListNode l1, ListNode l2) {
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

  // 自底向上归并排序
  static class Solution2 {
    public ListNode sortList(ListNode head) {
      if (head == null) {
        return head;
      }

      int len = len(head);

      // 设置一个哨兵节点，让每一轮归并后的链表的头节点链接至它。
      ListNode preHead = new ListNode(-1, head);

      for (int subLen = 1; subLen < len; subLen <<= 1) {
        ListNode prev = preHead;
        // cutPoint 就是待排序的链表。
        // 在一轮归并中，初次进入 while 时，通过 prev 引用修改了 preHead.next，
        // 使它指向这轮归并产生的链表的头节点。
        ListNode cutPoint = preHead.next;
        // Wrong, bug
        //ListNode cutPoint = head;

        while (cutPoint != null) {
          // 保存子链-1的头节点，并让 cutPoint 指向子链-1的最后一个元素
          ListNode head1 = cutPoint;
          for (int i = 1; i < subLen && cutPoint.next != null; i++) {
            cutPoint = cutPoint.next;
          }

          // 保存子链-2的头节点
          ListNode head2 = cutPoint.next;
          // 断开子链-1和子链-2。此时 cutPoint 仍指向子链-1的最后一个元素
          cutPoint.next = null;

          // 先让 cutPoint 指向子链-2的头节点，再通过循环让 cutPoint 指向子链-2的最后一个元素
          // 这里的处理模式/套路跟子链-1基本相同
          cutPoint = head2;
          for (int i = 1; i < subLen && cutPoint != null && cutPoint.next != null; i++) {
            cutPoint = cutPoint.next;
          }

          // 前面的循环结束后，cutPoint 指向子链-2的最后一个元素
          // 先让 remainderHead 指向剩余部分的头节点，再断开子链-2和剩余部分的链接
          ListNode remainderHead = null;
          if (cutPoint != null) {
            remainderHead = cutPoint.next;
            cutPoint.next = null;
          }

          // 合并现在完全独立的子链-1和子链-2
          ListNode merged = merge(head1, head2);
          prev.next = merged; // 将合并后的链表链接至「已排好序的」的链表部分的尾部
          while (prev.next != null) { // 移动 prev，使其指向新的尾部
            prev = prev.next;
          }

          // 继续处理原链表的剩余部分
          cutPoint = remainderHead;
        }
      }

      return preHead.next;
    }

    int len(ListNode l) {
      int len = 0;
      ListNode n = l;
      while (n != null) {
        len++;
        n = n.next;
      }
      return len;
    }

    ListNode merge(ListNode l1, ListNode l2) {
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
