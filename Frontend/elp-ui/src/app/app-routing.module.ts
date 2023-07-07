import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import {HomeComponent} from './home/home.component';
import { ApplyLoanFormComponent } from './apply-loan-form/apply-loan-form.component';
import {LoansComponent} from './loans/loans.component';
import {ProfileComponent} from './profile/profile.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { PaymentComponent } from './payment/payment.component';
import { AuthGuard } from './_auth/auth.guard';
<<<<<<< HEAD
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AddLoanComponent } from './add-loan/add-loan.component';
import { LoanDetailsComponent } from './loan-details/loan-details.component';
import { AdminLoanApplicationComponent } from './admin-loan-application/admin-loan-application.component';
import { AdminLoansComponent } from './admin-loans/admin-loans.component';
import { LoginComponent } from './login/login.component';
// import {ContactComponent} from './contact/contact.component';
=======
import { AdminLoansComponent } from './admin-loans/admin-loans.component';
import { AdminLoanApplicationComponent } from './admin-loan-application/admin-loan-application.component';
import { LoanDetailsComponent } from './loan-details/loan-details.component';
import { AddLoanComponent } from './add-loan/add-loan.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26


const routes: Routes = [
  //{ path: '', component: AppComponent },
  //{ path: 'home', component: HomeComponent },
  //{ path: 'apply-loan-form', component: ApplyLoanFormComponent },
  //{ path: 'loans', component: LoansComponent },
  //{path:'profile', component: ProfileComponent},
  // {path:'contact',component: ContactComponent}

  {path:'',component:LoginComponent},
  { path: 'userHome', component: HomeComponent ,canActivate:[AuthGuard], data:{roles:['User']} },
  { path: 'apply-loan-form', component: ApplyLoanFormComponent,canActivate:[AuthGuard], data:{roles:['User']} },
  { path: 'loans', component: LoansComponent ,canActivate:[AuthGuard], data:{roles:['User']} },
  {path:'profile', component: ProfileComponent,canActivate:[AuthGuard], data:{roles:['User','Admin']} },
  {path :'admin-home',component:AdminHomeComponent,canActivate:[AuthGuard], data:{roles:['Admin']} },
  {path:'admin-loans',component:AdminLoansComponent, canActivate:[AuthGuard], data:{roles:['Admin']} },
  {path:'admin-loan-application',component:AdminLoanApplicationComponent, canActivate:[AuthGuard], data:{roles:['Admin']} },
  {path:'loan-details',component:LoanDetailsComponent, canActivate:[AuthGuard], data:{roles:['User']} },
  {path:'add-loan',component:AddLoanComponent, canActivate:[AuthGuard], data:{roles:['Admin']} },
  {path:'adminHome',component:AdminHomeComponent,canActivate:[AuthGuard], data:{roles:['Admin']} },
<<<<<<< HEAD
  {path:'add-payment' , component:PaymentComponent,canActivate:[AuthGuard], data:{roles:['Admin']} },
=======
>>>>>>> 2792fb7a80a97530c689298accb04bb00bf54c26
  {path:'forbidden',component:ForbiddenComponent}
  

]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}