```mermaid
    classDiagram
        class App {
            +main(String[] args)
        }
        class ConsoleUI {
            -CarService carService
            -Scanner scanner
            +run()
            -showMenu()
            -addCar()
            -readCar()
            -findCar()
            -showAllCars()
            -compareCars()
        }
        class CarService {
            -CarRepository carRepository
            +add(Car car)
            +read(String carName) Car
            +find(String keyword) List~Car~
            +showAll() List~Car~
            +compare(String car1, String car2) String
        }
        class CarRepository {
            -String dbUrl
            -String sbUser
            -String sbPassword
            -getConnection() Connection
            +save(Car car)
            +findByName(String name) Optional~Car~
            +findByPartialName(String keyword) List~Car~
            +findAll() List~Car~
        }
        class Car {
            -String brand
            -String model
            -int year
            +getBrand() String
            +getModel() String
            +getYear() int
            +equals(Object o) boolean
            +hashCode() int
        }
        %%External data base
        class MariaDB {
            <<Database>>
            car_db
            Table: cars
        }

        ConsoleUI --> CarService : send user requests
        CarService --> CarRepository : send DB requests

        App ..> ConsoleUI : create and run
        App ..> CarService : create and inject
        App ..> CarRepository : create and inject

        ConsoleUI ..> Car : create from user input
        carService ..> Car : verify and format
        CarRepository ..> Car : map from SQL to Car Object

        CarRepository ..> MariaDB : Connect through JDBC