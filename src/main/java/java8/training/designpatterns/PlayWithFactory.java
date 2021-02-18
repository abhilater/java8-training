package java8.training.designpatterns;

import java.awt.Color;
import java.util.List;
import java8.training.designpatterns.functions.Factory;
import java8.training.designpatterns.model.Circle;

public class PlayWithFactory {

  public static void main(String[] args) {

//    Supplier<Circle> factory = () -> new Circle();
//    Circle circle = factory.get();
//    Factory<Circle> factory = () -> new Circle(); // tight coupling

    Factory<Circle> factory1 = Factory.createFactory(Circle::new);
    Factory<Circle> factory2 = Factory.createFactory(color -> new Circle(color), Color.RED);
    factory2 = Factory.createFactory(Circle::new, Color.RED); // example of Partial applications
    // if you are not happy with get() and wan't something like newInstance()
    // you can extend Supplier to create a new Factory interface
    Circle circle = factory1.newInstance();
    System.out.println("Circle = " + circle);

    // add more helper factory methods using default methods
    List<Circle> circles = factory2.create5();
    System.out.println(circles);


  }
}
