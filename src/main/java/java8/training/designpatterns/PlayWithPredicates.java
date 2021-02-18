package java8.training.designpatterns;

import java8.training.designpatterns.functions.Predicate;

public class PlayWithPredicates {

  public static void main(String[] args) {
    Predicate<String> notNull = s -> null != s;
    Predicate<String> notEmpty = s -> !s.isEmpty();
    Predicate<String> notNullAndNotEmpty = notNull.and(notEmpty);

    Predicate<String> isNull = notNull.negate();

    System.out.println(notNullAndNotEmpty.test("Hello"));
    System.out.println(notNullAndNotEmpty.test(""));
    System.out.println(notNullAndNotEmpty.test(null));
    System.out.println(isNull.test(null));
    System.out.println(isNull.test(""));
  }

}
