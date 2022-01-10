package joinstring;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UseJava8StreamApi {
  public static void main(String[] args) {
    System.out.println(Stream.of("a", "b", "c").collect(Collectors.joining(",")));
    System.out.println(Stream.of("a", "b", "c").collect(Collectors.joining(",", "[", "]")));
  }
}
