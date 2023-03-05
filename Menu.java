package carsharing;

import carsharing.DAO.Company;
import carsharing.DAO.CompanyDaoImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    CompanyDaoImpl companyDao;

    public Menu(CompanyDaoImpl companyDao) {
        this.companyDao = companyDao;
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
                    System.out.println("default");
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
                    if (companyDao.getAllCompanies().get().isEmpty()) {
                        System.out.println("The company list is empty");
                    } else {
                        System.out.println("Company list:");
                        //System.out.println(companyDao.getAllCompanies());
                        Optional<List<Company>> companyList = companyDao.getAllCompanies();
                        for (Company c : companyList.get()) {
                            System.out.println(c.getID() + ". " + c.getName());
                        }
                        System.out.println();
                    }
                    printCompanyMenu();
                    break;
                case 2:
                    System.out.println("Enter the company name: ");
                    scanner.nextLine();
                    String nameOfCompany = scanner.nextLine();
                    companyDao.addCompany(nameOfCompany);
                    System.out.println("The company was created!");
                    System.out.println();
                    printCompanyMenu();
                    break;
                case 0:
                    printMainMenu();
                    break;
                default:
                    System.out.println("Dont know");
                    break;
            }
            choice = Integer.parseInt(scanner.next());
        }
    }
}
