package thread;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

public class ThreadUncaughtExceptionHandler {

  public static void main(String[] args) {
    Thread t1 =
        new Thread(
            () -> {
              for (int c = 1; c <= 10; c++) {
                System.out.println("Thread t1: c = " + c);
                if (c == 3) {
                  throw new RuntimeException("[Thread t1] Bad count: " + c);
                }
              }
            });

    System.out.println(
        "Thread global/static uncaughtExceptionHandler: "
            + MoreObjects.firstNonNull(Thread.getDefaultUncaughtExceptionHandler(), "null"));
    System.out.println(
        "Thread instance's uncaughtExceptionHandler: "
            + MoreObjects.firstNonNull(t1.getUncaughtExceptionHandler(), "null"));

    Thread t2 =
        new Thread(
            () -> {
              for (int c = 1; c <= 10; c++) {
                System.out.println("Thread t2: c = " + c);
                if (c == 6) {
                  throw new RuntimeException("[Thread t2] Bad count: " + c);
                }
              }
            });
    t2.setUncaughtExceptionHandler(
        (t, e) -> {
          System.err.println("\n------------------------------");
          System.err.println(
              "Custom UncaughtExceptionHandler: \n" + ">>> " + e.getClass() + ":" + e.getMessage());
        });

    t1.start();
    t2.start();
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
  }
}
