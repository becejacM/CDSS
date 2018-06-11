import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../services/patient/patient.service';
import { ToastrService } from 'ngx-toastr';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { IPatient } from '../../model/IPatient';

@Component({
  selector: 'app-diagnostic-process-form',
  templateUrl: './diagnostic-process-form.component.html',
  styleUrls: ['./diagnostic-process-form.component.css']
})
export class DiagnosticProcessFormComponent implements OnInit {

  patient:IPatient;
  id:any;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    console.log("evo meee");
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    console.log(this.id);
    this.getPatient();
  }

  getPatient(){
    this.patientService.getById(this.id)
    .subscribe(data => {
      console.log(data);
        this.patient = data;
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
