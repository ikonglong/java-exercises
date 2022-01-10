package vavr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import java.util.concurrent.atomic.AtomicInteger;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

public class TryRunTest {

  @Test
  public void runJdkRunnable() {
    final AtomicInteger count = new AtomicInteger(0);
    Runnable counter = () -> count.incrementAndGet();
    Try.runRunnable(counter); // Without call to Try.get()
    assertThat(count.get()).isEqualTo(1);
    Try.runRunnable(counter).get();
    assertThat(count.get()).isEqualTo(2);
  }

  @Test
  public void runCheckedRunnable() {
    final AtomicInteger count = new AtomicInteger(0);
    CheckedRunnable counter =
        new CheckedRunnable() {
          @Override
          public void run() throws Throwable {
            count.incrementAndGet();
          }
        };
    Try.run(counter); // Without call to Try.get()
    assertThat(count.get()).isEqualTo(1);
    Try.run(counter).get();
    assertThat(count.get()).isEqualTo(2);
  }

  @Test
  public void runCheckedRunnableThatThrowException() {
    final AtomicInteger count = new AtomicInteger(0);
    assertThatCode(
            () ->
                Try.run(
                    () -> {
                      count.incrementAndGet();
                      throw new RuntimeException("boom!");
                    })) // Without call to Try.get()
        .doesNotThrowAnyException();
    // This indicates that if given CheckedRunnable may throw exception and you don't call
    // Try.get(), you will not get the thrown exception.
    assertThat(count.get()).isEqualTo(1);

    assertThatThrownBy(
            () ->
                Try.run(
                        () -> {
                          throw new RuntimeException("boom!");
                        })
                    .get())
        .isInstanceOf(RuntimeException.class)
        .hasMessage("boom!");
  }

  @Test
  public void runCheckedRunnableDecoratedByRetry() {
    RetryConfig config = RetryConfig.custom().maxAttempts(3).build();
    RetryRegistry registry = RetryRegistry.of(config);
    Retry retry = registry.retry("retryA");
    final AtomicInteger count = new AtomicInteger(0);
    ThrowingCallable code =
        () ->
            Try.run(
                    Retry.decorateCheckedRunnable(
                        retry,
                        new CheckedRunnable() {
                          @Override
                          public void run() throws Throwable {
                            count.incrementAndGet();
                            throw new RuntimeException("boom!");
                          }
                        }))
                .get();
    assertThatThrownBy(code).isInstanceOf(RuntimeException.class).hasMessage("boom!");
    assertThat(count.get()).isEqualTo(3);
  }
}
