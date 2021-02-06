package banksystem.app;

import java.util.Scanner;

public class CardAccount {
    private final Scanner scanner = new Scanner(System.in);
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
        String input;
        while (true) {
            try {
                System.out.println("\n1. Balance");
                System.out.println("2. Log out");
                System.out.println("0. Exit");
                input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("\nBalance: " + getBalance());
                        break;
                    case "2":
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


    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getPIN() {
        return PIN;
    }

    public int getBalance() {
        return balance;
    }

    public void changeBalance(int balance) {
        this.balance += balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}