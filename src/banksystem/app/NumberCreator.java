package banksystem.app;

import java.util.Random;

public class NumberCreator {
    private static final Random random = new Random();

    public static String createPIN() {
        String PIN = "";
        while (PIN.length() != 4) {
            PIN += random.nextInt(10);
        }
        return PIN;
    }

    public static String createUniqueNumber() {
        while (true) {
            String uniqueNum = createNum();
            if (checkLuhn(uniqueNum) == false) {
                continue;
            }
            if (DBConnector.findByNumber(uniqueNum)) {
                continue;
            }
            return uniqueNum;
        }
    }

    private static String createNum() {
        String uniqueNumber = "400000";
        while (uniqueNumber.length() != 15) {
            uniqueNumber += random.nextInt(10);
        }
        uniqueNumber += checkSum(uniqueNumber);
        return uniqueNumber;
    }

    public static boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNo.charAt(i) - '0';
            if (isSecond == true)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public static String checkSum(String cardNumber) {
        int[] number = new int[cardNumber.length()];
        int sum = 0;
        for(int i = 0; i < cardNumber.length() ; i++){
            number[i] = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            if (i % 2 == 0) {
                if (number[i] * 2 > 9) {
                    number[i] = number[i] * 2 - 9;
                } else {
                    number[i] = number[i] * 2;
                }
            }
            sum = sum + number[i];
        }
        return String.valueOf((10 - (sum % 10) == 10) ? 0 : (10 - (sum % 10)));
    }
}
