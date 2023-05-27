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

    public LoanApplication updateLoanApplicationById(Long id, LoanApplication loanApplication) {
        Optional<LoanApplication> existingLoanApplication = loanApplicationRepo.findById(id);
        if (!existingLoanApplication.isPresent()) {
            throw new RuntimeException("Loan application not found with id: " + id);
        }
        // get the loan application by id
        var loanApplication_var = existingLoanApplication.get();
        // set the loan application purpose, loan amount, status and application date
        loanApplication_var.setPurpose(loanApplication.getPurpose());
        loanApplication_var.setLoanAmount(loanApplication.getLoanAmount());
        loanApplication_var.setStatus(loanApplication.getStatus());
        loanApplication_var.setApplicationDate(loanApplication.getApplicationDate());
        // save the loan application
        return loanApplicationRepo.save(loanApplication_var);

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

    public Optional<LoanApplication> findAllByStatus(String status) {
        try {
            return loanApplicationRepo.findAllByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all loan applications");
        }

    }

//    public Optional<LoanApplication> findAllByUserId(Long user) {
//
//    }
}
