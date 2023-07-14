package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.service.LoanService;

import EducationLoanPortal.Education.Loan.Portal.service.StringEncryptionEncoderDecoder;
import com.itextpdf.text.DocumentException;

import EducationLoanPortal.Education.Loan.Portal.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private MailService mailService;

    @PostMapping("")
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        Loan addedLoan = loanService.addLoan(loan);

        if (addedLoan != null) {
            // Compose email message
            String to = addedLoan.getUser().getEmail(); // Use the user's email address as the recipient
            String subject = "Loan Added Successfully";
            String body = "Loan has been added successfully.\n\n" +
                    "Loan Details:\n" +
                    "Loan Amount: " + addedLoan.getLoanAmount() + "\n" +
                    "Start Date: " + addedLoan.getStartDate() + "\n" +
                    "End Date: " + addedLoan.getEndDate() + "\n" +
                    "Interest Rate: " + addedLoan.getInterestRate() + "\n";

            // Send email
            mailService.sendMail(to, subject, body);

            return new ResponseEntity<>(addedLoan, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get Loan by id
    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable("id") Long id)
            throws ResourceNotFoundException, UserNotFoundException {
        Optional<Loan> loan = Optional.ofNullable(loanService.getLoanById(id));
        if (loan.isEmpty()) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        return loan;
    }

    // update loan by id
    @PutMapping("/{id}")
    public Optional<Loan> updateLoan(@PathVariable("id") Long id, @RequestBody Loan loan)
            throws ResourceNotFoundException, UserNotFoundException {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        return loanService.updateLoanById(id, loan);
    }

    // delete loan by id
    @DeleteMapping("/{id}")

    public ResponseEntity<Object> deleteLoan(@PathVariable("id") Long id) throws ResourceNotFoundException, UserNotFoundException {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        if (loanService.deleteLoan(id)) {
            // return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllLoans(
            @RequestParam(required = false) Long user,
            @RequestParam(required = false) String status) throws UserNotFoundException {
        {
            // If user id is present, return all loans by user id
            if (user != null) {
                List<Loan> loans = loanService.findAllLoansByUserId(user);
                return ResponseEntity.ok(loans);
            }
            // If status is present, return all loans by status
            else if (status != null) {
                List<Loan> loans = loanService.findAllLoansByStatus(status);
                return ResponseEntity.ok(loans);
            } else {
                // Return an error response
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No loans found");
            }
        }

    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf  (@RequestParam(required = false) String encodedId) throws UserNotFoundException {
        long id = StringEncryptionEncoderDecoder.decodeToLong(encodedId);
        Long loan = loanService.getLoanById(id).getUser_id();

        try {
            byte[] pdfBytes = loanService.generateLoanApplicationPdf(loan);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "loan_application.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (DocumentException | ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}