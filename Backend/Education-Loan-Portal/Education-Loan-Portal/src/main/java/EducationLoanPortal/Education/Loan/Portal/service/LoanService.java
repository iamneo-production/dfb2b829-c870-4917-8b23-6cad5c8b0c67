package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
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
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanService {

    private final LoanRepo loanRepo;

    public LoanService(LoanRepo loanRepo) {
        this.loanRepo = loanRepo;
    }

    public Loan addLoan(Loan loan) {
        return loanRepo.save(loan);
    }

    // get loan by id
    public Loan getLoanById(Long id) throws UserNotFoundException {
        Optional<Loan> loan = loanRepo.getLoanById(id);
        if (loan.isEmpty()) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
        return loan.get();
    }
    // update an existing loan by id

    public Optional<Loan> updateLoanById(Long id, Loan loan) throws ResourceNotFoundException {
        Optional<Loan> existingLoan = loanRepo.getLoanById(id);
        if (!existingLoan.isPresent()) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }

        return Optional.ofNullable(loanRepo.save(loan));
    }

    // delete an existing loan by id
    public boolean deleteLoan(Long id) throws UserNotFoundException {
        try {
            loanRepo.deleteLoanById(id);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
        return true;
    }

    public List<Loan> findAllLoansByUserId(Long userId) {
        {
            try {


                return loanRepo.findAllByUserId(userId);

            } catch (Exception e) {
                throw new RuntimeException("Error while getting all loan applications");
            }


        }
    }
    // Get all loans by user id


    public List<Loan> findAllLoansByStatus(String status) throws UserNotFoundException {
        try {
            return loanRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by status " + status + " was not found");
        }
    }

    public byte[] generateLoanApplicationPdf(Long loanId) throws DocumentException, IOException, ResourceNotFoundException, UserNotFoundException {
        Loan loan = getLoanById(loanId);

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

        Paragraph subheading = new Paragraph("Loan Receipt");
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
        userTable.addCell(String.valueOf(loan.getUser().getId()));

        userTable.addCell("User Email");
        userTable.addCell(loan.getUser().getEmail());

        userTable.addCell("First Name");
        userTable.addCell(loan.getUser().getFirstName());

        userTable.addCell("Last Name");
        userTable.addCell(loan.getUser().getLastName());

        userTable.addCell("Address");
        userTable.addCell(loan.getUser().getAddress());

        userTable.addCell("Phone Number");
        userTable.addCell(loan.getUser().getPhoneNumber());

        document.add(userTable);

        // Add spacing between user table and loan table
        document.add(Chunk.NEWLINE);
        Paragraph subheading2 = new Paragraph("Loan Details");
        subheading2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subheading2);

        document.add(Chunk.NEWLINE);

        // Create a table for loan details
        PdfPTable loanTable = new PdfPTable(2);
        loanTable.setWidthPercentage(100);

        loanTable.addCell("Loan ID");
        loanTable.addCell(String.valueOf(loan.getId()));

        loanTable.addCell("Loan Amount");
        loanTable.addCell(String.valueOf(loan.getLoanAmount()));

        loanTable.addCell("Interest Rate");
        loanTable.addCell(String.valueOf(loan.getInterestRate()));

        loanTable.addCell("Status");
        loanTable.addCell(loan.getStatus());

        loanTable.addCell("Start Date");
        loanTable.addCell(loan.getStartDate().toString());

        loanTable.addCell("End Date");
        loanTable.addCell(loan.getEndDate().toString());

        document.add(loanTable);

        // Close the PDF document
        document.close();
        writer.close();

        return outputStream.toByteArray();
    }

}