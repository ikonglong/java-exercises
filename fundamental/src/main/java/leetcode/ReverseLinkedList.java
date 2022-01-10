package leetcode;

public class ReverseLinkedList {
  public static void main(String[] args) {
    ByLoopV2 solutionV2 = new ByLoopV2();
    ListNode n5 = new ListNode(5);
    ListNode n4 = new ListNode(4, n5);
    ListNode n3 = new ListNode(3, n4);
    ListNode n2 = new ListNode(2, n3);
    ListNode n1 = new ListNode(1, n2);
    ListNode reversedHead = solutionV2.reverseList(n1);
    System.out.println(n1);
    System.out.println(n2);
  }

  static class ByLoopV1 {
    public ListNode reverseList(ListNode head) {
      ListNode current = head;
      ListNode prev = null;
      ListNode reversedHead = null;
      while (current != null) {
        if (current.next == null) {
          reversedHead = current;
        }

        ListNode next = current.next;
        current.next = prev;
        prev = current;
        current = next;
      }
      return reversedHead;
    }
  }

  static class ByLoopV2 {
    public ListNode reverseList(ListNode head) {
      if (head == null) {
        throw new IllegalArgumentException("head of linked-list is null");
      }

      if (head.next == null) {
        return head;
      }

      /** 有 Bug。初始时 prev 指向 head，current 指向 head.next， 导致没有修改 head.next 指针 */
      ListNode prev = head;
      ListNode current = head.next;
      ListNode next = current.next;
      ListNode reversedHead = null;
      while (current != null) {
        if (current.next == null) {
          reversedHead = current;
        }
        next = current.next;
        current.next = prev;
        prev = current;
        current = next;
      }
      return reversedHead;
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
