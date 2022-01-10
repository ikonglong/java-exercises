package thread;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DaemonThreadExitWhenMainThreadFinish {
  public static void main(String[] args) {
    final ArrayBlockingQueue<Integer> que = new ArrayBlockingQueue<>(1000);
    for (int n = 1; n <= 1000; n++) {
      que.add(n);
    }
    ExecutorService executor =
        Executors.newSingleThreadExecutor(new CustomThreadFactory("worker-", false));
    Thread t =
        new Thread(
            () -> {
              executor.shutdown();
              System.err.println("Shut down executor");
            });
    t.setDaemon(true);
    Runtime.getRuntime().addShutdownHook(t);
    executor.submit(
        () -> {
          boolean interrupted = false;
          while (!(interrupted && que.isEmpty())) {
            try {
              Integer element = que.take();
              System.out.println("Element: " + element);
              Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              interrupted = true;
              System.err.println("worker thread was interrupted");
            }
          }
        });
    Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
    System.err.println("Main thread finished!!!");
  }
}
