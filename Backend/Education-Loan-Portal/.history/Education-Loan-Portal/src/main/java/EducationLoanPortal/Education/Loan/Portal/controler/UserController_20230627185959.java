package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.User;
import EducationLoanPortal.Education.Loan.Portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //1. User management:

   //- `POST /users`: Create a new user
   @PostMapping("")
   public ResponseEntity<User> addUser(@RequestBody User user) {
       User newUser = userService.addUser(user);
       return new ResponseEntity<>(newUser, HttpStatus.CREATED);
   }

   //- `GET /users/{id}`: Retrieve a specific user by ID
   @GetMapping("/{id}")
   public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
       User user = userService.findUserById(id);
       return new ResponseEntity<>(user, HttpStatus.OK);
   }



   //`PUT /users/{id}`: Update an existing user by ID
   @PutMapping("/{id}")
   public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotFoundException {
       User updateUser = userService.updateUser(user);
       return new ResponseEntity<>(updateUser, HttpStatus.OK);
   }

   //`DELETE /users/{id}`: Delete an existing user by ID
   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
       userService.deleteUser(id);
       return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/all")
   public ResponseEntity<List<User>> getAllUsers() {
       List<User> users = userService.findAllUsers();
       return new ResponseEntity<>(users, HttpStatus.OK);
   }



    //for the spring jwt
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }







}
