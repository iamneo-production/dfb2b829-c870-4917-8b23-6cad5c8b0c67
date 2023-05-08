package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//
public interface LoanApplicationRepo extends JpaRepository<LoanApplication, Long> {

    // void deleteLoanApplicationById(Long id);

    Optional<LoanApplication> findLoanApplicationById(Long id);

    // add loan application
    LoanApplication save(LoanApplication loanApplication);

    Optional<LoanApplication> findAllByUserId(Long id);

    Optional<LoanApplication> findAllByStatus(String status);

}