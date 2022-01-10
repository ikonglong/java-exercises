package leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class DequeTest {
  public static void main(String[] args) {
    Deque<String> que = new LinkedList<>();
    que.offerFirst("1");
    que.offerFirst("2");
    que.offerFirst("3");
    for (String s : que) {
      System.out.println(s);
    }
    String s;
    //    while ((s = que.poll()) != null) {
    //      System.out.println(s);
    //    }
    //    while ((s = que.pollFirst()) != null) {
    //      System.out.println(s);
    //    }
    while ((s = que.pollLast()) != null) {
      System.out.println(s);
    }
  }
}
