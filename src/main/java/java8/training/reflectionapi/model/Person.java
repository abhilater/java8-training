package java8.training.reflectionapi.model;

import java8.training.reflectionapi.reflection.annotation.Column;
import java8.training.reflectionapi.reflection.annotation.PrimaryKey;

public class Person {

  @PrimaryKey(name = "k_id")
  private long id;

  @Column(name = "c_name")
  private String name;

  @Column(name = "c_age")
  private int age;

  public Person() {
  }

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}
