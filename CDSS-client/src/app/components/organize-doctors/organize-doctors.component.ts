import { Component, OnInit } from '@angular/core';
import { IAppUser } from '../../model/IAppUser';
import { SearchCriteria } from '../../model/SearchCriteria';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { DoctorService } from '../../services/doctor/doctor.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';

@Component({
  selector: 'app-organize-doctors',
  templateUrl: './organize-doctors.component.html',
  styleUrls: ['./organize-doctors.component.css']
})
export class OrganizeDoctorsComponent implements OnInit {

  doctors: IAppUser[];
  active: IAppUser;
  addForm: FormGroup;
  
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private doctorService: DoctorService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  ngOnInit() {
    this.active = null;    
    this.createForm();
    this.criteria.sortColumn = 'firstname';
    this.criteria.sortDirection = 'desc';
    
    this.getAllDoctors();
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
    this.getAllDoctors();
  }

  sortDoctors(criteria: SearchCriteria): IAppUser[] {
    console.log(criteria);
    return this.doctors.sort((a, b) => {
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
        username: [''],
        password: [''],
        email: [this.active.email,  Validators.compose([Validators.required, Validators.email])]
      });
    }
    else{
      this.addForm = this.fb.group({
        firstname: ['', Validators.compose([Validators.required])],
        lastname: ['', Validators.compose([Validators.required])],
        username: ['', Validators.compose([Validators.required])],
        password: ['', Validators.compose([Validators.required])],
        email: ['',  Validators.compose([Validators.required, Validators.email])]
      });
    }
    
  }



  getAllDoctors(){
    this.doctorService.getAll(this.page, this.limit)
    .subscribe(data => {
        this.doctors = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.doctors = this.sortDoctors(this.criteria);
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
    this.doctorService.getById(id)
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
    this.doctorService.add(this.addForm.value)
    .subscribe(data => {
        console.log(data);
        this.getAllDoctors();
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
    this.doctorService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAllDoctors();
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
    this.getAllDoctors();
  }

  onNext(): void {
    this.page++;
    this.getAllDoctors();
  }

  onPrev(): void {
    this.page--;
    this.getAllDoctors();
  }

  onChangePerPage(n: number): void{
    console.log(n);
    this.limit=n;
    this.getAllDoctors();
  }

}
