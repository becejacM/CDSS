import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../services/patient/patient.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { IPatient } from '../../model/IPatient';
import { DiseaseService } from '../../services/diseases/disease.service';
import { ISymptom } from '../../model/ISymptom';
import { IDisease } from '../../model/IDisease';
import { ListOfSymptoms } from '../../model/ListOfSymptoms';

@Component({
  selector: 'app-diagnostic-process-form',
  templateUrl: './diagnostic-process-form.component.html',
  styleUrls: ['./diagnostic-process-form.component.css']
})
export class DiagnosticProcessFormComponent implements OnInit {

  
  id:any;
  addForm: FormGroup;
  chack: Boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService,private diseaseService: DiseaseService, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    console.log("evo meee");
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    console.log(this.id);
    this.chack = false;
    this.createForm();
    
  }
  createForm() {
    this.addForm = this.fb.group({
      sym: ['', Validators.compose([Validators.required])],
      symptoms: ['', Validators.compose([Validators.required])]

    });
  }
  
  listOfSymptoms: ISymptom[] = new Array;
  Symptom: ISymptom = new ISymptom;
  Disease: IDisease = new IDisease;
  list : ListOfSymptoms = new ListOfSymptoms;
  
  checkSymptom() {
    this.chack = true;
    if (this.addForm.value['sym'] != "") {
      console.log(this.addForm.value['sym']);
      this.Symptom.name = this.addForm.value['sym'];
      this.diseaseService.checkSymptom(this.Symptom.name)
        .subscribe(data => {
          console.log(data);
          let newValue =this.addForm.value["symptoms"] + " " + this.Symptom.name;
          this.addForm.controls["symptoms"].setValue(newValue);
          this.listOfSymptoms.push(this.Symptom);
          this.list.symptoms.push(this.Symptom.name);
          this.chack = false;
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
  submit() {
    this.diseaseService.getDiagnose(this.list)
    .subscribe(data => {
        console.log(data);
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
