package usevolatile;

import java.util.concurrent.TimeUnit;

public class VolatileRefExample3 {
  private static Data data = new Data(-1, -1, -1);

  private static class Data {
    private int a;
    private int b;
    private int c;

    public Data(int a, int b, int c) {
      this.a = a;
      this.b = b;
      this.c = c;
    }

    public void setValues(int a, int b, int c) {
      synchronized (this) {
        this.a = a;
        try {
          TimeUnit.MICROSECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        this.b = b;
      }
      try {
        TimeUnit.MICROSECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.c = c;
    }

    public synchronized int[] getValues() {
      return new int[] {a, b, c};
    }
  }

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 30; i++) {
      int a = i;
      int b = i;
      int c = i;

      // writer
      Thread writerThread =
          new Thread(
              () -> {
                data.setValues(a, b, c);
              });

      // reader
      Thread readerThread =
          new Thread(
              () -> {
                int[] values = data.getValues();
                int x = values[0];
                int y = values[1];
                int z = values[2];
                if (x != y) {
                  System.out.printf("[a!=b]: a = %s, b = %s%n", x, y);
                }
                if (x != z) {
                  System.out.printf("[a!=c]: a = %s, c = %s%n", x, z);
                }
              });
      writerThread.start();
      readerThread.start();
      writerThread.join();
      readerThread.join();
    }
    System.out.println("finished");
  }
}
