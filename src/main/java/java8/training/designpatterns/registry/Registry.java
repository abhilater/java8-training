package java8.training.designpatterns.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java8.training.designpatterns.functions.Factory;
import java8.training.designpatterns.model.Shape;

public interface Registry {

  static Registry createRegistry(Consumer<Builder<Shape>> consumer) {
    Map<String, Factory<Shape>> map = new HashMap<>();
    Builder<Shape> builder = (label, factory) -> map.put(label,
        factory); // map.put is not directly implemented in consumer as consumer is client code
    consumer.accept(builder); // implementation is builder.register("rectangle", Rectangle::new);
    // and implementation of builder.register is map.put(label, factory);

    return shape -> map.computeIfAbsent(shape, s -> {
      throw new IllegalArgumentException("Not a shape " + s);
    });
  }

  Factory<? extends Shape> buildShapeFactory(String shape);
}
