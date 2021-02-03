package banksystem.app;

import java.util.*;

public class BankSystem {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private String input = "";
    private Map<String, Account> accountMap = new HashMap<>();

    public void menu() {
        while (true) {
            try {
                System.out.println("\n1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");
                input = scanner.nextLine();

                switch (input) {
                    case "1":
                        Account tmp = new Account(createNewCard());
                        accountMap.put(tmp.getNumberOfCard(), tmp);
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

            if (accountMap.containsKey(number) && accountMap.get(number).checkPIN(pin)) {
                System.out.println("\nYou have successfully logged in!");
                accountMap.get(number).logIn();
            } else
                throw new CustomException("Wrong card number or PIN!");
        } catch (CustomException e) {
            e.print();
        }
    }

    private Card createNewCard() {
        String pin = "";
        while (pin.length() != 4) {
            pin += random.nextInt(10);
        }
        return new Card(createUniqueNumber(), pin);
    }

    private String createUniqueNumber() {
        String uniqueNumber = "400000";
        while (uniqueNumber.length() != 16) {
            uniqueNumber += random.nextInt(10);
        }

        if (accountMap.containsKey(uniqueNumber))
            createUniqueNumber();

        return uniqueNumber;
    }
}