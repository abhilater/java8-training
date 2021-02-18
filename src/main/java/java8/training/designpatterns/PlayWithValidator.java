package java8.training.designpatterns;

import java8.training.designpatterns.model.Person;
import java8.training.designpatterns.util.Validator;

public class PlayWithValidator {

  public static void main(String[] args) {
    // business validation name must not be null and agent > 0 and < 150
    Person sarah = new Person("Sarah", 29);
    Person james = new Person(null, 29);
    Person mary = new Person("Mary", -10);
    Person john = new Person("John", 1000);
    Person linda = new Person(null, 1_000);

    // factory method to initialize the validator
    sarah =
        Validator.validation(p -> p.getName() != null, "The name should not be null")
            .thenValidation(p -> p.getAge() > 0, "Age should be gt 0")
            .thenValidation(p -> p.getAge() < 150, "Age should be lt 150")
            .on(sarah)
            .validate();
    System.out.println(sarah);

//    mary =
//        Validator.validation(p -> p.getName() != null, "The name should not be null")
//            .thenValidation(p -> p.getAge() > 0, "Age should be gt 0")
//            .thenValidation(p -> p.getAge() < 150, "Age should be lt 150")
//            .on(mary)
//            .validate();
//    System.out.println(mary);

//    john =
//        Validator.validation(p -> p.getName() != null, "The name should not be null")
//            .thenValidation(p -> p.getAge() > 0, "Age should be gt 0")
//            .thenValidation(p -> p.getAge() < 150, "Age should be lt 150")
//            .on(john)
//            .validate();
//    System.out.println(john);

    linda =
        Validator.validation(p -> p.getName() != null, "The name should not be null")
            .thenValidation(p -> p.getAge() > 0, "Age should be gt 0")
            .thenValidation(p -> p.getAge() < 150, "Age should be lt 150")
            .on(linda)
            .validate();
    System.out.println(linda);


  }
}
