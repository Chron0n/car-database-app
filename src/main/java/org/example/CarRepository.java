package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CarRepository - handle DB requests.
 */
public class CarRepository {
  private final String dbUrl;
  private final String dbUser;
  private final String dbPassword;

  /**
   * Set url, user, password to database.
   */
  public CarRepository() {
    Dotenv dotenv = Dotenv.load();
    this.dbUrl = dotenv.get("DB_URL");
    this.dbUser = dotenv.get("DB_USER");
    this.dbPassword = dotenv.get("DB_PASSWORD");
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
  }

  /**
   * Save new car entry in database.
   */
  public void save(Car car) {
    String sql = "INSERT INTO cars (brand, model, year) VALUES (?, ?, ?)";

    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, car.getBrand());
      statement.setString(2, car.getModel());
      statement.setInt(3, car.getYear());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error: cannot save to database: " + e.getMessage());
    }
  }

  /**
   * Search by model name in database.
   *
   * @param modelName Model name of car.
   * @return Found car.
   */
  public Optional<Car> findByName(String modelName) {
    String sql = "SELECT brand, model, year FROM cars WHERE model = ?";

    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, modelName);

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          String brand = rs.getString("brand");
          String model = rs.getString("model");
          int year = rs.getInt("year");
          return Optional.of(new Car(brand, model, year));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error: Cannot read from database: " + e.getMessage());
    }
    return Optional.empty();
  }

  /**
   * Search by keyword in model names of database.
   *
   * @param keyword Part of model name.
   * @return List of matching cars.
   */
  public List<Car> findByPartialName(String keyword) {
    List<Car> cars = new ArrayList<>();
    String sql = "SELECT brand, model, year FROM cars WHERE model LIKE ?";

    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, "%" + keyword + "%");

      try (ResultSet rs = statement.executeQuery()) {
        while (rs.next()) {
          String brand = rs.getString("brand");
          String model = rs.getString("model");
          int year = rs.getInt("year");
          cars.add(new Car(brand, model, year));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error: Cannot read from database: " + e.getMessage());
    }
    return cars;
  }

  /**
   * Select all cars in database.
   *
   * @return List of all cars.
   */
  public List<Car> findAll() {
    List<Car> cars = new ArrayList<>();
    String sql = "SELECT brand, model, year FROM cars";

    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery()) {

      while (rs.next()) {
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        cars.add(new Car(brand, model, year));
      }
    } catch (SQLException e) {
      System.err.println("Error: Cannot read from database: " + e.getMessage());
    }
    return cars;
  }
}
