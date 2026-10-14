package org.example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CarService - handle on user requests as DB requests.
 */
public class CarService {
  private final CarRepository carRepository;

  /**
   * Inject carRepository.
   *
   * @param carRepository CarRepository object.
   */
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  /**
   * Send add request.
   * Do not check for duplicates.
   *
   * @param car Car object.
   */
  public void add(Car car) {
    if (car.getBrand() == null || car.getBrand().trim().isEmpty()) {
      throw new IllegalArgumentException("Car brand cannot be empty");
    }
    if (car.getYear() < 1700) {
      throw new IllegalArgumentException("Production year cannot be lower than 1700");
    }
    carRepository.save(car);
  }

  /**
   * Send read request.
   *
   * @param carName Car model. 
   * @return Found car.
   */
  public Car read(String carName) {
    Optional<Car> foundCar = carRepository.findByName(carName);
    return foundCar.orElseThrow(() -> 
      new IllegalArgumentException("Car named " + carName + " not found"));
  }

  /**
   * Find car by keyword.
   *
   * @param keyword Keyword to search by.
   * @return List of matching cars.
   */
  public List<Car> find(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
      throw new IllegalArgumentException("Keyword cannot be empty");
    }
    return carRepository.findByPartialName(keyword);
  }

  /**
   * Select all cars.
   *
   * @return List of all cars.
   */
  public List<String> showAll() {
    List<Car> cars = carRepository.findAll();
    return cars.stream().map(Car::toString).collect(Collectors.toList());
  }

  /**
   * Compare two cars.
   * Search for cars by model.
   *
   * @param carName1 First car's model.
   * @param carName2 Second car's model.
   * @return Formalized String of all cars.
   */
  public String compare(String carName1, String carName2) {
    Car car1 = read(carName1);
    Car car2 = read(carName2);

    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%-15s | %-15s | %-15s%n", "Parameter", carName1, carName2));
    sb.append(String.format("%-15s | %-15s | %-15s%n",
                            "---------------", "---------------", "---------------"));
    sb.append(String.format("%-15s | %-15s | %-15s%n", "Brand", car1.getBrand(), car2.getBrand()));
    sb.append(String.format("%-15s | %-15s | %-15s%n", "Mode", car1.getModel(), car2.getModel()));
    sb.append(String.format("%-15s | %-15s | %-15s%n", "Year", car1.getYear(), car2.getYear()));

    return sb.toString();
  }
}