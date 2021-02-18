package java8.training.designpatterns;

import java8.training.designpatterns.functions.Function;

public class PlayWithIdentity {

  public static void main(String[] args) {
    //Function<String, String> identity = s -> s;
    Function<String, String> identity = Function.identity();

    System.out.println(identity.apply("Hi"));
  }

}
