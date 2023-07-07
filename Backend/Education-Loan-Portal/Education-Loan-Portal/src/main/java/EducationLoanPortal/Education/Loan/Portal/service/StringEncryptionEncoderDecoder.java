package EducationLoanPortal.Education.Loan.Portal.service;

import java.nio.charset.StandardCharsets;

public class StringEncryptionEncoderDecoder {
    private static final int SHIFT_AMOUNT = 3; // You can change this shift amount if needed

    public static String encodeString(String input) {
        StringBuilder encryptedString = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                int shiftedChar = c + SHIFT_AMOUNT;
                if ((Character.isUpperCase(c) && shiftedChar > 'Z') ||
                        (Character.isLowerCase(c) && shiftedChar > 'z') ||
                        (Character.isDigit(c) && shiftedChar > '9')) {
                    shiftedChar -= 26; // Wrap around for letters or digits
                }
                encryptedString.append((char) shiftedChar);
            } else {
                encryptedString.append(c);
            }
        }
        return encryptedString.toString();
    }

    public static String decodeString(String input) {
        StringBuilder decryptedString = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                int shiftedChar = c - SHIFT_AMOUNT;
                if ((Character.isUpperCase(c) && shiftedChar < 'A') ||
                        (Character.isLowerCase(c) && shiftedChar < 'a') ||
                        (Character.isDigit(c) && shiftedChar < '0')) {
                    shiftedChar += 26; // Wrap around for letters or digits
                }
                decryptedString.append((char) shiftedChar);
            } else {
                decryptedString.append(c);
            }
        }
        return decryptedString.toString();
    }

    public static long decodeToLong(String input) {
        String decodedString = decodeString(input);
        return Long.parseLong(decodedString);
    }
}
