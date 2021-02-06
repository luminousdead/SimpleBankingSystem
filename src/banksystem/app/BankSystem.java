package banksystem.app;

import java.util.*;

public class BankSystem {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private String input;

    private DBConnector dbConnector;
    private CardAccount logInAcc;
    public BankSystem(String databaseName) {
        dbConnector = new DBConnector("jdbc:sqlite:D:/SQLite/databases/" + databaseName);
        dbConnector.initDB();
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
                        CardAccount tmp = new CardAccount(createUniqueNumber(), createPIN());
                        dbConnector.addCard(tmp);
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

            logInAcc = dbConnector.logIN(number, pin);
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

    private String createPIN() {
        String PIN = "";
        while (PIN.length() != 4) {
            PIN += random.nextInt(10);
        }
        return PIN;
    }

    // добавить расчет чексума чтобы ускорить процесс создания карты
    // отрефакторить эту часть кода
    private String createUniqueNumber() {
//        int count = 0; // для проверки
        while (true) {
            String uniqueNum = createNum();
//            System.out.println(s);
//            System.out.println(checkLuhn(s));

            if (checkLuhn(uniqueNum) == false) {
//                count++;
                continue;
            }

            if (dbConnector.findByNumber(uniqueNum)) {
                continue;
            }

            return uniqueNum;
        }
    }

    private String createNum() {
        String uniqueNumber = new String("400000"); // Импрувнуть создание
        while (uniqueNumber.length() != 16) {
            uniqueNumber += random.nextInt(10);
        }

        return uniqueNumber;
    }

    private boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = cardNo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}