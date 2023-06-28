import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { User } from '../user';
import { UserService } from '../user.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @ViewChild('editDialog') editDialog!: TemplateRef<any>;
  dialogRef!: MatDialogRef<any>;
  user: User = new User();
  userdetails: User[] = [];
  userDetails: any;
  userId: any;

  constructor(
    private userService: UserService,
    private dialog: MatDialog,
    public userAuthService: UserAuthService
  ) {
    this.userDetails = this.userAuthService.getUserdetails();
    this.userId = this.userDetails.id;
  }

  ngOnInit() {
    this.getUserdetails();
  }

  getUserdetails() {
    const id = 13; // Specify the ID of the user you want to fetch
    this.userService.getUserById(id).subscribe(
      (resp) => {
        console.log(resp);
        this.userdetails = [resp];
      },
      (err) => {
        console.log(err);
      }
    );
  }

  openEditDialog(user: User) {
    this.dialogRef = this.dialog.open(this.editDialog, {
      width: '400px',
      data: user
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateUser(result);
      }
    });
  }

  closeEditDialog() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }

  updateUser(updatedUser: User) {
    const id = updatedUser.id;
    this.userService.updateUser(id, updatedUser).subscribe(
      (resp) => {
        console.log(resp);
        this.openDialog('Success', 'User details updated successfully');
      },
      (err) => {
        console.log(err);
        this.openDialog('Error', 'Failed to update the details');
      }
    );
  }

  openDialog(title: string, message: string) {
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message }
    });
  }
}
