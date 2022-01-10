package book_concur_in_practice.jcip.build_custom_synchronizer;

public class ClientCallingGrumpy {

  static final int SLEEP_GRANULARITY = 1000; // millisecond

  public static void main(String[] args) {
    GrumpyBoundedBuffer<Integer> buffer = new GrumpyBoundedBuffer<>(5);
    for (int i = 1; i <= 5; i++) {
      try {
        buffer.put(i);
      } catch (BufferFullException e) {
      }
    }

    while (true) {
      try {
        Integer item = buffer.take();
        System.out.println(item); // use item
        break;
      } catch (BufferEmptyException e) { // if empty, sleep or yield
        ThreadUtil.sleep(SLEEP_GRANULARITY);
        // Thread.yield();
      }
    }
  }
}
