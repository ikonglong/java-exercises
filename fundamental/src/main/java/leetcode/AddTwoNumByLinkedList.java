package leetcode;

public class AddTwoNumByLinkedList {
  public static void main(String[] args) {
    //

  }

  static class Solution {
    // 因为题目没有明确说不能修改传入的链表，所以下面的实现修改了传入的链表
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      ListNode head = null, tail = null; // 结果链表的头、尾指针
      // 虽然计算个位数时无需从其前一位获得进位值（前面没有数位了），
      // 但可以认为从前一位获得的进位值为 0。这样循环中的逻辑就比较统一了。
      int carry = 0;
      while (l1 != null || l2 != null) {
        // 如果已走到链表末尾，后续就补 0
        int v1 = (l1 == null ? 0 : l1.val);
        int v2 = (l2 == null ? 0 : l2.val);
        int sum = v1 + v2 + carry;
        if (head == null) {
          head = tail = new ListNode(sum % 10);
        } else {
          tail.next = new ListNode(sum % 10);
          tail = tail.next;
        }

        carry = sum / 10; // 计算进位值
        if (l1 != null) {
          l1 = l1.next;
        }
        if (l2 != null) {
          l2 = l2.next;
        }
      }

      // 如果进位值不为 0，就需要添加一个数位
      if (carry > 0) {
        tail.next = new ListNode(carry);
      }
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
