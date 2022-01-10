package locks;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

public class Mutex {
  class Sync extends AbstractQueuedSynchronizer {
    @Override
    protected boolean tryAcquire(int ignore) {
      return compareAndSetState(0, 1);
    }

    @Override
    protected boolean tryRelease(int ignore) {
      setState(0);
      return true;
    }
  }

  private final Sync sync = new Sync();

  public void acquire() {
    sync.acquire(0);
  }

  public void release() {
    sync.release(0);
  }

  public static void main(String[] args) {
    final Mutex mutex = new Mutex();
    Thread t1 =
        new Thread(
            () -> {
              try {
                mutex.acquire();
                System.out.println("[t1] acquire succeeded");
                Uninterruptibles.sleepUninterruptibly(5, TimeUnit.MINUTES);
                System.out.println("[t1] work done");
              } finally {
                mutex.release();
              }
            });
    t1.setName("t1");
    t1.start();

    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    Thread t2 = new Thread(() -> {
        try {
          mutex.acquire();
          System.out.println("[t2] acquire succeeded");
          Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
          System.out.println("[t2] work done");
        } finally {
          mutex.release();
        }
    });
    t2.setName("t2");
    t2.start();

    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.HOURS);
  }
}
