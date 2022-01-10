package guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

public class CacheDemo {

  private static final Value EMPTY = new Value("empty");
  private static final Value EXIST = new Value("exist");

  public static void main(String[] args) throws Exception {
    LoadingCache<String, Value> cache =
        CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(
                new CacheLoader<String, Value>() {
                  @Override
                  public Value load(String key) throws Exception {
                    return EMPTY;
                  }
                });
    cache.put("keyA", new Value("A"));
    System.out.println(cache.get("keyB"));
    System.out.println("---------------------------------------------");

    System.out.println("keyA -> " + cache.get("keyA") == null ? "null" : cache.get("keyA"));
    System.out.println("keyB -> " + cache.get("keyB") == null ? "null" : cache.get("keyB"));
    System.out.println("---------------------------------------------");

    Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);

    System.out.println("keyA -> " + cache.get("keyA") == null ? "null" : cache.get("keyA"));
    System.out.println("keyB -> " + cache.get("keyB") == null ? "null" : cache.get("keyB"));
  }

  private static class Value {
    private String val;

    Value(String value) {
      this.val = value;
    }

    @Override
    public String toString() {
      return "Value{val=" + val + "}";
    }
  }
}
