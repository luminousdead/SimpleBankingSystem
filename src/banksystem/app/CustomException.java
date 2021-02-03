package banksystem.app;

public class CustomException extends Exception {
    CustomException(String msg) {
        super(msg);
    }

    public void print() {
        System.out.println(super.getMessage());
    }
}

