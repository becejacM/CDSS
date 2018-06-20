import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {HomepageComponent} from './components/homepage/homepage.component';
import {OnlyLoggedInGuard} from './guard/only-logged-in.guard';
import {AuthenticationComponent} from './components/authentication/authentication.component';
import {AlreadyLoggedInGuard} from './guard/already-logged-in.guard';
import {ChangePasswordComponent} from './components/change-password/change-password.component';
import { DiagnosticProcessCardComponent } from './components/diagnostic-process-card/diagnostic-process-card.component';
import { DiagnosticProcessComponent } from './components/diagnostic-process/diagnostic-process.component';
import { DiagnosticProcessFormComponent } from './components/diagnostic-process-form/diagnostic-process-form.component';
import { McardComponent } from './components/mcard/mcard.component';
import { MonitoringComponent } from './components/monitoring/monitoring.component';
import { RcardComponent } from './components/rcard/rcard.component';
import { ReportingComponent } from './components/reporting/reporting.component';
import { OrganizeCardComponent } from './components/organize-card/organize-card.component';
import { OrganizeDoctorsComponent } from './components/organize-doctors/organize-doctors.component';
import { OrganizePatientsComponent } from './components/organize-patients/organize-patients.component';
import { OrganizeSymptomsComponent } from './components/organize-symptoms/organize-symptoms.component';
import { OrganizeDiseasesComponent } from './components/organize-diseases/organize-diseases.component';
import { OrganizeMedicinesComponent } from './components/organize-medicines/organize-medicines.component';
import { PatientDetailsComponent } from './components/patient-details/patient-details.component';
import { OrganizeIngredientsComponent } from './components/organize-ingredients/organize-ingredients.component';
import { Report1Component } from './components/report1/report1.component';
import { Report2Component } from './components/report2/report2.component';
import { Report3Component } from './components/report3/report3.component';


const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {path: 'login', component: AuthenticationComponent, canActivate: [AlreadyLoggedInGuard]},
  {path: 'home', component: HomepageComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'change-password', component: ChangePasswordComponent},
  {
    path: 'dpcard', component: DiagnosticProcessCardComponent, canActivate: [OnlyLoggedInGuard], children: [
    {path: 'diagnostic-process', component: DiagnosticProcessComponent,canActivate: [OnlyLoggedInGuard]},
  {path: 'patient/:id', component: DiagnosticProcessFormComponent,canActivate: [OnlyLoggedInGuard]}
  
  ]},
  {
    path: 'mcard', component: McardComponent, canActivate: [OnlyLoggedInGuard], children: [
    {path: 'monitoring', component: MonitoringComponent,canActivate: [OnlyLoggedInGuard]},
  
  ]},
  {
    path: 'rcard', component: RcardComponent, canActivate: [OnlyLoggedInGuard], children: [
    {path: 'reporting', component: ReportingComponent,canActivate: [OnlyLoggedInGuard]
    , children: [
      {path: 'report1', component: Report1Component,canActivate: [OnlyLoggedInGuard]},
      {path: 'report2', component: Report2Component,canActivate: [OnlyLoggedInGuard]},
      {path: 'report3', component: Report3Component,canActivate: [OnlyLoggedInGuard]}, 
    ]
  
  },
    
  
  ]},
  {
    path: 'organizecard', component: OrganizeCardComponent, canActivate: [OnlyLoggedInGuard], children: [
    {path: 'doctors', component: OrganizeDoctorsComponent,canActivate: [OnlyLoggedInGuard]},
    {path: 'patients', component: OrganizePatientsComponent,canActivate: [OnlyLoggedInGuard]},
    {path: 'diseases', component: OrganizeDiseasesComponent,canActivate: [OnlyLoggedInGuard]},
    {path: 'symptoms', component: OrganizeSymptomsComponent,canActivate: [OnlyLoggedInGuard]},
    {path: 'medicines', component: OrganizeMedicinesComponent,canActivate: [OnlyLoggedInGuard]},
    {path: 'ingredients', component: OrganizeIngredientsComponent,canActivate: [OnlyLoggedInGuard]},    
    {path: 'patientDetails/:id', component: PatientDetailsComponent, canActivate: [OnlyLoggedInGuard]},    
  ]},
  
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
