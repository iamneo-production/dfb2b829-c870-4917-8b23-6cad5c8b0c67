package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    List<Payment> findByLoanId(Long loan_id);

    Optional<Payment> getPaymentById(Long id);


//    // Get loan by id
//    Optional<Payment> getPaymentById(Long id);
//
//    // Delete loan by id
////    void deleteById(Long id);
////    // delete loan by id
////    Optional<Loan> deletePaymentById(Long id);
//    // Get all loans by user id
//    List<Payment> findAllByUserId(Long userId);
//
//    // Get all loans by status
//    List<Payment> findAllByStatus(String status);
//

}