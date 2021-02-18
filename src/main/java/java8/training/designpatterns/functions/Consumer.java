package java8.training.designpatterns.functions;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> {

  void accept(T t);

  default Consumer<T> andThen(Consumer<T> c2) {
    Objects.requireNonNull(c2);
    return (T t) -> {
      this.accept(t);
      c2.accept(t);
    };
  }
}
