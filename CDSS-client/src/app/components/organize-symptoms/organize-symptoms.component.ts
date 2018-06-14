import { Component, OnInit } from '@angular/core';
import { IAppUser } from '../../model/IAppUser';
import { SearchCriteria } from '../../model/SearchCriteria';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { DoctorService } from '../../services/doctor/doctor.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { ISymptom } from '../../model/ISymptom';
import { SymptomService } from '../../services/symptoms/symptom.service';

@Component({
  selector: 'app-organize-symptoms',
  templateUrl: './organize-symptoms.component.html',
  styleUrls: ['./organize-symptoms.component.css']
})
export class OrganizeSymptomsComponent implements OnInit {

  symptoms: ISymptom[];
  active: ISymptom;
  addForm: FormGroup;
  
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private symptomService: SymptomService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  ngOnInit() {
    this.active = null;    
    this.createForm();
    this.criteria.sortColumn = 'name';
    this.criteria.sortDirection = 'desc';
    
    this.getAllSymptoms();
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
    this.getAllSymptoms();
  }

  sortSymptoms(criteria: SearchCriteria): ISymptom[] {
    console.log(criteria);
    return this.symptoms.sort((a, b) => {
      if (criteria.sortDirection === 'desc') {
        //console.log(a[criteria.sortColumn], b[criteria.sortColumn]);
        return a[criteria.sortColumn] < b[criteria.sortColumn] ? 1 : -1;
        // return a < b ? -1 : 1;
      } else {
        //console.log(a[criteria.sortColumn], b[criteria.sortColumn]);
        return a[criteria.sortColumn] > b[criteria.sortColumn] ? 1 : -1;
      }
    });
  }

  createForm() {
    if(this.active!==null){
      this.addForm = this.fb.group({
        name: [this.active.name, Validators.compose([Validators.required])]
      });
    }
    else{
      this.addForm = this.fb.group({
        name: ['', Validators.compose([Validators.required])]
      });
    }
    
  }



  getAllSymptoms(){
    this.symptomService.getAll(this.page, this.limit)
    .subscribe(data => {
        this.symptoms = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.symptoms = this.sortSymptoms(this.criteria);
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
    this.symptomService.getById(id)
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
    this.symptomService.add(this.addForm.value)
    .subscribe(data => {
        console.log(data);
        this.getAllSymptoms();
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
    this.symptomService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAllSymptoms();
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
    this.getAllSymptoms();
  }

  onNext(): void {
    this.page++;
    this.getAllSymptoms();
  }

  onPrev(): void {
    this.page--;
    this.getAllSymptoms();
  }

  onChangePerPage(n: number): void{
    console.log(n);
    this.limit=n;
    this.getAllSymptoms();
  }

}
