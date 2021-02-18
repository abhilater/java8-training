package java8.training.completeablefuture.model;

public class Car {

  int id;
  int manufacturerId;
  String model;
  int year;
  float rating;

  public Car(int id, int manufacturerId, String model, int year) {
    this.id = id;
    this.manufacturerId = manufacturerId;
    this.model = model;
    this.year = year;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getManufacturerId() {
    return manufacturerId;
  }

  @Override
  public String toString() {
    return "Car (id=" + id + ", manufacturerId=" + manufacturerId + ", model=" + model + ", year="
        + year
        + ", rating=" + rating;
  }
}
