package generics;

public class Employee {
  protected String title;
  protected String name;

  public Employee(String name) {
    this("Employee", name);
  }

  Employee(String title, String name) {
    this.title = title;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("%s(%s)", title, name);
  }
}

class Manager extends Employee {
  public Manager(String name) {
    super("Manager", name);
  }

  public Manager(String title, String name) {
    super(title, name);
  }
}

class Executive extends Manager {
  public Executive(String name) {
    super("Executive", name);
  }
}
