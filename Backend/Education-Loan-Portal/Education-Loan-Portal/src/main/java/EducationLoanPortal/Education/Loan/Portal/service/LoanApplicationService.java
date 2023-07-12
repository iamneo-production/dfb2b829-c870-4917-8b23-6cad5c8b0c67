package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanApplicationRepo;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanApplicationService {

    private final LoanApplicationRepo loanApplicationRepo;

    public LoanApplicationService(LoanApplicationRepo loanApplicationRepo) {
        this.loanApplicationRepo = loanApplicationRepo;
    }

    public LoanApplication addLoanApplication(LoanApplication loanApplication) {
        return loanApplicationRepo.save(loanApplication);
    }

    public LoanApplication getLoanApplicationById(Long id) {
        return loanApplicationRepo.findLoanApplicationById(id)
                .orElseThrow(() -> new RuntimeException("Loan application by id " + id + " was not found"));
    }

    public LoanApplication updateLoanApplicationById(Long id, LoanApplication loanApplication) {
        Optional<LoanApplication> existingLoanApplication = loanApplicationRepo.findById(id);
        if (!existingLoanApplication.isPresent()) {
            throw new RuntimeException("Loan application not found with id: " + id);
        }

        LoanApplication loanApplication_var = existingLoanApplication.get();
        loanApplication_var.setPurpose(loanApplication.getPurpose());
        loanApplication_var.setLoanAmount(loanApplication.getLoanAmount());
        loanApplication_var.setStatus(loanApplication.getStatus());
        loanApplication_var.setApplicationDate(loanApplication.getApplicationDate());
        return loanApplicationRepo.save(loanApplication_var);
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
            throw new RuntimeException("Error while getting loan application by id");
        }
    }

    public boolean deleteLoanApplicationById(Long id) {
        try {
            loanApplicationRepo.deleteLoanApplicationById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting loan application by id");
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
            if (loanApplication.getUser().getId().equals(userId)) {
                result.add(loanApplication);
            }
        }
        System.out.println(result);

        return result;
    }
    public byte[] generateLoanApplicationPdf(LoanApplication loanApplication) throws DocumentException, IOException {
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

        Paragraph subheading = new Paragraph("Loan Application Form Details");
        subheading.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading);

        document.add(Chunk.NEWLINE);

        Paragraph subheading1= new Paragraph("User Detils");
        subheading.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading1);
        document.add(Chunk.NEWLINE);


        // Create a table for user details
        PdfPTable userTable = new PdfPTable(2);
        userTable.setWidthPercentage(100);

        userTable.addCell("User ID");
        userTable.addCell(String.valueOf(loanApplication.getUser().getId()));

        userTable.addCell("User Email");
        userTable.addCell(loanApplication.getUser().getEmail());

        userTable.addCell("First Name");
        userTable.addCell(loanApplication.getUser().getFirstName());

        userTable.addCell("Last Name");
        userTable.addCell(loanApplication.getUser().getLastName());

        userTable.addCell("Address");
        userTable.addCell(loanApplication.getUser().getAddress());

        userTable.addCell("Phone Number");
        userTable.addCell(loanApplication.getUser().getPhoneNumber());

        document.add(userTable);

        // Add spacing between user table and loan table
        document.add(Chunk.NEWLINE);
        Paragraph subheading2= new Paragraph("Loan application Deatils");
        subheading.setAlignment(Paragraph.ALIGN_CENTER);
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
