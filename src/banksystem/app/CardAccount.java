package banksystem.app;

import java.util.Scanner;

public class CardAccount {
    private final Scanner scanner = new Scanner(System.in);
    private String input;

    private int id;
    private String NUMBER;
    private String PIN;
    private int balance; // = 0

    public CardAccount(String numberOfCard, String pin) {
        this.balance = 0;
        this.NUMBER = numberOfCard;
        this.PIN = pin;
        System.out.println("\nYour card has been created:");
        System.out.println("Your card number:");
        System.out.println(getNUMBER());
        System.out.println("Your card PIN:");
        System.out.println(getPIN());
    }

    public CardAccount(String numberOfCard, String pin, int id, int balance) {
        this.balance = balance;
        this.id = id;
        this.NUMBER = numberOfCard;
        this.PIN = pin;
    }

    public void logIn() {
        while (true) {
            try {
                System.out.println("\n1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
                input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("\nBalance: " + getBalance());
                        break;
                    case "2":
                        addIncome();
                        break;
                    case "3":
                        transfer();
                        break;
                    case "4":
                        DBConnector.deleteByID(getId());
                        System.out.println("\nThe account has been closed!");
                        return;
                    case "5":
                        // logOut
                        System.out.println("\nYou have successfully logged out!");
                        return;
                    case "0":
                        System.out.println("\nBye!");
                        System.exit(0);
                    default:
                        throw new CustomException("\nWrong Input!");
                }

            } catch (CustomException e) {
                e.print();
            }
        }
    }


    public String getNUMBER() {
        return NUMBER;
    }

    public String getPIN() {
        return PIN;
    }

    public int getBalance() {
        return balance;
    }

    private void addIncome() {
        System.out.println("\nEnter income:");
        try {
            int income = Integer.parseInt(scanner.nextLine());
            if (income <= 0) {
                throw new CustomException("Wrong income!");
            }
            this.balance += income;
            DBConnector.updateBalanceByID(getId(), balance);
            System.out.println("Income was added!");
        } catch (CustomException e) {
            e.print();
        }
    }


    public int getId() {
        return id;
    }

    private void transfer() {
        if (getBalance() == 0) {
            System.out.println("You have no money!");
            return;
        }

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        input = scanner.nextLine();

        if (input.equals(getNUMBER())) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }
        if (!NumberCreator.checkLuhn(input)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }
        if (!DBConnector.findByNumber(input)) {
            System.out.println("Such a card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        try {
            int value = Integer.parseInt(scanner.nextLine());
            System.out.println(DBConnector.doTransfer(getNUMBER(), input, value));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}