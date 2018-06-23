import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {NgxPermissionsService} from 'ngx-permissions';
import {ToastrService} from 'ngx-toastr';

import {LoggedUtils} from '../../utils/logged-utils';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { HttpErrorResponse } from '@angular/common/http/src/response';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  private perm;

  constructor(private router: Router, private toastr: ToastrService, 
    private permissionsService: NgxPermissionsService, private autheticationService: AuthenticationService) {
  }

  ngOnInit() {
    console.log(LoggedUtils.getRoles());
    const perm = [];
    
    perm.push(LoggedUtils.getRole());
    this.permissionsService.loadPermissions(perm);
    this.permissionsService.permissions$.subscribe((permisios) => {
    });
    
  }

  logoutf() {
    this.autheticationService.logout().subscribe(
      data => {
        LoggedUtils.clearLocalStorage();
        this.router.navigate(['/login']);
        this.permissionsService.flushPermissions();
        this.toastr.success('You are logged out');
      },(err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
    
  }


  getId(): number {
    return LoggedUtils.getId();
  }


}
