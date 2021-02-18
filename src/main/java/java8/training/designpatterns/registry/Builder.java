package java8.training.designpatterns.registry;

import java8.training.designpatterns.functions.Factory;

@FunctionalInterface
public interface Builder<T> {

  // record in a map that this label belongs this factory
  void register(String label, Factory<T> factory);
}
