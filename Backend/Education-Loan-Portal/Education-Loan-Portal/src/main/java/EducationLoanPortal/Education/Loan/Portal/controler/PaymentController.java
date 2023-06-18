package EducationLoanPortal.Education.Loan.Portal.controler;



import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import EducationLoanPortal.Education.Loan.Portal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @GetMapping("/findByLoanId/{loan_id}")
    public ResponseEntity<?> getPaymentsByLoanId(@PathVariable("id") Long loan_id){
        List<Payment> payments = paymentService.getPaymentsByLoanId(loan_id);
        if(!payments.isEmpty()){
            return ResponseEntity.ok(payments);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }



}
