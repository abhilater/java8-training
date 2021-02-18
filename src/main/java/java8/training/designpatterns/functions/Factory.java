package java8.training.designpatterns.functions;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Factory<T> extends Supplier<T> {

  static <T> Factory<T> createFactory(Supplier<T> supplier) {
    return () -> supplier.get();
  }

  static <T, R> Factory<T> createFactory(Function<R, T> constructor, R color) {
    return () -> constructor.apply(color);
  }

  static <T> Factory<T> createFactorySingleton(Supplier<T> supplier) {
    T singleton = supplier.get();
    return () -> singleton;
  }

  default T newInstance() {
    // delegate to supplier get()
    return get();
  }

  default List<T> create5() {
    return IntStream.range(0, 5)
        .mapToObj(i -> newInstance())
        .collect(Collectors.toList());

  }
}
