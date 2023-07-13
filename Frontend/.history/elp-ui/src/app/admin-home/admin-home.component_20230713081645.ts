import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../_services/user-auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LogoutConfirmationDialogComponent } from '..//logout-confirmation-dialog/logout-confirmation-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  isDarkMode: boolean = false;

  constructor(
    public userAuthService: UserAuthService,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

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

  toggleDarkMode(): void {
    this.isDarkMode = !this.isDarkMode;

    if (this.isDarkMode) {
      this.snackBar.open('Dark mode enabled', '', { duration: 2000 });
    } else {
      this.snackBar.open('Light mode enabled', '', { duration: 2000 });
    }
  }
}
