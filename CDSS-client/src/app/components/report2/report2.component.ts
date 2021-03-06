import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { PatientService } from '../../services/patient/patient.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { IPatient } from '../../model/IPatient';
import { IReport } from '../../model/IReport';

@Component({
  selector: 'app-report2',
  templateUrl: './report2.component.html',
  styleUrls: ['./report2.component.css']
})
export class Report2Component implements OnInit {

  patients: IReport[];
  
  ngOnInit() {
    this.getAll();
  }

  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  getAll(){
    console.log("ovde");
    this.patientService.getReport2()
    .subscribe(data => {
        this.patients = data;
        console.log(this.patients);
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

}
