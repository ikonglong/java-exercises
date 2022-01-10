package thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

  private final AtomicInteger threadCount = new AtomicInteger(0);
  private final String baseThreadName;
  private final boolean daemon;

  public NamedThreadFactory(String baseThreadName) {
    this(baseThreadName, false);
  }

  public NamedThreadFactory(String baseThreadName, boolean daemon) {
    this.baseThreadName = baseThreadName;
    this.daemon = daemon;
  }

  @Override
  public Thread newThread(Runnable r) {
    String threadName = baseThreadName + "-" + threadCount.incrementAndGet();
    Thread t = new Thread(r, threadName);
    t.setDaemon(daemon);
    return t;
  }
}
