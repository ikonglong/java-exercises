package classloader;

public class ThreadCtxClassLoader {
  public static void main(String[] args) {
    Thread t1 =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                System.out.println("Do something");
                System.out.println("object.getClass().get" + getClass().getClassLoader());
              }
            });
    t1.start();
    System.out.println("Thread.this.getContextClassLoader: " + t1.getContextClassLoader());
  }
}
