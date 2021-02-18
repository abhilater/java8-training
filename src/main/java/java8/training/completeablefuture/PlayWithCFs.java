package java8.training.completeablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java8.training.completeablefuture.model.Car;

public class PlayWithCFs {

  static ExecutorService executorService = Executors.newFixedThreadPool(5);

  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    CompletableFuture<Void> start = new CompletableFuture<>();

    Supplier<CompletableFuture<List<Car>>> supplier = () -> fetchCars();
    Function<List<Car>, CompletableFuture<List<Car>>> populateRatings = cars -> {
      List<CompletableFuture<Car>> updatedCars = cars.stream()
          .map(carWithRating()).collect(Collectors.toList());

      CompletableFuture<Void> allFutures = CompletableFuture
          .allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]));

      CompletableFuture<List<Car>> allCompletableFuture = allFutures.thenApply(future -> {
        return updatedCars.stream()
            .map(completableFuture -> completableFuture.join())
            .collect(Collectors.toList());
      });
      return allCompletableFuture;
    };

//    start
//        .thenCompose(nil -> supplier.get())
//        .thenCompose(populateRatings)
//        .thenAccept(cars -> {
//          cars.forEach(System.out::println);
//          long endTime = System.currentTimeMillis();
//          System.out.println("Took " + (endTime - startTime) + " ms.");
//        });
//    start.complete(null);

//    fetchCars()
//        .thenCompose(populateRatings)
//        .thenAccept(cars -> {
//          cars.forEach(System.out::println);
//          long endTime = System.currentTimeMillis();
//          System.out.println("Took " + (endTime - startTime) + " ms.");
//        });

//    fetchCars()
//        .thenCompose(populateRatings)
//        .whenComplete((cars, th) -> {
//          if (th != null) {
//            throw new RuntimeException(th);
//          }
//          cars.forEach(System.out::println);
//          long endTime = System.currentTimeMillis();
//          System.out.println("Took " + (endTime - startTime) + " ms.");
//        });

    fetchCars()
        .thenCompose(populateRatings)
        .whenComplete((cars, th) -> {
          System.out.println("Thread = [ " + Thread.currentThread().getName() + " ]");
          if (th != null) {
            throw new RuntimeException(th);
          }
          cars.forEach(System.out::println);
        }).join();
    System.out.println("Thread = [ " + Thread.currentThread().getName() + " ]");
    long endTime = System.currentTimeMillis();
    System.out.println("Took " + (endTime - startTime) + " ms.");

    //Thread.sleep(2000);
  }

  private static Function<Car, CompletableFuture<Car>> carWithRating() {
    return car -> fetchCarRating(car.getManufacturerId())
        .thenApplyAsync(r -> {
          System.out.println("Thread apply = [ " + Thread.currentThread().getName() + " ]");
          car.setRating(r);
          return car;
        });
  }

  private static CompletableFuture<Float> fetchCarRating(int manufacturer) {
    return CompletableFuture.supplyAsync(() -> {
      System.out.println("Thread rating = [ " + Thread.currentThread().getName() + " ]");
      simulateDelay();
      switch (manufacturer) {
        case 2:
          return 4f;
        case 3:
          return 4.1f;
        case 7:
          return 4.2f;
        default:
          return 5f;
      }
    }).exceptionally(th -> -1f);
  }

  private static CompletableFuture<List<Car>> fetchCars() {

    List<Car> cars = new ArrayList<>();
    cars.add(new Car(1, 3, "Fiesta", 2017));
    cars.add(new Car(2, 7, "Camry", 2014));
    cars.add(new Car(3, 2, "M2", 2008));
    cars.add(new Car(4, 2, "M3", 2008));
    return CompletableFuture.supplyAsync(() -> {
      System.out.println("Thread cars = [ " + Thread.currentThread().getName() + " ]");
      simulateDelay();
      return cars;
    });
  }

  public static void simulateDelay() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void test1() {
    System.out.println("Test1");
  }

  public void test2() {
    System.out.println("Test2");
  }
}
