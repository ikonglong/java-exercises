package thread;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InterruptThreadBlockedOnQueTake {
  public static void main(String[] args) {
    final ArrayBlockingQueue<String> que = new ArrayBlockingQueue<>(5);
    Thread t1 =
        new Thread(
            () -> {
              boolean interrupted = false;
              while (!interrupted) {
                try {
                  String s = que.take();
                  System.out.println("s: " + s);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                  interrupted = true;
                }
              }
            });
    t1.start();
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    t1.interrupt();
  }
}
