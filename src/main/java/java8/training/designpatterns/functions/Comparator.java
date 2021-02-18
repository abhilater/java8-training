package java8.training.designpatterns.functions;

import java.util.Objects;

@FunctionalInterface
public interface Comparator<T> {

  static <T, U extends Comparable<U>> Comparator<T> comparing(Function<T, U> keyExtractor) {
    Objects.requireNonNull(keyExtractor);
    return (t1, t2) -> keyExtractor.apply(t1).compareTo(keyExtractor.apply(t2));
  }

  int compare(T t1, T t2);

  default Comparator<T> reversed() {
    return (t1, t2) -> this.compare(t2, t1);
  }

  default Comparator<T> thenComparing(Comparator<T> cmpAge) {
    Objects.requireNonNull(cmpAge);
    return (t1, t2) -> {
      int compare = this.compare(t1, t2);
      if (compare == 0) {
        return cmpAge.compare(t1, t2);
      }
      return compare;
    };
  }

  default <U extends Comparable<U>> Comparator<T> thenComparing(Function<T, U> keyExtractor) {
    Objects.requireNonNull(keyExtractor);
    return thenComparing(Comparator.comparing(keyExtractor));
  }
}
