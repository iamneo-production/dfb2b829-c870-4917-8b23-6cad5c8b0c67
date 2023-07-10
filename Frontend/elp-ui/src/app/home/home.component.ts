import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../_services/user-auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LogoutConfirmationDialogComponent } from '..//logout-confirmation-dialog/logout-confirmation-dialog.component';
<<<<<<< HEAD
=======
import { MatSnackBar } from '@angular/material/snack-bar';
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
<<<<<<< HEAD
  constructor(
    public userAuthService: UserAuthService,
    private router: Router,
    private dialog: MatDialog
  ) { }

=======
  isDarkMode: boolean = false;

  constructor(
    public userAuthService: UserAuthService,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
  ngOnInit(): void {
    this.disableBackButton();
  }

  disableBackButton(): void {
    history.pushState(null, document.title, window.location.href);

    window.onpopstate = () => {
      this.openLogoutConfirmationDialog();
      history.pushState(null, document.title, window.location.href);
    };
  }

  openLogoutConfirmationDialog(): void {
    const dialogRef = this.dialog.open(LogoutConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.userAuthService.clear();
        this.router.navigate(['']);
      }
    });
  }
<<<<<<< HEAD
=======

  toggleDarkMode(): void {
    this.isDarkMode = !this.isDarkMode;

    if (this.isDarkMode) {
      this.snackBar.open('Dark mode enabled', '', { duration: 2000 });
    } else {
      this.snackBar.open('Light mode enabled', '', { duration: 2000 });
    }
  }
>>>>>>> c11db1ccd5398ac98788da5864af3d203c531967
}
