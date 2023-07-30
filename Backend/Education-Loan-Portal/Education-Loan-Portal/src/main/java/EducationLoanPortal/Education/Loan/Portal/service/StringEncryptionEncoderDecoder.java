package EducationLoanPortal.Education.Loan.Portal.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringEncryptionEncoderDecoder {
    static byte[] encryptPdf(byte[] pdfBytes, String password) throws IOException, DocumentException {
        // Create a temporary file to work with the PDF
        File tempFile = File.createTempFile("temp", ".pdf");

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfBytes);
        }

        // Create a PdfReader to read the temporary file
        PdfReader reader = new PdfReader(tempFile.getAbsolutePath());

        // Create a ByteArrayOutputStream to write the encrypted PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create a PdfStamper to modify the PDF and set encryption
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        stamper.setEncryption(password.getBytes(), password.getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);

        // Close the PdfStamper
        stamper.close();

        // Close the PdfReader
        reader.close();

        // Delete the temporary file
        tempFile.delete();

        // Return the encrypted PDF as byte array
        return outputStream.toByteArray();
    }
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
