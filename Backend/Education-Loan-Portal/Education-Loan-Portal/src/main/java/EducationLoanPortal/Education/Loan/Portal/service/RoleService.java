package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.Role;
import EducationLoanPortal.Education.Loan.Portal.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}