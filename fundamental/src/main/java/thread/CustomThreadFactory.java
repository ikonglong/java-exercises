package thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
  static final AtomicInteger count = new AtomicInteger(0);

  final String threadNamePrefix;
  final boolean daemon;

  public CustomThreadFactory(String threadNamePrefix) {
    this(threadNamePrefix, false);
  }

  public CustomThreadFactory(String threadNamePrefix, boolean daemon) {
    this.threadNamePrefix = threadNamePrefix;
    this.daemon = daemon;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r, threadNamePrefix + count.incrementAndGet());
    t.setDaemon(daemon);
    return t;
  }
}
