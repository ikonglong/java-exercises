package thread;

import com.google.common.util.concurrent.Uninterruptibles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolShutdown {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(
        () -> {
          for (; ; ) {
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            System.out.println("I'm busy");
          }
        });

    Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

    executorService.shutdown();
    System.out.println("Is executor shutdown: " + executorService.isShutdown());
    for (; ; ) {
      if (executorService.isTerminated()) {
        System.out.println("ExecutorService terminated");
        System.exit(0);
      } else {
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
      }
    }
  }
}
