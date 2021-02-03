package banksystem.app;

import java.util.Scanner;

public class Account {
    private transient final Scanner scanner = new Scanner(System.in);
    private Card card;

    public Account(Card card) {
        this.card = card;
        System.out.println("\nYour card has been created:");
        System.out.println("Your card number:");
        System.out.println(card.getNUMBER());
        System.out.println("Your card PIN:");
        System.out.println(card.getPIN());
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
                        getBalance();
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

    public void logOut() {

    }

    public void getBalance() {
        System.out.println("\nBalance: " + card.getBalance());
    }

    public String getNumberOfCard() {
        return card.getNUMBER();
    }

    public boolean checkPIN(String pin) {
        return this.card.getPIN().equals(pin);
    }
}

