package leetcode;

public class FirstCommonNodeOfTwoLists {
  public static void main(String[] args) {
    ListNode aN5 = new ListNode(5);
    ListNode aN4 = new ListNode(4, aN5);
    ListNode aN3 = new ListNode(8, aN4);
    ListNode aN2 = new ListNode(1, aN3);
    ListNode aN1 = new ListNode(4, aN2);

//    ListNode bN6 = new ListNode(5);
//    ListNode bN5 = new ListNode(4, bN6);
//    ListNode bN4 = new ListNode(8, bN5);
    // 引用同一个节点，而非值相同
    ListNode bN3 = new ListNode(1, aN3);
    ListNode bN2 = new ListNode(0, bN3);
    ListNode bN1 = new ListNode(5, bN2);

    Solution s = new Solution();
    ListNode n = s.getIntersectionNode(aN1, bN1);
    System.out.println(n);

    Solution2 s2 = new Solution2();
    ListNode n2 = s2.getIntersectionNode(aN1, bN1);
    System.out.println(n2);
  }

  public static class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
      if (headA == null || headB == null) {
        return null;
      }

      ListNode pA = headA;
      ListNode pB = headB;
      //while (pA.val = pB.val) { // Wrong, bug
      while (pA != pB) { // 引用同一个节点，而非值相同
        pA = (pA == null ? headB : pA.next);
        pB = (pB == null ? headA : pB.next);
      }
      return pA;
    }
  }

  public static class Solution2 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
      if (headA == null || headB == null) {
        return null;
      }

      int lenOfA = len(headA);
      int lenOfB = len(headB);

      ListNode longListHead;
      ListNode shortListHead;
      int lenDiff;
      if (lenOfA > lenOfB) {
        longListHead = headA;
        shortListHead = headB;
        lenDiff = lenOfA - lenOfB;
      } else {
        longListHead = headB;
        shortListHead = headA;
        lenDiff = lenOfB - lenOfA;
      }

      // 长的先遍历几步
      for (int i = 0; i < lenDiff; i++) {
        longListHead = longListHead.next;
      }

      // 引用同一个节点，而非值相同
      // longListHead.val != shortListHead.val // Wrong, bug
      // longListHead != shortListHead         // Correct

      while (longListHead != null
          && shortListHead != null
          && longListHead != shortListHead) {
        longListHead = longListHead.next;
        shortListHead = shortListHead.next;
      }
      return longListHead;

      //      ListNode firstCommon = null;
      //      while (longListHead != null && shortListHead != null) {
      //        if (longListHead.val == shortListHead.val) {
      //          firstCommon = longListHead;
      //          break;
      //        }
      //        longListHead = longListHead.next;
      //        shortListHead = shortListHead.next;
      //      }
      //      return firstCommon;
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
