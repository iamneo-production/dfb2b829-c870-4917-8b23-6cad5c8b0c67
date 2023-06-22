package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
   @Autowired
    private  PaymentRepo paymentRepo;
   @Autowired
   private LoanRepo loanRepo;
    public List<Payment> getPaymentsByLoanId(Long loan_id){
        return paymentRepo.findByLoanId(loan_id);
    }
    public Payment createPayment(Payment payment){
        return paymentRepo.save(payment);
    }

    public Payment getPaymentById(Long id) throws ResourceNotFoundException{
        Optional<Payment> payment = paymentRepo.getPaymentById(id);
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not fount with payment id: "+id);
        }
        else {
            return payment.get();
        }
    }

//    public Payment updatePayment(Payment payment) {
//        return paymentRepo.save(payment);
//    }
}