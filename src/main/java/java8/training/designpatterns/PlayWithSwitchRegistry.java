package java8.training.designpatterns;

import java.util.function.Consumer;
import java8.training.designpatterns.functions.Factory;
import java8.training.designpatterns.model.Rectangle;
import java8.training.designpatterns.model.Shape;
import java8.training.designpatterns.model.Square;
import java8.training.designpatterns.model.Triangle;
import java8.training.designpatterns.registry.Builder;
import java8.training.designpatterns.registry.Registry;

public class PlayWithSwitchRegistry {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
//    SwitchRegistry registry = new SwitchRegistry();

//    Factory<Rectangle> rectangleFactory = (Factory<Rectangle>) registry.buildShapeFactory("rectangle");
//    System.out.println("Rectangle : "+ rectangleFactory.newInstance());

    Consumer<Builder<Shape>> consumer1 = builder -> builder.register("rectangle", Rectangle::new);
    Consumer<Builder<Shape>> consumer2 = builder -> builder.register("square", Square::new);
    Consumer<Builder<Shape>> consumer3 = builder -> builder.register("triangle", Triangle::new);
    Consumer<Builder<Shape>> initializer = consumer1.andThen(consumer2).andThen(consumer3);

    Registry registry = Registry.createRegistry(initializer);

    Factory<Rectangle> rectangleFactory =
        (Factory<Rectangle>) registry.buildShapeFactory("rectangle");

    Rectangle rectangle = rectangleFactory.newInstance();
    System.out.println("Rectangle = " + rectangle);

    Factory<Triangle> triangleFactory =
        (Factory<Triangle>) registry.buildShapeFactory("triangle");

    Triangle triangle = triangleFactory.newInstance();
    System.out.println("Triangle = " + triangle);

  }
}
