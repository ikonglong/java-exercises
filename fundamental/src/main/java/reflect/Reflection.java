package reflect;

public class Reflection {

  public static void main(String[] args) {
    Book[] books = new Book[] {new Book()};
    System.out.println(books.getClass());

    Double[] doubleWrapperArray = new Double[] {1d};
    System.out.println(doubleWrapperArray.getClass());
    System.out.println(doubleWrapperArray.getClass().getComponentType());
    System.out.println(doubleWrapperArray.getClass().getComponentType() == Double.class);

    double[] doubleArray = new double[] {1d};
    System.out.println(doubleArray.getClass());
    System.out.println(doubleArray.getClass().getComponentType());
    System.out.println(doubleArray.getClass().getComponentType() == double.class);

    double a = 12d;
    System.out.println(((Double) a).getClass());
    Object b = a;
    System.out.println(b instanceof Double);
  }

  public static class Book {}
}
