package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Create a new loan
    @PostMapping("")
    public Loan addLoan(@RequestBody Loan loan) {
        return loanService.addLoan(loan);
    }

    // update loan by id
    @PutMapping("/{id}")
    public Optional<Loan> updateLoan(@PathVariable("id") Long id, @RequestBody Loan loan) throws ResourceNotFoundException {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        return loanService.updateLoanById(id, loan);
    }

    // delete loan by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        loanService.deleteLoan(id);
    }

    // get all loans by user id
    @GetMapping(params = "userId")
    public Optional<Loan> findAllLoansByUserId(@RequestParam("userId") Long id) throws ResourceNotFoundException {
        Optional<Loan> loans = loanService.findAllLoansByUserId(id);
        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Loans not found for user id: " + id);
        }
        return loans;
    }

    // get all loans by status
    @GetMapping(params = "status")
    public Optional<Loan> findAllLoansByStatus(@RequestParam("status") String status) throws ResourceNotFoundException {
        Optional<Loan> loans = loanService.findAllLoansByStatus(status);
        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Loans not found with status: " + status);
        }
        return loans;
    }
}
