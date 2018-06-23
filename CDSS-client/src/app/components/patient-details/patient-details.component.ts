import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../services/patient/patient.service';
import { ToastrService } from 'ngx-toastr';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { IPatient } from '../../model/IPatient';
import { IMedicine } from '../../model/IMedicine';
import { IIngredient } from '../../model/IIngredient';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  medicineAlergies:IMedicine[];
  ingredientsAlergies:IIngredient[];
  patient:IPatient;
  id:any;
  showAlergies:Boolean;
  toggleAlergie:Boolean;
  toggleMR:Boolean;
  
  addForm: FormGroup;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    console.log("evo meee");
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    console.log(this.id);
    this.getPatient();
    this.getAlergies();
    this.showAlergies = false;
    this.toggleAlergie = false;
    this.toggleMR = false;
    this.createForm();
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

  getAlergies(){
    this.patientService.getAlergies(this.id)
    .subscribe(data => {
      console.log(data);
        this.medicineAlergies = data.medicines;
        this.ingredientsAlergies = data.ingredients;
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  alergies(){
    this.showAlergies = !this.showAlergies;
    if(this.showAlergies === false){
      this.toggleAlergie = false;
    }
  }
  medicalRecord(){
    this.toggleMR = !this.toggleMR;
    this.showAlergies = false;
    this.toggleAlergie = false;
  }

  addAlergie(){
    this.toggleAlergie = !this.toggleAlergie;
  }

  submitAlergie(){
    console.log(this.addForm.value);
    this.patientService.addAlergie(this.id,this.addForm.value['name'])
    .subscribe(data => {
        console.log(data);
        this.getAlergies();
        this.toggleAlergie = false;
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  createForm() {
    
      this.addForm = this.fb.group({
        name: ['', Validators.compose([Validators.required])]
      });
    
  }
}

