package EducationLoanPortal.Education.Loan.Portal.repository;

import EducationLoanPortal.Education.Loan.Portal.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Integer> {
    Optional<Otp>  findByEmail(String email);

    long countByEmailAndOtpGeneratedTimeBetween(String email, LocalDateTime with, LocalDateTime with1);
}
