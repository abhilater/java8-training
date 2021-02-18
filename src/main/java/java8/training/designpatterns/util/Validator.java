package java8.training.designpatterns.util;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java8.training.designpatterns.model.Person;

@FunctionalInterface
public interface Validator {

  static Validator validation(Predicate<Person> predicate, String errMessage) {
    return p -> {
      if (predicate.test(p)) {
        return () -> p;
      } else {
        return () -> {
          ValidationException exception = new ValidationException("The object is not valid");
          exception.addSuppressed(new IllegalArgumentException(errMessage));
          throw exception;
        };
      }
    };
  }

  ValidatorSupplier on(Person p);

  default Validator thenValidation(Predicate<Person> predicate, String errMessage) {
    return p -> {
      try {
        on(p).validate();
        if (predicate.test(p)) {
          return () -> p;
        } else {
          return () -> {
            ValidationException exception = new ValidationException("The object is not valid");
            exception.addSuppressed(new IllegalArgumentException(errMessage));
            throw exception;
          };
        }
      } catch (ValidationException e) {
        if (predicate.test(p)) {
          return () -> {
            throw e;
          };
        } else {
          e.addSuppressed(new IllegalArgumentException(errMessage));
          return () -> {
            throw e;
          };
        }
      }
    };
  }

  interface ValidatorSupplier extends Supplier<Person> {

    default Person validate() {
      return get();
    }
  }

  class ValidationException extends RuntimeException {

    public ValidationException(String errMessage) {
      super(errMessage);
    }
  }
}
