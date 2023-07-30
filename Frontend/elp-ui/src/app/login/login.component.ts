
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  
  errorMessage: string = '';
  userEmail: string = ''; // Added property
  userPassword: string = ''; // Added property

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userAuthService.clear(); // Clear storage when the component initializes
  }
  
  login(loginForm: NgForm) {
    this.errorMessage = 'form is invalid';

    if (loginForm.invalid) {
      return;
    }

    this.userService.login(loginForm.value).subscribe(
      (response: any) => {
        this.userAuthService.setRoles(response.user.role);
        this.userAuthService.setToken(response.jwtToken);
        const userDetails = {
          id: response.user.id,
          firstName: response.user.firstName,
          lastName: response.user.lastName,
          email: response.user.email,
        };
        this.userAuthService.setUserdetails(userDetails);

        const role = response.user.role[0].roleName;
        if (role === 'Admin') {
          this.router.navigate(['/adminHome']);
        } else {
          this.router.navigate(['/userHome']);
        }
      },
      (error) => {
        this.errorMessage = 'User email or password is wrong.';
        console.log(error);
      }
    );
  }
}
