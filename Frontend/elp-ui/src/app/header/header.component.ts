import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isNavbarOpen: boolean = false;

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    public userService: UserService
  ) {}

  ngOnInit(): void {}

  public isLoggedIn(): boolean {
    return this.userAuthService.isLoggedIn();
  }

  public logout(): void {
    this.userAuthService.clear();
    this.router.navigate(['']);
  }

  public toggleNavbar(): void {
    this.isNavbarOpen = !this.isNavbarOpen;
  }
}
