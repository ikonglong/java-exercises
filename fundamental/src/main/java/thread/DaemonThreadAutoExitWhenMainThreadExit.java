package thread;

public class DaemonThreadAutoExitWhenMainThreadExit {
  public static void main(String[] args) {
    Thread t =
        new Thread(
            () -> {
              for (; ; ) {
                System.out.println("Iteration done");
              }
            });
    t.setDaemon(true);
    t.start();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
    }
  }
}
