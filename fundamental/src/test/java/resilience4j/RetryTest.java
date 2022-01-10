package resilience4j;

import static org.mockito.BDDMockito.times;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

public class RetryTest {

  @Test
  public void retryTimes() {
    RetryConfig config =
        RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(100))
            .retryOnException(e -> e instanceof RuntimeException)
            .build();

    // Given we have a GreetingService which throws an exception
    GreetingService greetingService = BDDMockito.mock(GreetingService.class);
    BDDMockito.given(greetingService.sayHello()).willThrow(new RuntimeException("Bad mood!"));

    // Create a Retry with custom configuration
    Retry retry = Retry.of("custom-retry", config);
    // Decorate the invocation of the GreetingService
    CheckedFunction0<String> retriableSupplier =
        Retry.decorateCheckedSupplier(retry, greetingService::sayHello);

    // When I invoke the function
    Try<String> result =
        Try.of(retriableSupplier).recover((throwable) -> "`Hello` from recovery function");

    // Then the greetingService should be invoked 3 times
    BDDMockito.then(greetingService).should(times(3)).sayHello();
    // and the exception should be handled by the recovery function
    Assertions.assertThat(result.get()).isEqualTo("`Hello` from recovery function");
  }

  interface GreetingService {

    String sayHello();
  }
}
