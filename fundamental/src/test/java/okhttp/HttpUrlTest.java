package okhttp;

import okhttp3.HttpUrl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpUrlTest {

  @Test
  public void xx() {
    HttpUrl url =
        new HttpUrl.Builder()
            .scheme("http")
            .host("api.x.com")
            .addPathSegment("v2")
            .addPathSegment("books")
            .build();
    System.out.println(url.toString());
    Assertions.assertThat(url.toString()).isEqualTo("http://api.x.com/v2/books");
  }
}
