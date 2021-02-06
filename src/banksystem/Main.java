package banksystem;

import banksystem.app.BankSystem;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("-fileName")) {
            BankSystem bankSystem = new BankSystem(args[1]);
            bankSystem.menu();
        } else {
            System.out.println("Something went wrong!");
        }
    }
}
