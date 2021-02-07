package banksystem.app;

import java.util.*;

public class BankSystem {
    private final Scanner scanner = new Scanner(System.in);
    private String input;

    private CardAccount logInAcc;
    public BankSystem(String databaseName) {
        DBConnector.setUrl("jdbc:sqlite:D:/SQLite/databases/" + databaseName);
//        DBConnector.deleteDB("card");
        DBConnector.initDB();
    }

    public void menu() {
        while (true) {
            try {
                System.out.println("\n1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");
                input = scanner.nextLine();

                switch (input) {
                    case "1":
                        CardAccount tmp = new CardAccount(NumberCreator.createUniqueNumber(), NumberCreator.createPIN());
                        DBConnector.addCard(tmp);
                        break;
                    case "2":
                        logIntoAccount();
                        break;
                    case "0":
                        System.out.println("\nBye!");
                        return;
                    default:
                        throw new CustomException("\nWrong Input!");
                }
            } catch (CustomException e) {
                e.print();
            }
        }
    }

    public void logIntoAccount() {
        String number;
        String pin;
        try {
            System.out.println("\nEnter your card number:");
            number = scanner.nextLine();
            System.out.println("Enter your PIN:");
            pin = scanner.nextLine();

            logInAcc = DBConnector.logIN(number, pin);
            if (logInAcc == null) {
                throw new CustomException("Wrong card number or PIN!");
            } else {
                System.out.println("\nYou have successfully logged in!");
                logInAcc.logIn();
            }
        } catch (CustomException e) {
            e.print();
        }
    }
}