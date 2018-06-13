import { Component, OnInit } from '@angular/core';
import { IPatient } from '../../model/IPatient';
import { SearchCriteria } from '../../model/SearchCriteria';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { PatientService } from '../../services/patient/patient.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';

@Component({
  selector: 'app-diagnostic-process',
  templateUrl: './diagnostic-process.component.html',
  styleUrls: ['./diagnostic-process.component.css']
})
export class DiagnosticProcessComponent implements OnInit {

  patients: IPatient[];
  addForm: FormGroup;
  addPatientForm: FormGroup;
  
  timeNow = new Date();

  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleFilter: boolean;
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  ngOnInit() {
    this.createForm();
    this.createPatientForm();
    this.criteria.sortColumn = 'firstname';
    this.criteria.sortDirection = 'desc';
    
    this.getAllPatients();
    
    this.toggleFilter = false;
    this.toggleAdd = false;
  }

  filter() {
    this.toggleFilter = !this.toggleFilter;
  }

  add() {
    this.toggleAdd = !this.toggleAdd;
  }

  onSorted($event) {
    console.log($event);
    this.criteria = $event;
    this.getAllPatients();
  }

  sortPatients(criteria: SearchCriteria): IPatient[] {
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
    this.addForm = this.fb.group({
      firstname: [''],
      lastname: [''],
      address: [''],
      medicalCardNumber: ['']
    });
  }

  createPatientForm() {
    this.addPatientForm = this.fb.group({
      firstname: ['', Validators.compose([Validators.required])],
      lastname: ['', Validators.compose([Validators.required])],
      address: ['', Validators.compose([Validators.required])],
      medicalCardNumber: ['', Validators.compose([Validators.required])],
      email: ['',  Validators.compose([Validators.required, Validators.email])]
    });
  }

  getAllPatients(){
    this.patientService.getAll(this.page, this.limit)
    .subscribe(data => {
        this.patients = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.patients = this.sortPatients(this.criteria);
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  getPatients() {
    console.log("krecem");
    
    if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['lastname'].value !== '' &&
      this.addForm.controls['medicalCardNumber'].value !== '' && this.addForm.controls['address'].value !== '') {
      this.getByFNLNMCNA(this.addForm.controls['firstname'].value,
        this.addForm.controls['lastname'].value, this.addForm.controls['medicalCardNumber'].value,
        this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['lastname'].value !== '' && this.addForm.controls['medicalCardNumber'].value !== '') {
      this.getByFNLNMCN(this.addForm.controls['firstname'].value, this.addForm.controls['lastname'].value,this.addForm.controls['medicalCardNumber'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['lastname'].value !== '' &&  this.addForm.controls['address'].value !== '') {
      this.getByFNLNA(this.addForm.controls['firstname'].value, this.addForm.controls['lastname'].value, this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['medicalCardNumber'].value !== '' &&
      this.addForm.controls['address'].value !== '') {
      this.getByFNMCNA(this.addForm.controls['firstname'].value, this.addForm.controls['medicalCardNumber'].value,
        this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['lastname'].value !== '' && this.addForm.controls['medicalCardNumber'].value !== '' && this.addForm.controls['address'].value !== '') {
      this.getByLNMCNA(this.addForm.controls['lastname'].value, this.addForm.controls['medicalCardNumber'].value,this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['address'].value !== '' && this.addForm.controls['medicalCardNumber'].value !== '') {
      this.getByAMCN(this.addForm.controls['address'].value,this.addForm.controls['medicalCardNumber'].value, this.criteria);
    } else if (this.addForm.controls['lastname'].value !== '' && this.addForm.controls['medicalCardNumber'].value!== '') {
      this.getByLNMCN(this.addForm.controls['lastnamemac'].value,this.addForm.controls['medicalCardNumber'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['lastname'].value !== '') {
      this.getByFNLN(this.addForm.controls['firstname'].value,this.addForm.controls['lastname'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['medicalCardNumber'].value !== '') {
      this.getByFNMCN(this.addForm.controls['firstname'].value, this.addForm.controls['medicalCardNumber'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '' && this.addForm.controls['address'].value !== '') {
      this.getByFNA(this.addForm.controls['firstname'].value, this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['lastname'].value !== '' && this.addForm.controls['address'].value !== '') {
      this.getByLNA(this.addForm.controls['lastname'].value, this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['address'].value !== '') {
      this.getByA(this.addForm.controls['address'].value, this.criteria);
    } else if (this.addForm.controls['lastname'].value !== '') {
      this.getByLN(this.addForm.controls['lastname'].value, this.criteria);
    } else if (this.addForm.controls['firstname'].value !== '') {
      this.getByFN(this.addForm.controls['firstname'].value, this.criteria);
    } else if (this.addForm.controls['medicalCardNumber'].value !== '') {
      this.getByMCN(this.addForm.controls['medicalCardNumber'].value, this.criteria);
    } else {
      console.log('sviii');
      this.getAllPatients();
    }
  }

  getByFNLNMCNA(firstname: String, lastname: String, medicalCardNumber: String, address:String, criteria: SearchCriteria) {
    this.patientService.getByFNLNMCNA(firstname, lastname, medicalCardNumber,address, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNLNMCN(firstname: String, lastname: String,medicalCardNumber:String, criteria: SearchCriteria) {
    this.patientService.getByFNLNMCN(firstname,lastname,medicalCardNumber, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNLNA(firstname: String, lastname: String,address:String, criteria: SearchCriteria) {
    this.patientService.getByFNLNA(firstname, lastname, address, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNMCNA(firstname: String, medicalCardNumber: String, address: String, criteria: SearchCriteria) {
    this.patientService.getByFNMCNA(firstname, medicalCardNumber, address, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByLNMCNA(lastname: String,medicalCardNumber: String, address: String, criteria: SearchCriteria) {
    this.patientService.getByLNMCNA(lastname, medicalCardNumber, address, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByAMCN(a: String,mcn: String, criteria: SearchCriteria) {
    this.patientService.getByAMCN(a, mcn, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByLNMCN(ln: String, mcn: String, criteria: SearchCriteria) {
    this.patientService.getByLNMCN(ln, mcn, this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNLN(fn: String, ln: String, criteria: SearchCriteria) {
    this.patientService.getByFNLN(fn, ln,this.page, this.limit)
      .subscribe(data => {
          alert(data['content']);
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNMCN(fn: String, mcn:String, criteria: SearchCriteria) {
    this.patientService.getByFNMCN(fn, mcn,this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFNA(fn: String,a:String, criteria: SearchCriteria) {
    this.patientService.getByFNA(fn, a, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByLNA(ln: String, a: String, criteria: SearchCriteria) {
    this.patientService.getByLNA(ln, a, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByMCN(medicalCardNumber:String, criteria: SearchCriteria) {
    this.patientService.getByMCN(medicalCardNumber, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByA(address: string, criteria: SearchCriteria) {
    this.patientService.getByA(address, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByLN(lastname: string, criteria: SearchCriteria) {
    this.patientService.getByLN(lastname, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  getByFN(firstname: string, criteria: SearchCriteria) {
    this.patientService.getByFN(firstname, this.page, this.limit)
      .subscribe(data => {
          this.patients = data['content'];
          this.total = data['totalElements'];
          this.loading = false;
          this.patients = this.sortPatients(criteria);

        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  addPatient(){
    this.patientService.add(this.addPatientForm.value)
    .subscribe(data => {
        console.log(data);
        this.getPatients();
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

  submit() {
    this.getPatients();
    this.toggleFilter = false;
  }

  submitPatient() {
    console.log(this.addPatientForm.value);    
   this.addPatient();
  }

  goToPage(n: number): void {
    this.page = n;
    this.getPatients();
  }

  onNext(): void {
    this.page++;
    this.getPatients();
  }

  onPrev(): void {
    this.page--;
    this.getPatients();
  }

  onChangePerPage(n: number): void{
    console.log(n);
    this.limit=n;
    console.log("Evoe me" +this.limit);
    this.getPatients();
  }

}
