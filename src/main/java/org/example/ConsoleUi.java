package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * ConsoleUi - manage CLI interface.
 */
public class ConsoleUi {
  private final CarService carService;
  private final Scanner scanner;

  public ConsoleUi(CarService carService) {
    this.carService = carService;
    this.scanner = new Scanner(System.in);
  }

  /**
   * Run main loop in console.
   */
  public void run() {
    boolean running = true;
    while (running) {
      showMenu();
      System.out.print("> ");
      String input = scanner.nextLine().trim();

      try {
        switch (input) {
          case "1" -> addCar();
          case "2" -> readCar();
          case "3" -> findCar();
          case "4" -> showAllCars();
          case "5" -> compareCars();
          case "0" -> {
            running = false;
            System.out.println("Quiting...");
          }
          default -> System.out.println("Unknown option");
        }
      } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
      } catch (Exception e) {
        System.err.println("Unexpected error occured: " + e.getMessage());
      }
      System.out.println();
    }
  }

  private void showMenu() {
    System.out.println("1 - Add new car");
    System.out.println("2 - Display car specification");
    System.out.println("3 - Search for cars with provided keyword in name");
    System.out.println("4 - Display all cars");
    System.out.println("5 - Compare two cars");
    System.out.println("0 - Quit");
  }

  private void addCar() {
    System.out.println("Brand: ");
    String brand = scanner.nextLine().trim();

    System.out.println("Model: ");
    String model = scanner.nextLine().trim();

    System.out.println("Year: ");
    int year = Integer.parseInt(scanner.nextLine().trim());

    Car car = new Car(brand, model, year);
    carService.add(car);
    System.out.println("Car added");
  }

  private void readCar() {
    System.out.println("Exact model: ");
    String model = scanner.nextLine().trim();
    Car car = carService.read(model);
    System.out.println("Found: " + car.toString());
  }

  private void findCar() {
    System.out.println("Keyword to search by: ");
    String keyword = scanner.nextLine().trim();
    List<Car> found = carService.find(keyword);

    if (found.isEmpty()) {
      System.out.println("No matching car for " + keyword);
    } else {
      System.out.println("Search results:");
      found.forEach(car -> System.out.println("- " + car.toString()));
    }
  }

  private void showAllCars() {
    List<String> cars = carService.showAll();
    if (cars.isEmpty()) {
      System.out.println("No cars saved");
    } else {
      System.out.println("All cars:");
      cars.forEach(System.out::println);
    }
  }

  private void compareCars() {
    System.out.println("Exact model of the first car: ");
    String carName1 = scanner.nextLine().trim();

    System.out.println("Exact model of the second car: ");
    String carName2 = scanner.nextLine().trim();

    System.out.println("\n" + carService.compare(carName1, carName2));
  }
}
