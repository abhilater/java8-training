package java8.training.reflectionapi.db;

import java.sql.SQLException;
import org.h2.tools.Server;

public class DBLauncher {

  public static void main(String[] args) throws SQLException {
    Server.main();
    System.out.println("DB Launched");
  }

}
