package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanApplicationRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Optional<LoanApplication> findAllByUserId(Long id) {
        try {
            return loanApplicationRepo.findAllByUserId(id);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error while getting all loan applications by user id");
        }}

         public Optional<LoanApplication> findAllByStatus(String status){
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
