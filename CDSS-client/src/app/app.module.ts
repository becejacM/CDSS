import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {CommonModule} from '@angular/common';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgxPermissionsModule} from 'ngx-permissions';
import {OwlDateTimeModule} from 'ng-pick-datetime/date-time/date-time.module';
import {OwlNativeDateTimeModule} from 'ng-pick-datetime/date-time/adapter/native-date-time.module';

import {RecaptchaModule} from 'ng-recaptcha';
import {RecaptchaFormsModule} from 'ng-recaptcha/forms';


import { AppComponent } from './app.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { NotFoundPageComponent } from './components/not-found-page/not-found-page.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AuthenticationService } from './services/authentication/authentication.service';
import { OnlyLoggedInGuard } from './guard/only-logged-in.guard';
import { AlreadyLoggedInGuard } from './guard/already-logged-in.guard';
import { TokenInterceptorService } from './services/token-interceptor/token-interceptor.service';
import { AppRoutingModule } from './app-routing.module';
import { DiagnosticProcessCardComponent } from './components/diagnostic-process-card/diagnostic-process-card.component';
import { DiagnosticProcessComponent } from './components/diagnostic-process/diagnostic-process.component';
import { SortableColumnComponent } from './components/sortable-column/sortable-column.component';
import { SortableColumnDirective } from './directives/sortable-column.directive';
import { PaginationComponent } from './components/pagination/pagination.component';
import { PatientService } from './services/patient/patient.service';
import { SortService } from './services/sort/sort.service';
import { DiagnosticProcessFormComponent } from './components/diagnostic-process-form/diagnostic-process-form.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    NavbarComponent,
    HomepageComponent,
    NotFoundPageComponent,
    ChangePasswordComponent,
    DiagnosticProcessCardComponent,
    DiagnosticProcessComponent,
    SortableColumnComponent,
    SortableColumnDirective,
    PaginationComponent,
    DiagnosticProcessFormComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    ToastrModule.forRoot(),
    NgxPermissionsModule.forRoot(),
    HttpClientModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    AppRoutingModule,
    BrowserModule,
    RecaptchaModule.forRoot(), // Keep in mind the "forRoot"-magic nuances!
    RecaptchaFormsModule, // if you need forms support

  ],
  providers: [AuthenticationService,
    PatientService,
    SortService,
    OnlyLoggedInGuard,
    AlreadyLoggedInGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },],
  bootstrap: [AppComponent]
})
export class AppModule { }
