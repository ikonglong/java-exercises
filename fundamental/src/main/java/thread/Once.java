package thread;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.*;

public class Once<T> {
  private final ExecutorService executor =
      new ThreadPoolExecutor(
          1,
          1,
          0,
          TimeUnit.SECONDS,
          new SynchronousQueue<>(),
          r -> new Thread(r, "Once"),
          new ThreadPoolExecutor.AbortPolicy());

  private Future<T> future = null;

  public Future<T> submit(Callable<T> callable) {
    synchronized (this) {
      Future<T> future = this.future;
      if (future != null) {
        return future;
      }
      for (; ; ) {
        try {
          future =
              executor.submit(
                  () -> {
                    try {
                      return callable.call();
                    } catch (Exception e) {
                      e.printStackTrace();
                      return null;
                    } finally {
                      synchronized (Once.this) {
                        Once.this.future = null;
                      }
                    }
                  });
          this.future = future;
          return future;
        } catch (RejectedExecutionException e) {
          future = this.future;
          if (future != null) {
            return future;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    Once once = new Once();
    once.executor.submit(
        () -> {
          System.out.println("Task 1 executing");
          Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        });
    once.executor.submit(
        () -> {
          System.out.println("Task 2 executing");
          Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        });
  }
}
