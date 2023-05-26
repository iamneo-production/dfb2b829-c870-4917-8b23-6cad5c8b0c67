package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return loanRepo.getLoanById(id)
                .orElseThrow(() -> new UserNotFoundException("Loan by id " + id + " was not found"));
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
    public Optional<Loan> findAllLoansByUserId(Long id) {
        try {
            return loanRepo.findAllByUserId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
    }

    // get all loans by status
    public Optional<Loan> findAllLoansByStatus(String status) {
        try {
            return loanRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new UserNotFoundException("Loan by id " + id + " was not found");
        }
    }


}