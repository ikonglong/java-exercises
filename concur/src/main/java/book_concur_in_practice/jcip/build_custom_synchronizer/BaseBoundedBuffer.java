package book_concur_in_practice.jcip.build_custom_synchronizer;

import jcip.GuardedBy;

/**
 * Base class for classic array-based circular bounded buffer where the buffer state variables (buf,
 * head, tail, and count) are guarded by the buffer’s intrinsic lock.
 */
public abstract class BaseBoundedBuffer<V> {
  @GuardedBy("this")
  private final V[] buf;

  @GuardedBy("this")
  private int head;

  @GuardedBy("this")
  private int tail;

  @GuardedBy("this")
  private int count;

  public BaseBoundedBuffer(int capacity) {
    this.buf = (V[]) new Object[capacity];
  }

  protected final synchronized void doPut(V v) {
    buf[tail++] = v;
    if (tail == buf.length) {
      tail = 0;
    }
    count++;
  }

  protected final synchronized V doTake() {
    V v = buf[head];
    buf[head] = null;
    if (head++ == buf.length) { // head 向后移。head 可能大于 tail
      head = 0;
    }
    count--;
    return v;
  }

  public synchronized boolean isFull() {
    return count == buf.length;
  }

  public synchronized boolean isEmpty() {
    return count == 0;
  }
}
