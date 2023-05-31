//package EducationLoanPortal.Education.Loan.Portal.controler;
//
//import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
//import EducationLoanPortal.Education.Loan.Portal.model.Loan;
//import EducationLoanPortal.Education.Loan.Portal.service.LoanService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/loans")
//public class LoanController {
//
//    @Autowired
//    private LoanService loanService;
//
//    // Create a new loan
//    @PostMapping("")
//    public Loan addLoan(@RequestBody Loan loan) {
//        return loanService.addLoan(loan);
//    }
//
////    // Get Loan by id
////    @GetMapping("/{id}")
////    public Optional<Loan> getLoanById(@PathVariable("id") Long id) throws ResourceNotFoundException {
////        Optional<Loan> loan = Optional.ofNullable(loanService.getLoanById(id));
////        if (loan.isEmpty()) {
////            throw new ResourceNotFoundException("Loan not found with id: " + id);
////        }
////        return loan;
////    }
//
//    // update loan by id
//    @PutMapping("/{id}")
//    public Optional<Loan> updateLoan(@PathVariable("id") Long id, @RequestBody Loan loan)
//            throws ResourceNotFoundException {
//        Loan existingLoan = loanService.getLoanById(id);
//        if (existingLoan == null) {
//            throw new ResourceNotFoundException("Loan not found with id: " + id);
//        }
//        return loanService.updateLoanById(id, loan);
//    }
//
//    // delete loan by id
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteLoan(@PathVariable("id") Long id) throws ResourceNotFoundException {
//        Loan existingLoan = loanService.getLoanById(id);
//        if (existingLoan == null) {
//            throw new ResourceNotFoundException("Loan not found with id: " + id);
//        }
//        loanService.deleteLoan(id);
//    }
////    @GetMapping
////    public Optional<Loan> findAllLoans(
////            @RequestParam(value = "user_id", required = false) Long userId,
////            @RequestParam(value = "status", required = false) String status
////    ) throws ResourceNotFoundException {
////        if (userId != null) {
////            Optional<Loan> loans = loanService.findAllLoansByUserId(userId);
////            if (loans.isEmpty()) {
////                throw new ResourceNotFoundException("Loans not found for user id: " + userId);
////            }
////            return loans;
////        } else if (status != null) {
////            Optional<Loan> loans = loanService.findAllLoansByStatus(status);
////            if (loans.isEmpty()) {
////                throw new ResourceNotFoundException("Loans not found with status: " + status);
////            }
////            return loans;
////        } else {
////            // neither parameter is present
////            throw new IllegalArgumentException("Missing user_id or status parameter");
////        }
////    }
//
//}
