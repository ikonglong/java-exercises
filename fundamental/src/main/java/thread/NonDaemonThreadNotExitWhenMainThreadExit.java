package thread;

/**
 * main thread 是 non-daemon thread。
 *
 * <p>当 main thread exit 时，non-daemon threads 不会跟着主动退出。 但如果执行 kill pid 让进程退出，所有线程都会 exit。
 */
public class NonDaemonThreadNotExitWhenMainThreadExit {

  public static void main(String[] args) {
    Thread t =
        new Thread(
            () -> {
              for (; ; ) {
                System.out.println("Iteration done");
              }
            });
    System.out.println("Thread t is " + t.isDaemon());
    System.out.println("Thread main is " + Thread.currentThread().isDaemon());
    t.start();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
    }
  }
}
