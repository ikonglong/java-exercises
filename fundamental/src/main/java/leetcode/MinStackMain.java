package leetcode;

import java.util.Stack;

public class MinStackMain {
  public static void main(String[] args) {
    MinStackV1 minStack = new MinStackV1();
    minStack.push(0);
    minStack.push(1);
    minStack.push(0);
    minStack.getMin();
    minStack.pop();
    minStack.getMin();
  }

  static class MinStackV1 {
    Stack<Integer> dataStack;
    Stack<Integer> asistStack;

    MinStackV1() {
      dataStack = new Stack<>();
      asistStack = new Stack<>();
      asistStack.push(Integer.MIN_VALUE);
    }

    public void push(Integer e) {
      dataStack.push(e);
      // 每次 push 都对应保存一个到目前为止的最小值
      // 若栈为空，peek 会抛出异常
      asistStack.push(Math.min(e, asistStack.peek()));
    }

    public Integer pop() {
      dataStack.pop();
      return asistStack.pop();
    }

    public Integer top() {
      return dataStack.peek();
    }

    public Integer getMin() {
      return asistStack.peek();
    }
  }

  static class Node {
    int val;
    int min;
    Node next;

    Node(int val, int min) {
      this.val = val;
      this.min = min;
    }

    Node(int val, int min, Node next) {
      this.val = val;
      this.min = min;
      this.next = next;
    }
  }

  static class MinStackV2 {
    Stack<Node> stack = new Stack<>();

    public void push(Integer e) {
      //      int min = e;
      //      if (!stack.isEmpty()) {
      //        min = Math.min(e, stack.peek().min);
      //      }
      //      stack.push(new Node(e, min));

      if (stack.isEmpty()) {
        stack.push(new Node(e, e));
      } else {
        stack.push(new Node(e, Math.min(e, stack.peek().val)));
      }
    }

    public Integer pop() {
      return stack.pop().val;
    }

    public Integer top() {
      return stack.peek().val;
    }

    public Integer getMin() {
      return stack.peek().min;
    }
  }

  static class MinStackV3 {
    Node head;
    Node tail;

    public void push(Integer e) {
      if (head == null) {
        head = new Node(e, e);
      } else {
        //        Node n = new Node(e, Math.min(e, head.min));
        //        n.next = head;
        //        head = n;
        head = new Node(e, Math.min(e, head.min), head);
      }
    }

    public Integer pop() {
      Node res = head;
      if (head != null) {
        head = head.next;
      }
      return res.val;
    }

    public Integer top() {
      return head == null ? null : head.val;
    }

    public Integer getMin() {
      return head == null ? null : head.min;
    }
  }
}
