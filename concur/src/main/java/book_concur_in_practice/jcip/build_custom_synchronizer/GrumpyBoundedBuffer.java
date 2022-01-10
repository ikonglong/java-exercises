package book_concur_in_practice.jcip.build_custom_synchronizer;

/**
 * forcing the caller to manage the state dependence. Pushing the state dependence back to the
 * caller also makes it nearly impossible to do things like preserve FIFO ordering; by forcing the
 * caller to retry, you lose the information of who arrived ﬁrst.
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
  public GrumpyBoundedBuffer(int cap) {
    super(cap);
  }

  public synchronized void put(V v) throws BufferFullException {
    if (isFull()) throw new BufferFullException();
    doPut(v);
  }

  public synchronized V take() throws BufferEmptyException {
    if (isEmpty()) throw new BufferEmptyException();
    return doTake();
  }
}
