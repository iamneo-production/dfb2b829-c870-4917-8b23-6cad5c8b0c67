package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



public interface LoanRepo extends JpaRepository<Loan, Long> {

    // Get loan by id
    Optional<Loan> getLoanById(Long id);

    // Delete loan by id
    void deleteById(Long id);
    // delete loan by id
    Optional<Loan> deleteLoanById(Long id);
    // Get all loans by user id
    List<Loan> findAllByUserId(Long userId);

    // Get all loans by status
    List<Loan> findAllByStatus(String status);


}