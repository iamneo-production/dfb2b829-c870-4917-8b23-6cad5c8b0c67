package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.service.LoanApplicationService;
import EducationLoanPortal.Education.Loan.Portal.service.StringEncryptionEncoderDecoder;
import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan-applications")
public class LoanApplicationController {
    private LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }
    //
    // 2. Loan application management:
    // - `POST /loan-applications`: Create a new loan application

    @PostMapping("")
    public ResponseEntity<LoanApplication> addLoanApplication(@RequestBody LoanApplication loanApplication) {
        LoanApplication newLoanApplication = loanApplicationService.addLoanApplication(loanApplication);
        return new ResponseEntity<>(newLoanApplication, HttpStatus.CREATED);
    }

    // `GET /loan-applications/{id}`: Retrieve a specific loan application
    // by ID

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable("id") Long id) {
        Optional<LoanApplication> loanApplication = loanApplicationService.findLoanApplicationById(id);
        if (loanApplication.isPresent()) {
            return new ResponseEntity<>(loanApplication.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /loan-applications/{id}`: Update an existing loan application
    // by ID

    @PutMapping("/{id}")
    public ResponseEntity<LoanApplication> updateLoanApplication(@PathVariable("id") Long id,
            @RequestBody LoanApplication updatedLoanApplication) {
        LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(id);
        // if (loanApplication != null) {
        if (loanApplication != null) {
            LoanApplication newLoanApplication = loanApplicationService.updateLoanApplicationById(id,
                    updatedLoanApplication);
            return new ResponseEntity<>(newLoanApplication, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete loan application by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoanApplication(@PathVariable("id") Long id) {
        // LoanApplication existingLoanApplication =
        // loanApplicationService.getLoanApplicationById(id);

        if (loanApplicationService.deleteLoanApplicationById(id)) {
            return new ResponseEntity<>("Loan Application deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Loan Application Not Found", HttpStatus.NOT_FOUND);
        }

    }



    @GetMapping
    public ResponseEntity<?> getAllLoanApplications(
            @RequestParam(required = false) Long user,
            @RequestParam(required = false) String status) {
        System.out.println(user);
        if (user != null) {
            System.out.println(user);
            List<LoanApplication> loanApplications = loanApplicationService.findAllByUserId(user);
            return ResponseEntity.ok(loanApplications);
        } else if (status != null) {
            List<LoanApplication> loanApplications = loanApplicationService.findAllByStatus(status);
            return ResponseEntity.ok(loanApplications);
        } else {
            // Return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No loans Found");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf  (@RequestParam(required = false) String encodedId){
        long id = StringEncryptionEncoderDecoder.decodeToLong(encodedId);
        Optional<LoanApplication> loanApplication = loanApplicationService.findLoanApplicationById(id);
        if (loanApplication.isPresent()) {
            try {
                byte[] pdfBytes = loanApplicationService.generateLoanApplicationPdf(loanApplication.get());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "loan_application.pdf");

                return ResponseEntity.ok().headers(headers).body(pdfBytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
