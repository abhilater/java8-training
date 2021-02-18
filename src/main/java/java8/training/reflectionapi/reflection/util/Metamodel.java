package java8.training.reflectionapi.reflection.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java8.training.reflectionapi.reflection.annotation.Column;
import java8.training.reflectionapi.reflection.annotation.PrimaryKey;

public class Metamodel<T> {


  private Class<T> clss;

  public Metamodel(Class<T> clss) {
    this.clss = clss;
  }

  public static <T> Metamodel of(Class<T> clss) {
    return new Metamodel(clss);
  }

  public PrimaryKeyField getPrimaryKey() {
    Field[] fields = clss.getDeclaredFields();
    return Arrays.stream(fields)
        .filter(f -> f.getAnnotation(PrimaryKey.class) != null)
        .map(f -> new PrimaryKeyField(f))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Primary key not found"));
  }

  public List<ColumnField> getColumns() {
    Field[] fields = clss.getDeclaredFields();
    return Arrays.stream(fields)
        .filter(f -> f.getAnnotation(Column.class) != null)
        .map(f -> new ColumnField(f))
        .collect(Collectors.toList());
  }

  public String buildInsertRequest() {
    // insert into Person (id, name, age) values (?, ?, ?)
    String columnQuery = buildColumnElement();
    String paramsQuery = buildQuestionMarkElement();

    return "insert into " + this.clss.getSimpleName() + "(" + columnQuery + ")"
        + " values (" + paramsQuery + ")";
  }

  private String buildQuestionMarkElement() {
    return IntStream.range(0, getColumns().size() + 1).mapToObj(i -> "?")
        .collect(Collectors.joining(", "));
  }

  private String buildColumnElement() {
    PrimaryKeyField primaryKeyField = getPrimaryKey();
    List<String> columnNames = getColumns().stream()
        .map(ColumnField::getName)
        .collect(Collectors.toList());
    columnNames.add(0, primaryKeyField.getName());
    return String.join(", ", columnNames);
  }

  public String builSelectByPrimaryKeyRequest() {
    // select id, name, age from Person where id = ?
    String columnElement = buildColumnElement();
    return "select " + columnElement + " from " + this.clss.getSimpleName() +
        " where " + getPrimaryKey().getName() + " = ?";
  }
}
