package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {

    // Create a new loan
    Loan save(Loan loan);

    // Get Loan by id

    Optional<Loan> getLoanById(Long id);

    // delete loan by id
    Optional<Loan> deleteLoanById(Long id);

    // get all loans by user id
    Optional<Loan> findAllByUserId(Long id);

    // get all loans by status
    Optional<Loan> findAllByStatus(String status);

    List<Long> findAllLoanIdsByUserId(Long userId);
}
