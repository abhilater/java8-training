package java8.training.designpatterns;

import java8.training.designpatterns.functions.Consumer;

public class PlayWithConsumers {

  public static void main(String[] args) {
    Consumer<String> c1 = s -> System.out.println("c1 -> " + s);
    Consumer<String> c2 = s -> System.out.println("c2 -> " + s);
    // chain c1 and c2
    Consumer<String> c3 = c1.andThen(c2);
    c3.accept("Hello");
    c3.andThen(s -> System.out.println("c3 -> " + s)).accept("Hi");

  }
}
