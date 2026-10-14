package org.example;

/**
 * Main class launching a program.
 */
public final class App {

  private App() {}
  /**
   * Main function.
   */

  public static void main(String[] args) {
    CarRepository carRepository = new CarRepository();
    CarService carService = new CarService(carRepository);
    ConsoleUi ui = new ConsoleUi(carService);

    ui.run();
  }
}
