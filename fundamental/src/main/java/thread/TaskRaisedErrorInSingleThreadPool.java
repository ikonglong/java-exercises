package thread;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskRaisedErrorInSingleThreadPool {

  public static void main(String[] args) {
    ThreadPoolExecutor pool =
        new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new CustomThreadFactory("x-"));

    pool.execute(
        () -> {
          System.out.print("Run task-1");
          throw new IllegalStateException("Error raised in task-1");
        });

    // Wait the first task to be done
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

    pool.execute(
        () -> {
          System.out.println("Run task-2");
          throw new IllegalStateException("Error raised in task-2");
        });

    // Wait the second task to be done
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

    pool.submit(
        () -> {
          System.out.println(Thread.currentThread().getName() + ": async executing task-3");
          throw new IllegalStateException(
              Thread.currentThread().getName() + ": Error raised in async-task-3");
        });

    Future<Void> future =
        pool.submit(
            () -> {
              throw new IllegalStateException(
                  Thread.currentThread().getName() + ": Error raised in async-task-4");
            });

    try {
      future.get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Main thread terminated");
  }
}
