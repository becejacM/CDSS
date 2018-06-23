import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {NgxPermissionsService} from 'ngx-permissions';
import {ToastrService} from 'ngx-toastr';

import {LoggedUtils} from '../../utils/logged-utils';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { HttpErrorResponse } from '@angular/common/http/src/response';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
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
    
    this.initializeWebSocketConnection();
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

  private stompClient;
  ws:any;
  disabled: boolean;
  
  initializeWebSocketConnection(){
    if (this.ws != null) {
      console.log("disconnect");
      this.ws.ws.close();
    }
    //let ws = new SockJS(http://localhost:8080/greeting);
    //var socket = new SockJS('wss://localhost:8443/socket');
    //this.ws = Stomp.over(socket);
    console.log("connect");
    console.log(Stomp);
    let socket = new WebSocket("ws://localhost:8080/secured/sbnz");
    this.ws = Stomp.over(socket);
    let that = this;
    //this.sendName();
    this.ws.connect({}, function(frame) {
      
      that.ws.subscribe("/secured/monitoring", function(message) {
        console.log(message); 
        if(LoggedUtils.getUser()!==null){
          that.toastr.warning(message['body']);          
        }
      });
    }, function(error) {
      console.log("STOMP error " + error);
    });
  }
}
