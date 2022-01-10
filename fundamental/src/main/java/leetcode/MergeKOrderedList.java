package leetcode;

import java.util.PriorityQueue;

public class MergeKOrderedList {
  public static void main(String[] args) {}

  static class SolutionV1 {
    // 每次循环将 lists[i] 合并至结果中
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }
      ListNode ans = lists[0];
      for (int i = 1; i < lists.length; i++) {
        ans = mergeTwoLists(ans, lists[i]);
      }
      return ans;
    }

    ListNode mergeTwoLists(ListNode a, ListNode b) {
      ListNode dummyHead = new ListNode();
      ListNode tail = dummyHead;
      ListNode aPtr = a, bPtr = b;
      while (aPtr != null && bPtr != null) {
        if (aPtr.val <= bPtr.val) {
          tail.next = aPtr;
          aPtr = aPtr.next;
        } else {
          tail.next = bPtr;
          bPtr = bPtr.next;
        }
        tail = tail.next;
      }
      tail.next = (aPtr == null ? bPtr : aPtr);
      return dummyHead.next;
    }
  }

  // 分支合并。逐层两两合并
  static class SolutionV2 {
    // 每次循环将 lists[i] 合并至结果中
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }
      return merge(lists, 0, lists.length - 1);
    }

    // 将索引在 [l, r] 中的链表合并为一个升序链表
    ListNode merge(ListNode[] lists, int l, int r) {
      if (l == r) return lists[l];
      // 采用下面的基本条件会报错：
      // java.lang.StackOverflowError：at line ..., Solution.merge
      // if (r == l + 1) return mergeTwoLists(lists[l], lists[r]);
      // if (l > r) return null; // 感觉这种情形不会发生。(l+r)/2 <= r
      int mid = (l + r) >> 1;
      return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

    ListNode mergeTwoLists(ListNode a, ListNode b) {
      ListNode dummyHead = new ListNode();
      ListNode tail = dummyHead;
      ListNode aPtr = a, bPtr = b;
      while (aPtr != null && bPtr != null) {
        if (aPtr.val <= bPtr.val) {
          tail.next = aPtr;
          aPtr = aPtr.next;
        } else {
          tail.next = bPtr;
          bPtr = bPtr.next;
        }
        tail = tail.next;
      }
      tail.next = (aPtr == null ? bPtr : aPtr);
      return dummyHead.next;
    }
  }

  // 使用现有的优先队列
  static class SolutionV3 {
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }

      PriorityQueue<ListNode> que = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);
      for (ListNode head : lists) {
        if (head != null) {
          que.offer(head);
        }
      }

      ListNode dummyHead = new ListNode(0);
      ListNode tail = dummyHead;
      while (!que.isEmpty()) {
        // 从优先队列中删除堆顶元素，添加至结果链表中
        tail.next = que.poll();
        tail = tail.next;
        if (tail.next != null) {
          que.offer(tail.next);
        } else {
          // 如果为 null，则表明一条链表遍历完毕。
          // 什么也不做。下次循环就会 poll 到其他链表的元素，重复前面的过程
        }
      }
      return dummyHead.next;
    }
  }

  // 分支算法，循环实现
  static class SolutionV4 {
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }
      int n = lists.length;
      // span 表示每一轮循环要合并的两个链表索引的距离/跨度
      for (int span = 1; span < n; span = 2 * span) {
        // i 是一组中第二个元素的索引，i - span 就是第一个元素的索引
        for (int i = span; i < n; i = i + 2 * span) {
          lists[i - span] = mergeTwoLists(lists[i - span], lists[i]);
        }
      }
      return lists[0];
    }

    ListNode mergeTwoLists(ListNode a, ListNode b) {
      ListNode dummyHead = new ListNode();
      ListNode tail = dummyHead;
      ListNode aPtr = a, bPtr = b;
      while (aPtr != null && bPtr != null) {
        if (aPtr.val <= bPtr.val) {
          tail.next = aPtr;
          aPtr = aPtr.next;
        } else {
          tail.next = bPtr;
          bPtr = bPtr.next;
        }
        tail = tail.next;
      }
      tail.next = (aPtr == null ? bPtr : aPtr);
      return dummyHead.next;
    }
  }

  // Brute force
  static class SolutionV5 {
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }

      ListNode dummyHead = new ListNode();
      ListNode tail = dummyHead;

      int minHeadListIndex = -1;
      while ((minHeadListIndex = minHeadListIndex(lists)) != -1) {
        tail.next = lists[minHeadListIndex];
        tail = tail.next;
        lists[minHeadListIndex] = lists[minHeadListIndex].next;
      }

      return dummyHead.next;
    }

    /** 返回头节点最小的那个链表的索引 */
    int minHeadListIndex(ListNode[] lists) {
      ListNode minHead = null;
      int minIndex = -1;
      for (int i = 0; i < lists.length; i++) {
        if (lists[i] == null) {
          continue;
        }
        if (minHead == null || lists[i].val < minHead.val) {
          minHead = lists[i];
          minIndex = i;
        }
      }
      return minIndex;
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
