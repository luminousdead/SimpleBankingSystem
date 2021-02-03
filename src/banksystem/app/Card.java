package banksystem.app;

public class Card {
    private final String NUMBER;
    private String PIN;
    private double balance; // = 0

    public Card(String numberOfCard, String pin) {
        this.balance = 0;
        this.NUMBER = numberOfCard;
        this.PIN = pin;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public double getBalance() {
        return balance;
    }

    public void changeBalance(double balance) {
        this.balance += balance;
    }
}