package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // Get all loans by user id
        List<Loan> loans = loanRepo.findAllByUserId(userId);

        if (loans.isEmpty()) {
            // Return an empty list if no loans were found
            return Collections.emptyList();
        } else {
            // Return the list of loans if at least one loan was found
            return loans;
        }
    }

    public List<Loan> findAllLoansByStatus(String status) throws UserNotFoundException {
        try {
            return loanRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by status " + status + " was not found");
        }
    }

}