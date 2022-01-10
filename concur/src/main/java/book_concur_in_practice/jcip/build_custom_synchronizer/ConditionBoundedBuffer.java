package book_concur_in_practice.jcip.build_custom_synchronizer;

import jcip.GuardedBy;
import jcip.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class ConditionBoundedBuffer<T> {
  private static final int BUFFER_SIZE = 8;

  protected final Lock lock = new ReentrantLock();
  // Condition Predicate: notFull (count < items.length)
  private final Condition notFull = lock.newCondition();
  // Condition Predicate: notEmpty (count > 0)
  private final Condition notEmpty = lock.newCondition();

  @GuardedBy("lock")
  private final T[] items = (T[]) new Object[BUFFER_SIZE];

  @GuardedBy("lock")
  private int head;

  @GuardedBy("lock")
  private int tail;

  @GuardedBy("lock")
  private int count;

  // blocks until: notFull
  public void put(T x) throws InterruptedException {
    lock.lock();
    try {
      while (count == items.length) {
        notFull.await(); // put current thread into waiting queue of `notFull` condition
      }
      items[tail++] = x;
      if (tail == items.length) {
        tail = 0; // circular buffer
      }
      count++;
      notEmpty.signal(); // good
      // notEmpty.signalAll(); // not good
    } finally {
      lock.unlock();
    }
  }

  // blocks until: notEmpty
  public T take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0) {
        notEmpty.await(); // put current thread into waiting queue of `notEmpty` condition
      }
      T v = items[head];
      items[head] = null; // important
      if (++head >= items.length) {
        head = 0;
      }
      count--;
      notFull.signal();
      return v;
    } finally {
      lock.unlock();
    }
  }
}
