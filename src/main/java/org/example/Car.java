package org.example;

import java.util.Objects;

/**
 * Car - stores brand, model and year of a car.
 */
public class Car {
  private final String brand;
  private final String model;
  private final int year;

  /**
   * Create new Car object.
   */
  public Car(String brand, String model, int year) {
    this.brand = brand;
    this.model = model;
    this.year = year;
  }

  public String getBrand() {
    return this.brand;
  }

  public String getModel() {
    return this.model;
  }

  public int getYear() {
    return this.year;
  }

  @Override
  public String toString() {
    return this.brand + " " + this.model + " (" + this.year + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Car car = (Car) o;
    return year == car.year
        && Objects.equals(this.brand, car.brand)
        && Objects.equals(this.model, car.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.brand, this.model, this.year);
  }
}
