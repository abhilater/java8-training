package java8.training.reflectionapi.reflection.util;

import java.lang.reflect.Field;
import java8.training.reflectionapi.reflection.annotation.PrimaryKey;

public class PrimaryKeyField {

  private Field field;
  private PrimaryKey primaryKey;

  public PrimaryKeyField(Field field) {
    this.field = field;
    this.primaryKey = field.getAnnotation(PrimaryKey.class);
  }

  public String getName() {
    return primaryKey.name();
  }

  public Class<?> getType() {
    return field.getType();
  }

  public Field getField() {
    return field;
  }
}
