package collection;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQue {
  public static void main(String[] args) {
    final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
    queue.offer("a");
    queue.offer("b");
    queue.offer("c");
    System.out.println(queue.peek());
    System.out.println(queue.peek());
    System.out.println(queue.peek());

    Thread t1 =
        new Thread(
            () -> {
              Iterator<String> itr = queue.iterator();
              for (int i = 0; i < 3; i++) {
                while (itr.hasNext()) {
                  System.out.println("Get " + itr.next());
                }
                System.out.println("No elements to get, so wait");
                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
              }
            });

    Thread t2 =
        new Thread(
            () -> {
              Uninterruptibles.sleepUninterruptibly(200, TimeUnit.MILLISECONDS);
              queue.offer("d");
              System.out.println("add d");
              Uninterruptibles.sleepUninterruptibly(200, TimeUnit.MILLISECONDS);
              queue.offer("e");
              System.out.println("add e");
              Uninterruptibles.sleepUninterruptibly(200, TimeUnit.MILLISECONDS);
              queue.offer("f");
              System.out.println("add f");
              Uninterruptibles.sleepUninterruptibly(200, TimeUnit.MILLISECONDS);
              queue.offer("g");
              System.out.println("add g");
            });
    t1.start();
    t2.start();
    Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
  }
}
