package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepo extends JpaRepository<LoanApplication, Long> {

    Optional<LoanApplication> findLoanApplicationById(Long id);

    LoanApplication save(LoanApplication loanApplication);

    // delete loan application by id
    void deleteLoanApplicationById(Long id);

    List<LoanApplication> findAllByUserId(Long user_id);

    List<LoanApplication> findAllByStatus(String status);
}
