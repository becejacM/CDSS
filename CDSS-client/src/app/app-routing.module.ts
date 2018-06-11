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
    {path: 'diagnostic-process', component: DiagnosticProcessComponent
  },
  {path: 'patient/:id', component: DiagnosticProcessFormComponent,canActivate: [OnlyLoggedInGuard]}
  
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
