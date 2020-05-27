import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsolePad{
  private static Connection connection = null;
  private static Statement statement = null;
  private static ResultSet resultSet = null;
  private static final String connection_url = "jdbc:mysql://localhost:3306/demo?useSSL=false"; 
  private static final String username = "test";
  private static final String pass = "test";
  private static String sqlSelect = "select password from padusers where username=";
  public static void main(String[] args) throws SQLException {
    out.println("\n" + " ===== Welcome to CONSOLE PAD =====");
    Scanner scanner = new Scanner(in);
    out.print("\n" + " Enter Username: ");
    String uname = scanner.nextLine();
    out.print("\n" + " Enter Password: ");
    String pword = scanner.nextLine();
    try {
      connection = DriverManager.getConnection(connection_url, username, pass);
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sqlSelect + "'" + uname.trim() + "'");
      if (resultSet.next()) {
        if (checkPassword(resultSet.getString("password"), pword)) {
          openPad(uname);
        }
        else {
          out.println(" Invalid password");
        }
      }
      else {
        out.println(" Invalid username");
      }
    }
    catch (SQLException sqlException) {
      out.println("Invalid URL / credentials" + "\n");
      sqlException.printStackTrace();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
    finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  public static boolean checkPassword(String dbPass, String uPass) {
    if (dbPass.equals(uPass)) {
      return true;
    }
    else {
      return false;
    }
  }

  public static void openPad(String name) throws SQLException{
    Scanner scanner = new Scanner(in);
    out.println("\n " + " ===== " + name + "'s Pad" + " =====");
    out.print("\n 1. View Notes" + "\n 2. Add Notes" + "\n ");
    int option = scanner.nextInt();
    switch (option) {
      case 1:
        try {
          connection = DriverManager.getConnection(connection_url, username, pass);
          statement = connection.createStatement();
          resultSet = statement.executeQuery("select notes from notes where username=" + "'" + name.trim() + "'");
          while (resultSet.next()) {
            out.println("==================");
            out.println(" " + resultSet.getString("notes") + "\n");
          }
        }
        catch (SQLException sqlException) {
          out.println("Invalid URL / credentials" + "\n");
          sqlException.printStackTrace();
        }
        catch (Exception exception) {
          exception.printStackTrace();
        }
        finally {
          if (resultSet != null) {
            resultSet.close();
          }
          if (statement != null) {
            statement.close();
          }
          if (connection != null) {
            connection.close();
          }
        }
        break;
      case 2:
        try {
          connection = DriverManager.getConnection(connection_url, username, pass);
          statement = connection.createStatement();
          Scanner scanner1 = new Scanner(in);
          out.println("\n " + "===== INSERT MODE ===== ");
          out.println(" Press ENTER to exit the insert mode" +"\n ");
          String text = scanner1.nextLine();
          int rows = statement.executeUpdate("insert into notes values(" + "'" + name.trim() + "'" + "," + "'" + text.trim() + "'" + ")");
          if (rows > 0) {
            out.println("\n " + " Text saved successfully.");
          }
        }
        catch (SQLException sqlException) {
          out.println("Invalid URL / credentials" + "\n");
          sqlException.printStackTrace();
        }
        catch (Exception exception) {
          exception.printStackTrace();
        }
        finally {
          if (resultSet != null) {
            resultSet.close();
          }
          if (statement != null) {
            statement.close();
          }
          if (connection != null) {
            connection.close();
          }
        }
        break;
    }
  }
}