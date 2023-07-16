import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { LogoutConfirmationDialogComponent } from '../logout-confirmation-dialog/logout-confirmation-dialog.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isNavbarOpen: boolean = false;
  currentRoute: string = '';

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    public userService: UserService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = event.urlAfterRedirects;
      }
    });
  }

  isLoggedIn(): boolean {
    return this.userAuthService.isLoggedIn();
  }

  logout(): void {
    const dialogRef = this.dialog.open(LogoutConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result === true) {
        this.userAuthService.clear();
        this.router.navigate(['']);
        console.log('The dialog was closed');
      }
    });
  }

  toggleNavbar(): void {
    this.isNavbarOpen = !this.isNavbarOpen;
  }
}
