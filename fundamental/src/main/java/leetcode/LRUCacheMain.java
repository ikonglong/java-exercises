package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheMain {
  public static void main(String[] args) {
    // ["LRUCache","put","put","get","put","get","put","get","get","get"]
    // [[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
    LRUCache cache = new LRUCache(2);
    cache.put(1, 1);
    cache.put(2, 2);
    System.out.println(cache.get(1));
    cache.put(3, 3);
    System.out.println(cache.get(2));
    cache.put(4, 4);
    System.out.println(cache.get(1));
    System.out.println(cache.get(3));
    System.out.println(cache.get(4));
  }

  static class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node() {}

    public Node(int _key, int _value) {
      key = _key;
      value = _value;
    }
  }

  static class LRUCache {

    private int size;
    private int capacity;
    private Node head;
    private Node tail;
    private Map<Integer, Node> cache = new HashMap<>();

    public LRUCache(int capacity) {
      this.size = 0;
      this.capacity = capacity;
      this.head = new Node(); // dummy head node 真正的节点添加在伪头节点之后
      this.tail = new Node(); // dummy tail node 真正的节点添加在伪尾节点之前
      head.next = tail;
      tail.prev = head;
    }

    public int get(int key) {
      Node node = cache.get(key);
      if (node == null) {
        return -1;
      }
      moveToHead(node);
      return node.value;
    }

    public void put(int key, int value) {
      Node node = cache.get(key);
      if (node == null) { // 给定的 key 不存在
        node = new Node(key, value);
        cache.put(key, node);
        addHead(node);
        if (++size > capacity) {
          Node tail = removeTail();
          cache.remove(tail.key);
          --size;
        }
      } else {
        node.value = value; // 更新
        moveToHead(node);
      }
    }

    void moveToHead(Node node) {
      remove(node);
      addHead(node);
    }

    void remove(Node node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
      node.prev = null;
      node.next = null;
    }

    Node removeTail() {
      Node node = tail.prev;
      remove(node); // 因为最后一个节点是伪节点，真正的尾节点是伪节点的前驱节点
      return node;
    }

    void addHead(Node node) {
      node.prev = head;
      node.next = head.next;
      head.next.prev = node;
      head.next = node;
    }
  }
}
