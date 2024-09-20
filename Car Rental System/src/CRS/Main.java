package CRS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
	private String carId;
	private String brand;
	private String model;
	private double basePricePerDay;
	private boolean isAvailible;

	public Car(String carId, String brand, String model, double basePricePerDay) {
		this.carId = carId;
		this.brand = brand;
		this.model = model;
		this.basePricePerDay = basePricePerDay;
		this.isAvailible = true;

	}

	public String getCarId() {
		return carId;
	}

	public String getbrand() {
		return brand;
	}

	public String getmodel() {
		return model;
	}

	public double calculatePrice(int rentalDays) {
		return basePricePerDay * rentalDays;
	}

	public void rent() {
		isAvailible = false;
	}

	public void returnCar() {
		isAvailible = true;
	}

	public boolean isAvailibilty() {
		return isAvailible;
	}

}

class Customer {
	private String CustomerId;
	private String name;

	public Customer(String CustomerId, String name) {
		this.CustomerId = CustomerId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCustomerId() {
		return CustomerId;
	}

}

class Rental {
	private Car car;
	private Customer customer;
	private int days;

	public Rental(Car car, Customer customer, int days) {
		this.car = car;
		this.customer = customer;
		this.days = days;

	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getDays() {
		return days;
	}

}

class CarRentalSystem {

	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<Car>();
		customers = new ArrayList<Customer>();
		rentals = new ArrayList<Rental>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailibilty()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));
		} else {
			System.out.println("Car is not available for rent");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
			System.out.println("Car Return Successfully");

		} else {
			System.out.println("Car was not rented");
		}
	}

	public void main_menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("================ Car Rental System ===================");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return Car");
			System.out.println("3. Exit");
			System.out.print("Enter Your Choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			if (choice == 1) {
				System.out.println("Rent a car");
				System.out.print("Enter your name: ");
				String customerName = sc.nextLine();

				System.out.println("Available Cars");
				for (Car car : cars) {
					if (car.isAvailibilty()) {
						System.out.println(car.getCarId() + " - " + car.getbrand() + "  " + car.getmodel());

					}
				}
				System.out.print("Enter the car id you want to rent: ");
				String carId = sc.nextLine();

				System.out.print("Enter the numer of days for rental: ");
				int rentalDays = sc.nextInt();
				sc.nextLine(); // Consume newline

				Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailibilty()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println("Rental Information");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectedCar.getbrand() + " " + selectedCar.getmodel());
					System.out.println("Rental Day: " + rentalDays);
					System.out.println("Total Price: " + totalPrice);

					System.out.println("Confirm Rental (Y/N) : ");
					String confirm = sc.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, rentalDays);
						System.out.println("\nCar rented successfully.");
					} else {
						System.out.println("\nRental canceled.");
					}
				} else {
					System.out.println("\nInvalid car selection or car not available for rent.");
				}

			} else if (choice == 2) {
				System.out.println("\n== Return a Car ==\n");
				System.out.print("Enter the car ID you want to return: ");
				String carId = sc.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailibilty()) {
						carToReturn = car;
						break;
					}
				}

				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCustomer();
							break;
						}
					}

					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Car returned successfully by " + customer.getName());
					} else {
						System.out.println("Car was not rented or rental information is missing.");
					}
				} else {
					System.out.println("Invalid car ID or car is not rented.");
				}
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}

		System.out.println("\nThank you for using the Car Rental System!");
	}

}

public class Main {

	public static void main(String[] args) {
		CarRentalSystem rentalSystem = new CarRentalSystem();
		Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
		Car car2 = new Car("C002", "Honda", "Accord", 70.0);
		Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
		rentalSystem.addCar(car1);
		rentalSystem.addCar(car2);
		rentalSystem.addCar(car3);

		rentalSystem.main_menu();
	}

}