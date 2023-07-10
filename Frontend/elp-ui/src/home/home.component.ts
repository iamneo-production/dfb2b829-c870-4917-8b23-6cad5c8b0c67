import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../_services/user-auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { LogoutConfirmationDialogComponent } from '..//logout-confirmation-dialog/logout-confirmation-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(
    public userAuthService: UserAuthService,
    private router: Router,
    private dialog: MatDialog
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
}
