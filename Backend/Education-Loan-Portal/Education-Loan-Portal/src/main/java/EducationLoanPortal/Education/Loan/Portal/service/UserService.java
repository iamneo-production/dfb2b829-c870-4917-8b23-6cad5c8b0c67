package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.User;
import EducationLoanPortal.Education.Loan.Portal.repository.UserRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) throws UserNotFoundException {

        var existingUser = userRepo.findUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User by id " + user.getId() + " was not found"));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        return userRepo.save(existingUser);
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow();
    }

    public void deleteUser(Long id) {
        userRepo.deleteUserById(id);
    }

}
