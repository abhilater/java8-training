package java8.training.reflectionapi.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java8.training.reflectionapi.reflection.annotation.Provides;

public class H2ConnectionProvider {

  @Provides
  public Connection buildConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
  }
}
