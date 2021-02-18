package java8.training.designpatterns;

import java8.training.designpatterns.functions.Function;
import java8.training.designpatterns.model.Meteo;

public class PlayWithFunctions {

  public static void main(String[] args) {
    Meteo meteo = new Meteo(20);

    Function<Meteo, Integer> readCelsius = m -> m.getTemperature();
    Function<Integer, Double> celsiustoFahrenheit = t -> t * 9d / 5d + 32d;
    // function chaining (not composition)
    Function<Meteo, Double> readFahrenheit = readCelsius.andThen(celsiustoFahrenheit);
    System.out.println("Meteo is F " + readFahrenheit.apply(meteo));

    // composing functions is different than chaining (it is just inverting the calls)
    // composing is only possible for Functions and not for Consumers(void) and Predicates(boolean)
    readFahrenheit = celsiustoFahrenheit.compose(readCelsius);
    System.out.println("Meteo is F " + readFahrenheit.apply(meteo));
  }

}
