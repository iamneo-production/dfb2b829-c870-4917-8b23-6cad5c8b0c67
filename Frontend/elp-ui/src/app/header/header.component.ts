import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    public userService: UserService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {}

<<<<<<< HEAD
  public isLoggedIn(): boolean {
    return this.userAuthService.isLoggedIn();
  }

  public logout(): void {
=======
  isLoggedIn(): boolean {
    return this.userAuthService.isLoggedIn();
  }

  logout(): void {
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
    const dialogRef = this.dialog.open(LogoutConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.userAuthService.clear();
        this.router.navigate(['']);
        console.log('The dialog was closed');
      }
    });
  }

<<<<<<< HEAD
  public toggleNavbar(): void {
=======
  toggleNavbar(): void {
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
    this.isNavbarOpen = !this.isNavbarOpen;
  }
}
