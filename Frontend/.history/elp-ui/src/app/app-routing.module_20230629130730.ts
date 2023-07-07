import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import {HomeComponent} from './home/home.component';
import { ApplyLoanFormComponent } from './apply-loan-form/apply-loan-form.component';
import {LoansComponent} from './loans/loans.component';
import {ProfileComponent} from './profile/profile.component';
import { AdminHomeComponent } from './admin-home/admin-home.component'
import {LoginComponent} from './login/login.component'
import { AuthGuard } from './_auth/auth.guard';
import { AdminLoansComponent } from './admin-loans/admin-loans.component';
import { AdminLoanApplicationComponent } from './admin-loan-application/admin-loan-application.component';
import { LoanDetailsComponent } from './loan-details/loan-details.component';
import { AddLoanComponent } from './add-loan/add-loan.component';


const routes: Routes = [

  {path:'',component:LoginComponent},
  { path: 'home', component: HomeComponent },
  { path: 'apply-loan-form', component: ApplyLoanFormComponent,canActivate:[AuthGuard], data:{roles:['User']} },
  { path: 'loans', component: LoansComponent },
  {path:'profile', component: ProfileComponent},
  {path :'admin-home',component:AdminHomeComponent},
  {path:'admin-loans',component:AdminLoansComponent},
  {path:'admin-loan-application',component:AdminLoanApplicationComponent},
  {path:'loan-details',component:LoanDetailsComponent},
  {path:'Add-loan',component:AddLoanComponent},
  {path:'adminHome',component:AdminHomeComponent,canActivate:[AuthGuard], data:{roles:['Admin']} },


]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}