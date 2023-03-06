package carsharing;

import carsharing.DAO.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    CompanyDaoImpl companyDao;
    CarDaoImpl carDao;
    CustomerDaoImpl customerDao;

    public Menu(CompanyDaoImpl companyDao, CarDaoImpl carDao, CustomerDaoImpl customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    static Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        printMainMenu();
    }
    public void printMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        while (true) {
            System.out.println();
            switch (choice) {
                case 1:
                    printCompanyMenu();
                    break;
                case 2:
                    chooseCustomerMenu();
                    break;
                case 3:
                    System.out.println("Enter the customer name:");
                    scanner.nextLine();
                    String nameOfCustomer = scanner.nextLine();
                    customerDao.add(new Customer(nameOfCustomer));
                    System.out.println("The customer was added!");
                    System.out.println();
                    printMainMenu();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    printMainMenu();
                    break;
            }
            choice = Integer.parseInt(scanner.next());
        }
    }

    public void printCompanyMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        while (true) {
            System.out.println();
            switch (choice) {
                case 1:
                    if (companyDao.getAll().isEmpty()) {
                        System.out.println("The company list is empty");
                        printCompanyMenu();
                    } else {
                        System.out.println("Choose the company:");
                        List<Company> companyList = companyDao.getAll();
                        for (Company c : companyList) {
                            System.out.println(c.getID() + ". " + c.getName());
                        }
                        System.out.println("0. Back");
                        scanner.nextLine();
                        int choiceOfCarCompany = scanner.nextInt();
                        if (choiceOfCarCompany == 0) {
                            printCompanyMenu();
                        }
                        for (Company company : companyList) {
                            if (company.getID() == choiceOfCarCompany) {
                                printCarMenu(company);
                            }
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Enter the company name: ");
                    scanner.nextLine();
                    String nameOfCompany = scanner.nextLine();
                    companyDao.add(new Company(nameOfCompany));
                    System.out.println("The company was created!");
                    System.out.println();
                    printCompanyMenu();
                    break;
                case 0:
                    printMainMenu();
                    break;
                default:
                    printCompanyMenu();
                    break;
            }
            choice = Integer.parseInt(scanner.next());
        }
    }

    public void printCarMenu(Company companyOfCar) {
        System.out.println("'" + companyOfCar.getName() + "'" + " company");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
        int choice = scanner.nextInt();
        while (true) {
            System.out.println();
            switch (choice) {
                case 1:
                    if (carDao.getCarsByCompanyId(companyOfCar.getID()).isEmpty()) {
                        System.out.println("The car list is empty!");
                        System.out.println();
                    } else {
                        List<Car> carList = carDao.getCarsByCompanyId(companyOfCar.getID());
                        int carIndexInList = 1;
                        for (Car c : carList) {
                            System.out.println(carIndexInList + ". " + c.getName());
                            carIndexInList++;
                        }
                        System.out.println();
                    }
                    printCarMenu(companyOfCar);
                    break;
                case 2:
                    System.out.println("Enter the car name: ");
                    scanner.nextLine();
                    String newCarName = scanner.nextLine();
                    carDao.add(new Car(newCarName, companyOfCar.getID()));
                    System.out.println("The car was added!");
                    System.out.println();
                    printCarMenu(companyOfCar);
                    break;
                case 0:
                    printCompanyMenu();
                    break;
                default:
                    System.out.println("Dont know");
                    break;
            }
            choice = Integer.parseInt(scanner.next());
        }
    }

    public void chooseCustomerMenu() {
        if (customerDao.getAll().isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            printMainMenu();
        } else {
            System.out.println("Customer list:");
            List<Customer> customerList = customerDao.getAll();
            for (Customer customer : customerList) {
                System.out.println(customer.getID() + ". " + customer.getName());
            }
            System.out.println("0. Back");
        }
        int choiceOfCustomer = scanner.nextInt();
        switch (choiceOfCustomer) {
            case 0:
                printMainMenu();
                break;
            default:
                List<Customer> customerList = customerDao.getAll();
                for (Customer customer : customerList) {
                    if (customer.getID() == choiceOfCustomer) {
                        printCustomerMenu(customer);
                    }
                }
                System.out.println();
                break;
            }
        }

        public void printCustomerMenu(Customer customer) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    if (customerDao.getRentedCar(customer.getID()).isPresent()) {
                        System.out.println("You've already rented a car!");
                        System.out.println(customer.getRentedCarID());
                        System.out.println();
                        break;
                    }
                    List<Company> companyList = companyDao.getAll();
                    if (companyList.size() > 0) {
                        System.out.println();
                        System.out.println("Choose a company:");
                        for (Company company : companyList) {
                            System.out.println(company.getID() + ". " + company.getName());
                        }
                        System.out.println("0. Back");
                        scanner.nextLine();
                        int choiceOfCompany = scanner.nextInt();
                        switch (choiceOfCompany) {
                            case 0:
                                printCompanyMenu();
                            default:
                                Integer idOfCompany = companyList.get(choiceOfCompany - 1).getID();
                                String nameOfChosenCompany = companyList.get(choiceOfCompany - 1).getName();
                                List<Car> cars = carDao.getCarsByCompanyId(idOfCompany)
                                        .stream()
                                        .filter(c -> !customerDao.isCarLocked(c.getID()))
                                        .toList();
                                if (cars.size() > 0) {
                                    System.out.println();
                                    System.out.println("Choose a car:");
                                    int counter = 1;
                                    for (Car car : cars) {
                                        System.out.println(counter + ". " + car.getName());
                                        counter++;
                                    }
                                    System.out.println("0. Back");
                                    int indexOfCar = scanner.nextInt();
                                    if (indexOfCar == 0) {
                                        System.out.println();
                                        printCustomerMenu(customer);
                                    } else {
                                        Integer carID = cars.get(indexOfCar - 1).getID();
                                        customer.setRentedCarID(carID);
                                        customerDao.update(customer);
                                        System.out.println("You rented '" + cars.get(indexOfCar - 1).getName() + "'");
                                    }
                                } else {
                                    System.out.println("No available cars in the '" +  nameOfChosenCompany + "' company");
                                }
                                System.out.println();
                                printCustomerMenu(customer);
                        }
                    }
                    break;
                case 2:
                    Optional<Car> rentedCar = customerDao.getRentedCar(customer.getID());
                    if (rentedCar.isPresent()) {
                        customer.setRentedCarID(null);
                        customerDao.update(customer);
                        System.out.println("You've returned a rented car!");
                    } else {
                        System.out.println("You didn't rent a car!");

                    }
                    break;
                case 3:
                    Optional<Car> myCar = customerDao.getRentedCar(customer.getID());
                    if (myCar.isEmpty()) {
                        System.out.println("You didn't rent a car!");
                      //  printCustomerMenu(customer);
                    } else {
                        System.out.println("Your rented car:");
                        System.out.println(myCar.get().getName());
                        System.out.println("Company");
                        System.out.println(companyDao.get(myCar.get().getCompanyID()).get().getName());
                    }
                    break;
                case 0:
                    printMainMenu();
                    break;
            }
            printCustomerMenu(customer);
        }
}


