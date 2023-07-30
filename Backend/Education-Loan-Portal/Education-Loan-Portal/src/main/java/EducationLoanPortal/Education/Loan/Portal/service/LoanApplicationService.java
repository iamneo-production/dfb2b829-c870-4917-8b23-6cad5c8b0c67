package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.model.User;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanApplicationRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.UserRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanApplicationService {

    private final LoanApplicationRepo loanApplicationRepo;
    private final MailService mailService;
    private final UserService userService;

    public LoanApplicationService(LoanApplicationRepo loanApplicationRepo, MailService mailService, UserService userService) {
        this.loanApplicationRepo = loanApplicationRepo;
        this.mailService = mailService;
        this.userService = userService;
    }

    public LoanApplication addLoanApplication(LoanApplication loanApplication) throws UserNotFoundException, DocumentException, IOException {
        LoanApplication newLoanApplication = loanApplicationRepo.save(loanApplication);

        // Extract loan details
        Double loanAmount = newLoanApplication.getLoanAmount();
        String purpose = newLoanApplication.getPurpose();
        LocalDate applicationDate = newLoanApplication.getApplicationDate();
        System.out.println(newLoanApplication);

        // Get user email based on user_id
        Long userId = newLoanApplication.getUser_id();
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        // Send email with loan details
        String to = user.getEmail();
        String subject = "Loan Application Created";
        String body = "You have successfully applied for the loan with the following details:\n\n" +
                "Loan Amount: " + loanAmount + "\n" +
                "Purpose: " + purpose + "\n" +
                "Application Date: " + applicationDate;

        // Generate the PDF with loan details
        byte[] pdfBytes = generateLoanApplicationPdf(newLoanApplication);

        // Encrypt the PDF with the user's phone number as the password
        String password = user.getPhoneNumber();
        pdfBytes = StringEncryptionEncoderDecoder.encryptPdf(pdfBytes, password);

        // Attach the PDF to the email
        String attachmentName = "Loan_Application.pdf";

        // Compose the email body with instructions for the password (phone number)
        body += "\n\nPlease keep your phone number (" + password + ") as the password for opening the attached PDF.";

        // Send email with the PDF attachment and password instructions in the body
        mailService.sendMailWithAttachment(to, subject, body, pdfBytes, attachmentName);

        return newLoanApplication;
    }

    public LoanApplication getLoanApplicationById(Long id) {
        return loanApplicationRepo.findLoanApplicationById(id)
                .orElseThrow(() -> new RuntimeException("Loan application with ID " + id + " was not found"));
    }

    public LoanApplication updateLoanApplicationById(Long id, LoanApplication updatedLoanApplication, boolean sendNotification) {
        LoanApplication existingLoanApplication = loanApplicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found with ID: " + id));

        String currentStatus = existingLoanApplication.getStatus();
        String updatedStatus = updatedLoanApplication.getStatus();

        existingLoanApplication.setPurpose(updatedLoanApplication.getPurpose());
        existingLoanApplication.setLoanAmount(updatedLoanApplication.getLoanAmount());
        existingLoanApplication.setStatus(updatedLoanApplication.getStatus());
        existingLoanApplication.setApplicationDate(updatedLoanApplication.getApplicationDate());

        LoanApplication savedLoanApplication = loanApplicationRepo.save(existingLoanApplication);

        // Check if the status has changed and send notification if required
        if (!currentStatus.equalsIgnoreCase(updatedStatus)) {
            User user = savedLoanApplication.getUser();
            if (user != null) {
                String email = user.getEmail();
                String subject;
                String body;

                if (updatedStatus.equalsIgnoreCase("approved")) {
                    subject = "Loan Application Approved";
                    body = "Your loan application has been approved.";
                } else if (updatedStatus.equalsIgnoreCase("rejected")) {
                    subject = "Loan Application Rejected";
                    body = "Your loan application has been rejected.";
                } else {
                    // For any other status change, no notification will be sent
                    return savedLoanApplication;
                }

                mailService.sendMail(email, subject, body);
            }
        }

        return savedLoanApplication;
    }

    public List<LoanApplication> findAllLoanApplications() {
        try {
            return loanApplicationRepo.findAllByStatus("pending");
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all loan applications");
        }
    }

    public Optional<LoanApplication> findLoanApplicationById(Long id) {
        try {
            return loanApplicationRepo.findLoanApplicationById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting loan application by ID");
        }
    }

    public boolean deleteLoanApplicationById(Long id) {
        try {
            loanApplicationRepo.deleteLoanApplicationById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting loan application by ID");
        }
        return true;
    }

    public List<LoanApplication> findAllByStatus(String status) {
        try {
            return loanApplicationRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all loan applications");
        }
    }

    public List<LoanApplication> findAllByUserId(Long userId) {
        List<LoanApplication> loanApplications = loanApplicationRepo.findAll();
        List<LoanApplication> result = new ArrayList<>();

        for (LoanApplication loanApplication : loanApplications) {
            if (loanApplication.getUser() != null && loanApplication.getUser().getId().equals(userId)) {
                result.add(loanApplication);
            }
        }

        return result;
    }
    public byte[] generateLoanApplicationPdf(LoanApplication loanApplication) throws DocumentException, IOException, UserNotFoundException {
        User user = userService.findUserById(loanApplication.getUser_id());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create a PDF document
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        // Open the PDF document
        document.open();

        // Add heading and subheading
        Paragraph heading = new Paragraph("Education Loan Portal");
        heading.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(heading);
        document.add(Chunk.NEWLINE);

        Paragraph subheading = new Paragraph("Loan Application Form Receipt");
        subheading.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading);

        document.add(Chunk.NEWLINE);

        Paragraph subheading1 = new Paragraph("User Details");
        subheading1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading1);
        document.add(Chunk.NEWLINE);

        // Create a table for user details
        PdfPTable userTable = new PdfPTable(2);
        userTable.setWidthPercentage(100);

        userTable.addCell("User ID");
        userTable.addCell(String.valueOf(user.getId()));

        userTable.addCell("User Email");
        userTable.addCell(user.getEmail());

        userTable.addCell("First Name");
        userTable.addCell(user.getFirstName());

        userTable.addCell("Last Name");
        userTable.addCell(user.getLastName());

        userTable.addCell("Address");
        userTable.addCell(user.getAddress());

        userTable.addCell("Phone Number");
        userTable.addCell(user.getPhoneNumber());

        document.add(userTable);

        // Add spacing between user table and loan table
        document.add(Chunk.NEWLINE);

        Paragraph subheading2 = new Paragraph("Loan Application Details");
        subheading2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading2);

        document.add(Chunk.NEWLINE);

        // Create a table for loan application details
        PdfPTable loanTable = new PdfPTable(2);
        loanTable.setWidthPercentage(100);

        loanTable.addCell("Loan ID");
        loanTable.addCell(String.valueOf(loanApplication.getId()));

        loanTable.addCell("Loan Amount");
        loanTable.addCell(String.valueOf(loanApplication.getLoanAmount()));

        loanTable.addCell("Purpose");
        loanTable.addCell(loanApplication.getPurpose());

        loanTable.addCell("Status");
        loanTable.addCell(loanApplication.getStatus());

        loanTable.addCell("Application Date");
        loanTable.addCell(loanApplication.getApplicationDate().toString());

        document.add(loanTable);

        // Close the PDF document
        document.close();
        writer.close();

        return outputStream.toByteArray();
    }
}


