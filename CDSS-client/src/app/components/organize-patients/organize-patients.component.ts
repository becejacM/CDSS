import { Component, OnInit } from '@angular/core';
import { IPatient } from '../../model/IPatient';
import { SearchCriteria } from '../../model/SearchCriteria';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { PatientService } from '../../services/patient/patient.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';
@Component({
  selector: 'app-organize-patients',
  templateUrl: './organize-patients.component.html',
  styleUrls: ['./organize-patients.component.css']
})
export class OrganizePatientsComponent implements OnInit {

  patients: IPatient[];
  active: IPatient;
  addForm: FormGroup;
  
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  ngOnInit() {
    this.active = null;    
    this.createForm();
    this.criteria.sortColumn = 'firstname';
    this.criteria.sortDirection = 'desc';
    
    this.getAll();
    this.toggleAdd = false;
  }


  add() {
    this.toggleAdd = !this.toggleAdd;
    if(this.toggleAdd===false){
      this.active = null;
    }
    this.createForm();
  }

  onSorted($event) {
    console.log($event);
    this.criteria = $event;
    this.getAll();
  }

  sortDoctors(criteria: SearchCriteria): IPatient[] {
    console.log(criteria);
    return this.patients.sort((a, b) => {
      if (criteria.sortDirection === 'desc') {
        console.log(a[criteria.sortColumn], b[criteria.sortColumn]);
        return a[criteria.sortColumn] < b[criteria.sortColumn] ? 1 : -1;
        // return a < b ? -1 : 1;
      } else {
        console.log(a[criteria.sortColumn], b[criteria.sortColumn]);
        return a[criteria.sortColumn] > b[criteria.sortColumn] ? 1 : -1;
      }
    });
  }

  createForm() {
    if(this.active!==null){
      this.addForm = this.fb.group({
        firstname: [this.active.firstname, Validators.compose([Validators.required])],
        lastname: [this.active.lastname, Validators.compose([Validators.required])],
        medicalCardNumber: [this.active.medicalCardNumber, Validators.compose([Validators.required])],
        address: [this.active.address, Validators.compose([Validators.required])],
        email: [this.active.email,  Validators.compose([Validators.required, Validators.email])]
      });
    }
    else{
      this.addForm = this.fb.group({
        firstname: ['', Validators.compose([Validators.required])],
        lastname: ['', Validators.compose([Validators.required])],
        medicalCardNumber: ['', Validators.compose([Validators.required])],
        address: ['', Validators.compose([Validators.required])],
        email: ['',  Validators.compose([Validators.required, Validators.email])]
      });
    }
    
  }



  getAll(){
    this.patientService.getAll(this.page, this.limit)
    .subscribe(data => {
        this.patients = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.patients = this.sortDoctors(this.criteria);
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  getById(id:any){
    this.patientService.getById(id)
    .subscribe(data => {
        console.log(data);
        this.toggleAdd = true;
        this.active = data;
        this.createForm();
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }
  addD(){
    console.log(this.addForm.value);
    this.patientService.add(this.addForm.value)
    .subscribe(data => {
        console.log(data);
        this.getAll();
        this.toggleAdd = false;
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  updateD(){
    console.log(this.addForm.value);
    this.patientService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAll();
        this.toggleAdd = false;
        this.active = null;
        this.createForm();
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }
  submit() {
    if(this.active===null){
      this.addD();  
    }
    else{
      console.log("update");
      this.updateD();      
    }
  }

  update(id:any){
    console.log(id);
    this.getById(id);
  }

  goToPage(n: number): void {
    this.page = n;
    this.getAll();
  }

  onNext(): void {
    this.page++;
    this.getAll();
  }

  onPrev(): void {
    this.page--;
    this.getAll();
  }

  onChangePerPage(n: number): void{
    console.log(n);
    this.limit=n;
    this.getAll();
  }

}
