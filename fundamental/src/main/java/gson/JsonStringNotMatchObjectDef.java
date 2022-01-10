package gson;

import com.google.gson.Gson;

public class JsonStringNotMatchObjectDef {

  public static void main(String[] args) {
    Gson gson = new Gson();
    Athlete athlete =
        gson.fromJson("{\"title\":\"How to read a book\", \"author\":\"J\"}", Athlete.class);
    System.out.println(String.format("Athlete{name=%s, gender=%s}", athlete.name, athlete.gender));
  }

  static class Book {
    String title;
    String author;
  }

  static class Athlete {
    String name;
    String gender;
  }
}
