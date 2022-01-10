package vavr;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

import io.vavr.control.Try;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionHandlingTest {

  @Test
  public void mapException() {
    Assertions.assertThatCode(
            () ->
                Try.run(
                        () -> {
                          throw new IllegalStateException("boom!");
                        })
                    .get())
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessage("boom!");

    Assertions.assertThatCode(
            () ->
                Try.run(
                        () -> {
                          throw new IOException("i/o error");
                        })
                    .mapFailure(
                        Case(
                            $(instanceOf(IOException.class)),
                            e -> new IllegalStateException("Bad state: " + e.getMessage(), e)))
                    .get())
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessage("Bad state: i/o error");
  }
}
