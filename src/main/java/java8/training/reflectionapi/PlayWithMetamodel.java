package java8.training.reflectionapi;

import java.util.List;
import java8.training.reflectionapi.beanmanager.BeanManager;
import java8.training.reflectionapi.model.Person;
import java8.training.reflectionapi.orm.EntityManager;
import java8.training.reflectionapi.orm.ManagedEntityManager;
import java8.training.reflectionapi.reflection.util.ColumnField;
import java8.training.reflectionapi.reflection.util.Metamodel;
import java8.training.reflectionapi.reflection.util.PrimaryKeyField;

public class PlayWithMetamodel {

  public static void main(String[] args) throws Exception {

    Metamodel<Person> metamodel = Metamodel.of(Person.class);
    PrimaryKeyField primaryKeyField = metamodel.getPrimaryKey();
    List<ColumnField> columnFields = metamodel.getColumns();

    System.out.println(
        "Primary key name = " + primaryKeyField.getName() + ", type = " + primaryKeyField.getType()
            .getSimpleName());
    columnFields.forEach(c -> System.out
        .println("Column name = " + c.getName() + ", type = " + c.getType().getSimpleName()));
    System.out.println(metamodel.buildInsertRequest());

//    Person john = new Person("John", 29);
//    Person linda = new Person("Linda", 30);
//    Person james = new Person("James", 31);
//    Person susan = new Person("Susan", 32);
//
//    EntityManager<Person> entityManager = EntityManager.of(Person.class);
//    entityManager.persist(john);
//    entityManager.persist(linda);
//    entityManager.persist(james);
//    entityManager.persist(susan);
//
//    System.out.println(john);
//    System.out.println(linda);
//    System.out.println(james);
//    System.out.println(susan);

    BeanManager beanManager = BeanManager.getInstance();
    EntityManager<Person> entityManager = beanManager.getInstance(ManagedEntityManager.class);
//    entityManager.persist(john);
//    entityManager.persist(linda);
//    entityManager.persist(james);
//    entityManager.persist(susan);

    Person john = entityManager.find(Person.class, 1L);
    Person linda = entityManager.find(Person.class, 2L);
    Person james = entityManager.find(Person.class, 3L);
    Person susan = entityManager.find(Person.class, 4L);
    System.out.println(john);
    System.out.println(linda);
  }
}
