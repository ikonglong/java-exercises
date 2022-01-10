package guava;

import com.google.common.base.MoreObjects;

public class ToString {
  public static void main(String[] args) {
    Book b = new Book();
    b.title = "分析模式";
    b.author = "Martin Fowler";
    System.out.println(b);
  }

  static class Book {
    String title;
    String author;

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this).add("title", title).add("author", author).toString();
    }
  }
}
