package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import EducationLoanPortal.Education.Loan.Portal.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}