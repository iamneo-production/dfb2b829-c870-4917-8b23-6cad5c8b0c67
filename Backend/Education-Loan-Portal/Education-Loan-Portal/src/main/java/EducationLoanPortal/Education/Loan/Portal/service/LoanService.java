package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public Loan getLoanById(Long id) {
        Loan loan = loanRepo.getLoanById(id)
                .orElseThrow(() -> new UserNotFoundException("Loan by id " + id + " was not found"));
        return loan;
    }

    // update an existing loan by id

    public Optional<Loan> updateLoanById(Long id, Loan loan) throws ResourceNotFoundException {
        Optional<Loan> existingLoan = loanRepo.findById(id);
        if (!existingLoan.isPresent()) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }

        return Optional.ofNullable(loanRepo.save(loan));
    }

    // delete an existing loan by id
    public void deleteLoan(Long id) {
        try {
            loanRepo.deleteLoanById(id);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
    }

    // get all loans by user id
//    public List<Loan> findAllLoansByUserId(Long userId) {
//        // get all loans ids by user id
//        List<Long> loanIds = loanRepo.findAllLoanIdsByUserId(userId);
//        // iterate over the list of loan ids and get the loan by id
//        List<Loan> loans = loanIds.stream().map(loanId -> loanRepo.getLoanById(loanId).get())
//                .collect(Collectors.toList());
//
//        if (loans.isEmpty()) {
//            // return an empty list if no loans were found
//            return Collections.emptyList();
//        } else {
//            // return the list of loans if at least one loan was found
//            return loans;
//        }
//    }



    // get all loans by status
    public Optional<Loan> findAllLoansByStatus(String status) {
        try {
            return loanRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
    }

}