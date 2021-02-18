package java8.training.designpatterns;

import java8.training.designpatterns.functions.Comparator;
import java8.training.designpatterns.functions.Function;
import java8.training.designpatterns.model.Person;

public class PlayWithComparators {

  public static void main(String[] args) {
    Person mary = new Person("Mary", 28);
    Person john = new Person("John", 22);
    Person linda = new Person("Linda", 26);
    Person james = new Person("James", 32);
    Person jamesYoung = new Person("James", 28);

    Function<Person, String> getName = p -> p.getName();
    Function<Person, Integer> getAge = p -> p.getAge();
    //Comparator<Person> cmpName = (p1,  p2) -> p1.getName().compareTo(p2.getName());
    Comparator<Person> cmpName = Comparator.comparing(getName);
    Comparator<Person> cmpAge = Comparator.comparing(getAge);

    // comparator reversed
    Comparator<Person> cmpNameReversed = cmpName.reversed();

    // chain/combine or compose comparators cmpName and cmpAge
    Comparator<Person> cmpNameThenAge = cmpName.thenComparing(cmpAge);

    // make the API design better by enhancing comparing factory method
    Comparator<Person> cmp =
        Comparator.comparing(Person::getName)
            .thenComparing(Person::getAge);

    System.out.println("Mary > John : " + (cmp.compare(mary, john) > 0));
    System.out.println("John > James : " + (cmp.compare(john, james) > 0));
    System.out.println("Linda > John : " + (cmp.compare(linda, john) > 0));
    System.out.println("JamesYoung > James : " + (cmp.compare(jamesYoung, james) > 0));
  }

}
