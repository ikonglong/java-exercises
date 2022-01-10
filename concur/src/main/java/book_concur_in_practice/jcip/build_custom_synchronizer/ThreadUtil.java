package book_concur_in_practice.jcip.build_custom_synchronizer;

public class ThreadUtil {
  public static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }
}
