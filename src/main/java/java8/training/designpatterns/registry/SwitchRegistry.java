package java8.training.designpatterns.registry;

import java8.training.designpatterns.functions.Factory;
import java8.training.designpatterns.model.Rectangle;
import java8.training.designpatterns.model.Shape;
import java8.training.designpatterns.model.Square;
import java8.training.designpatterns.model.Triangle;

public class SwitchRegistry {

  // this is a static builder all objects known at compile time, we want a dynamic one using maps
  public Factory<? extends Shape> buildShapeFactory(String shape) {
    switch (shape) {
      case "square":
        return () -> new Square();
      case "triangle":
        return () -> new Triangle();
      case "rectangle":
        return () -> new Rectangle();
      default:
        throw new IllegalArgumentException("Unknown shape: " + shape);
    }
  }
}
