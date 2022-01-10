package Collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ArrayListIterator {
  @Test
  public void removeByIterator_whileIterating() {
    List<String> list = new ArrayList<>(100);
    for (int i = 1; i <= 100; i++) {
      list.add("" + i);
    }

    Random random = new Random();
    Iterator<String> itr = list.iterator();
    while (itr.hasNext()) {
      String item = itr.next();
      if (random.nextInt(20) % 3 == 0) {
        itr.remove();
        System.out.println("Remove by iterator: " + item);
      } else {
        System.out.println("Read by iterator: " + item);
      }
    }

    System.out.println("Remaining items: " + list);
  }

  @Test
  public void addToList_whileIterating() {
    List<String> list = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      list.add("" + i);
    }

    Iterator<String> itr = list.iterator();
    while (itr.hasNext()) {
      String item = itr.next();
      if (Integer.valueOf(item) % 3 == 0) {
        list.add("x");
        System.out.println("Added x to list");
      } else {
        System.out.println("Read by iterator: " + item);
      }
    }
  }

  @Test
  public void addToList_whileForEach() {
    List<String> list = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      list.add("" + i);
    }

    list.forEach(
        elem -> {
          if (Integer.valueOf(elem) % 3 == 0) {
            list.add("x");
            System.out.println("Added x to list");
          } else {
            System.out.println("Read by foreach: " + elem);
          }
        });
  }
}
