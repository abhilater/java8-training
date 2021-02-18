package java8.training.reflectionapi.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;
import java8.training.reflectionapi.reflection.annotation.Inject;
import java8.training.reflectionapi.reflection.util.ColumnField;
import java8.training.reflectionapi.reflection.util.Metamodel;

public class ManagedEntityManager<T> implements EntityManager<T> {

  private AtomicLong ideGenerator = new AtomicLong(0L);

  @Inject
  private Connection connection;

  @Override
  public void persist(T t) throws SQLException, IllegalAccessException {

    Metamodel<T> metamodel = Metamodel.of(t.getClass());
    String sql = metamodel.buildInsertRequest();

    try (PreparedStatement statement = preparedStatementWith(sql).addParameters(t)) {
      statement.executeUpdate();
    }
  }

  @Override
  public T find(Class<T> clss, Object primaryKey)
      throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    Metamodel<T> metamodel = Metamodel.of(clss);
    String sql = metamodel.builSelectByPrimaryKeyRequest();

    try (PreparedStatement statement = preparedStatementWith(sql).addPrimaryKey(primaryKey);
        ResultSet resultSet = statement.executeQuery()) {
      return buildInstanceFrom(clss, resultSet);
    }
  }

  private T buildInstanceFrom(Class<T> clss, ResultSet resultSet)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
    Metamodel<T> metamodel = Metamodel.of(clss);
    T t = clss.getConstructor().newInstance();
    resultSet.next();

    Field pkField = metamodel.getPrimaryKey().getField();
    Class<?> pktype = metamodel.getPrimaryKey().getType();
    if (pktype == long.class) {
      pkField.setAccessible(true);
      pkField.set(t, resultSet.getInt(metamodel.getPrimaryKey().getName()));
    }

    for (int colIdx = 0; colIdx < metamodel.getColumns().size(); colIdx++) {
      ColumnField columnField = metamodel.getColumns().get(colIdx);
      Class<?> fieldType = columnField.getType();
      Field field = columnField.getField();
      field.setAccessible(true);
      if (fieldType == int.class) {
        int value = resultSet.getInt(columnField.getName());
        field.set(t, value);
      } else if (fieldType == String.class) {
        String value = resultSet.getString(columnField.getName());
        field.set(t, value);
      }
    }
    return t;
  }

  private PreparedStatementWrapper preparedStatementWith(String sql) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return new PreparedStatementWrapper(preparedStatement);
  }

  private class PreparedStatementWrapper<T> {

    private PreparedStatement preparedStatement;

    public PreparedStatementWrapper(PreparedStatement preparedStatement) {
      this.preparedStatement = preparedStatement;
    }

    public PreparedStatement addParameters(T t) throws SQLException, IllegalAccessException {
      Metamodel metamodel = Metamodel.of(t.getClass());
      Class<?> type = metamodel.getPrimaryKey().getType();
      if (type == long.class) {
        long id = ideGenerator.incrementAndGet();
        preparedStatement.setLong(1, id);
        Field field = metamodel.getPrimaryKey().getField();
        field.setAccessible(true);
        field.set(t, id);
      }
      for (int colIdx = 0; colIdx < metamodel.getColumns().size(); colIdx++) {
        ColumnField columnField = (ColumnField) metamodel.getColumns().get(colIdx);
        Class<?> fieldType = columnField.getType();
        Field field = columnField.getField();
        field.setAccessible(true);
        Object o = field.get(t);
        if (fieldType == int.class) {
          preparedStatement.setInt(colIdx + 2, (int) o);
        } else if (fieldType == String.class) {
          preparedStatement.setString(colIdx + 2, (String) o);
        }
      }
      return preparedStatement;
    }

    public PreparedStatement addPrimaryKey(Object primaryKey) throws SQLException {
      if (primaryKey.getClass() == Long.class) {
        preparedStatement.setLong(1, (long) primaryKey);
      }
      return preparedStatement;
    }
  }
}
