package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import java.util.Optional;

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

    // Get Loan by id
    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable("id") Long id) throws ResourceNotFoundException, UserNotFoundException {
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable("id") Long id) throws ResourceNotFoundException, UserNotFoundException {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        loanService.deleteLoan(id);
    }
    @GetMapping
    public List<Loan> findAllLoans(
            @RequestParam(value = "user_id", required = false) Long userId,
            @RequestParam(value = "status", required = false) String status
    ) throws ResourceNotFoundException, UserNotFoundException {
        // If user id is present, return all loans by user id
        if (userId != null) {
            return loanService.findAllLoansByUserId(userId);
        }
        // If status is present, return all loans by status
        if (status != null) {
            return loanService.findAllLoansByStatus(status);
        }
        // If no parameters are present, return an empty list
        return Collections.emptyList();
    }
    

}
