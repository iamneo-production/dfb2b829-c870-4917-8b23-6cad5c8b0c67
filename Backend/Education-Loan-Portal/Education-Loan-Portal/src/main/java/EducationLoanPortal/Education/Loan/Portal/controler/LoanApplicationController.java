package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.service.LoanApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan-applications")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping("")
    public ResponseEntity<LoanApplication> addLoanApplication(@RequestBody LoanApplication loanApplication) {
        LoanApplication newLoanApplication = loanApplicationService.addLoanApplication(loanApplication);
        return new ResponseEntity<>(newLoanApplication, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(@PathVariable("id") Long id) {
        Optional<LoanApplication> loanApplication = loanApplicationService.findLoanApplicationById(id);
        if (loanApplication.isPresent()) {
            return new ResponseEntity<>(loanApplication.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<LoanApplication> updateLoanApplication(@RequestBody
    // LoanApplication loanApplication) {
    // LoanApplication updateLoanApplication =
    // loanApplicationService.updateLoanApplication(loanApplication);
    // return new ResponseEntity<>(updateLoanApplication, HttpStatus.OK);
    // }

    @GetMapping
    public ResponseEntity<?> getAllLoanApplications(
            @RequestParam(required = false) Long user,
            @RequestParam(required = false) String status) {

        if (user != null) {
            Optional<LoanApplication> loanApplications = loanApplicationService.findAllByUserId(user);
            return ResponseEntity.ok(loanApplications);
        } else if (status != null) {
            Optional<LoanApplication> loanApplications = loanApplicationService.findAllByStatus(status);
            return ResponseEntity.ok(loanApplications);
        } else {
            // Return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either 'user' or 'status' parameter is required");
        }
    }


}
