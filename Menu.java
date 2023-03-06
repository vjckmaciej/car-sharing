package carsharing;

import carsharing.DAO.Car;
import carsharing.DAO.CarDaoImpl;
import carsharing.DAO.Company;
import carsharing.DAO.CompanyDaoImpl;
import java.util.List;
import java.util.Scanner;

public class Menu {
    CompanyDaoImpl companyDao;
    CarDaoImpl carDao;

    public Menu(CompanyDaoImpl companyDao, CarDaoImpl carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    static Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        printMainMenu();

    }
    public void printMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
//        while (choice == 1 || choice == 0) {
        while (true) {
            System.out.println();
            switch (choice) {
                case 1:
                    printCompanyMenu();
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
//        while (choice == 1 || choice == 2 || choice == 0) {
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
                        int counter = 1;
                        for (Company c : companyList) {
                            System.out.println(counter + ". " + c.getName());
                            counter++;
                        }
                        System.out.println("0. Back");
                        scanner.nextLine();
                        int choiceOfCarCompany = scanner.nextInt();
                        if (choiceOfCarCompany == 0) {
                            printCompanyMenu();
                        }
                        for (Company c : companyList) {
                            if (c.getID() == choiceOfCarCompany) {
                                printCarMenu(c);
                            }
                        }
                        System.out.println();
                    }
                    int choiceOfCarCompany = scanner.nextInt();

                    //printCompanyMenu();
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
//        while (choice == 1 || choice == 2 || choice == 0) {
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
}
