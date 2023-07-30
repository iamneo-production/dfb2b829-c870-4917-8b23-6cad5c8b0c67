package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Role;
import EducationLoanPortal.Education.Loan.Portal.model.User;
import EducationLoanPortal.Education.Loan.Portal.repository.RoleRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) throws UserNotFoundException {
        User existingUser = findUserById(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
//        existingUser.setEmail(user.getEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        return userRepo.save(existingUser);
    }

    public  User findUserById(Long id) throws UserNotFoundException {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
    public User registerNewUser(User user) {
        // Check if email already exists
        if (UserRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone number already exists
        if (userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        Role defaultRole = (Role) roleRepo.findByRoleName("User")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(defaultRole);

        user.setRole(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepo.save(user);
    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void initRoleAndUser() {
        Role adminRole = (Role) roleRepo.findByRoleName("Admin").orElseGet(() -> {
            Role role = new Role();
            role.setRoleName("Admin");
            role.setRoleDescription("Admin role");
            return roleRepo.save(role);
        });

        Role userRole = (Role) roleRepo.findByRoleName("User").orElseGet(() -> {
            Role role = new Role();
            role.setRoleName("User");
            role.setRoleDescription("Default role for newly created record");
            return roleRepo.save(role);
        });

        User adminUser = new User();
        adminUser.setPassword(getEncodedPassword("admin@pass"));
        adminUser.setEmail("admin");
        adminUser.setLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepo.save(adminUser);

        User user = new User();
        user.setEmail("udayagiricharankumar@gmail.com");
        user.setPassword(getEncodedPassword("udayagiricharankumar@gmail.com"));
        user.setFirstName("Charan");
        user.setLastName("Udayagiri");
        user.setPhoneNumber("9123456789");
        user.setAddress(" Block 10, 7th &5th Floor, DLF IT Park, Rd, Manapakkam, Chennai, Tamil Nadu 600125");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepo.save(user);
    }
}
