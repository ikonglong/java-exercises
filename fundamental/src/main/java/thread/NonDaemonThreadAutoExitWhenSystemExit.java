package thread;

/** 调用 System.exit(0) 时所有 non-daemon threads 都会自动退出。 */
public class NonDaemonThreadAutoExitWhenSystemExit {

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
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }

    System.exit(0);
  }
}
