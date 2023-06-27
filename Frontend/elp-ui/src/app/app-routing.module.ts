import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import {HomeComponent} from './home/home.component';
import { ApplyLoanFormComponent } from './apply-loan-form/apply-loan-form.component';
import {LoansComponent} from './loans/loans.component';
import {ProfileComponent} from './profile/profile.component';
<<<<<<< HEAD
import {LoginComponent} from './login/login.component';
=======
import { AdminHomeComponent } from './admin-home/admin-home.component'
import {LoginComponent} from './login/login.component'


>>>>>>> 66a0c15bf4ea54d4c0d157f6c390f1a04d927b26
// import {ContactComponent} from './contact/contact.component';

// import { HomeComponent } from './home/home.component';

const routes: Routes = [
  // { path: '', component: AppComponent },
<<<<<<< HEAD
  { path: 'home', component: HomeComponent },
  { path: 'apply-loan-form', component: ApplyLoanFormComponent },
  { path: 'loans', component: LoansComponent },
  {path:'profile', component: ProfileComponent},
  {path:'login',component:LoginComponent}
=======
  {path:'',component:LoginComponent},
  { path: 'userHome', component: HomeComponent },
  { path: 'apply-loan-form', component: ApplyLoanFormComponent },
  { path: 'loans', component: LoansComponent },
  {path:'profile', component: ProfileComponent},
  {path:'adminHome',component:AdminHomeComponent}
>>>>>>> 66a0c15bf4ea54d4c0d157f6c390f1a04d927b26
  // {path:'contact',component: ContactComponent}



  // { path: 'home', component: HomeComponent },
  // { path: 'about', component: AboutComponent },
  // { path: 'contact', component: ContactComponent 
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
