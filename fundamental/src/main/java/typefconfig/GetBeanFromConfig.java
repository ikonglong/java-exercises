package typefconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GetBeanFromConfig {
  public static void main(String[] args) {
    Book b = new Book();
    b.title = "Jokes";

    Map<String, Object> bookMap = new HashMap<>();
    bookMap.put("title", "Jokes");
    bookMap.put("price", 500D);

    Map<String, Object> map = new HashMap<>();
    map.put("buyer", "simon");
    map.put("book", bookMap);

    //    map.put("book", "xyz");
    //    map.put("names", "a,b,c");
    //    map.put("names2", Lists.newArrayList("1", "2", "3"));

    Config config = ConfigFactory.parseMap(map);
    System.out.println(config);
    System.out.println(config.getDouble("book.price"));
    System.out.println(config.getString("book.title"));
    Book aBook = ConfigBeanFactory.create(config.getConfig("book"), Book.class);
    System.out.println(aBook.title + ">>>" + aBook.price);

    System.out.println("!!!" + ConfigFactory.parseMap(Collections.emptyMap()));
    //    ConfigValue cv = ConfigValueFactory.fromAnyRef(b);
    //    System.out.println(cv);

    //    ConfigValue v = ConfigValueFactory.fromAnyRef(map);
    //    ConfigValueFactory.fromMap(map, "Book Map");
    //    Config config = ConfigFactory.parseMap(map, "Book Map");
    //    ConfigObject val = config.getObject("book");
  }

  public static class Book {
    String title;
    Double price;

    public Book() {}

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public Double getPrice() {
      return price;
    }

    public void setPrice(Double price) {
      this.price = price;
    }
  }
}
