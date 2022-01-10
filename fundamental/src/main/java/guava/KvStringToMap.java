package guava;

import com.google.common.base.Splitter;

import java.util.Map;

public class KvStringToMap {

  public static void main(String[] args) {
    String kvString = "a:1, b: 2 , c  :3";
    Map<String, String> map =
        Splitter.on(",")
            .trimResults()
            .omitEmptyStrings()
            .withKeyValueSeparator(":")
            .split(kvString);
  }
}
