package forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static java.lang.String.format;

public class FiboExample {

  public static void main(String[] args) {
    int processorsNum = Runtime.getRuntime().availableProcessors();
    ForkJoinPool pool = new ForkJoinPool(processorsNum);

    ForkJoinTask<Integer> result = pool.submit(new Fibonacci(5));
    System.out.println("result: " + result.join());
  }

  static class Fibonacci extends RecursiveTask<Integer> {
    private final int n; // 第 n 项，n 从 0 开始

    public Fibonacci(int num) {
      this.n = num;
    }

    @Override
    protected Integer compute() {
      if (n <= 1) { // 第 0、1 项分别为 0、1
        return n;
      }
      Fibonacci fiboMinus1 = new Fibonacci(n - 1);
      Fibonacci fiboMinus2 = new Fibonacci(n - 2);
      System.out.println(
          format("[%s] f(%d) = f(%d) + f(%d)", Thread.currentThread().getName(), n, n - 1, n - 2));
      // Arranges to asynchronously execute this task in the pool the current task is running in, if
      // applicable, or using the ForkJoinPool.commonPool() if not in ForkJoinPool. While it is not
      // necessarily enforced, it is a usage error to fork a task more than once unless it has
      // completed and been reinitialized. Subsequent modifications to the state of this task or any
      // data it operates on are not necessarily consistently observable by any thread other than
      // the one executing it unless preceded by a call to join or related methods, or a call to
      // isDone returning true.
      // --------
      // Subsequent modifications 随后的修改指的是在哪个操作之后呢？for() 操作之后吗？
      // for() 方法将 this task 提交给 ForkJoinPool 异步执行。
      fiboMinus1.fork();
      return fiboMinus2.compute() + fiboMinus1.join();
    }
  }
}
