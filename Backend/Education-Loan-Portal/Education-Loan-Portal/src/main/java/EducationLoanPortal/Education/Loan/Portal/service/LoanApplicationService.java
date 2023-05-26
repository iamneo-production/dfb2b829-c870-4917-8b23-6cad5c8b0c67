package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanApplicationRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanApplicationService {

    private final LoanApplicationRepo loanApplicationRepo;

    public LoanApplicationService(LoanApplicationRepo loanApplicationRepo) {
        this.loanApplicationRepo = loanApplicationRepo;
    }

    public LoanApplication addLoanApplication(LoanApplication loanApplication) {

        // get user id from user object and set it to user_id
        //
        // loanApplication.setUser_id(loanApplication.getUser().getId());

        return loanApplicationRepo.save(loanApplication);
    }

    // get loan application by id with exception handling
    public LoanApplication getLoanApplicationById(Long id) {
        return loanApplicationRepo.findLoanApplicationById(id)
                .orElseThrow(() -> new RuntimeException("Loan application by id " + id + " was not found"));
    }

    public Optional<LoanApplication> findAllLoanApplications() {
        try {
            return loanApplicationRepo.findAllByStatus("pending");
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all loan applications");
        }
    }

    // public LoanApplication updateLoanApplication(LoanApplication loanApplication)
    // {
    // return loanApplicationRepo.save(loanApplication);
    // }

    public Optional<LoanApplication> findLoanApplicationById(Long id) {
        try {
            return loanApplicationRepo.findLoanApplicationById(id);
        }
        // throw new UserNotFoundException("User by id " + id + " was not found");
        catch (Exception e) {
            throw new RuntimeException("Error while getting loan application by id");
        }
    }

    public List<Loan> findAllLoansByUserId(Long userId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepo.findAllByUserId(userId);
        if (loanApplication.isPresent()) {
            return loanApplication.get().getLoans();
        } else {
            throw new RuntimeException("Error while getting all loans by user id");
        }
    }

    public Optional<LoanApplication> findAllByStatus(String status) {
        try {
            return loanApplicationRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all loan applications");
        }

        // public void deleteLoanApplication(Long id) {
        // loanApplicationRepo.deleteLoanApplicationById(id);
        // }

    }
}
