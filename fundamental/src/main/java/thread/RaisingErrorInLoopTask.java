package thread;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaisingErrorInLoopTask {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newSingleThreadExecutor(new CustomThreadFactory("x-"));
    executor.execute(
    //executor.submit(
        (Runnable)
            () -> {
              int i = 0;
              while (true) {
                if (i == 10) {
                  throw new RuntimeException("Deliberate error");
                }
                i++;
                System.out.println("i=" + i);
              }
            });
    Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
    System.out.println("Main Thread: work done");
  }
}
