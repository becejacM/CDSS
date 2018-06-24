import { Component, OnInit } from '@angular/core';
import { SearchCriteria } from '../../model/SearchCriteria';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DoctorService } from '../../services/doctor/doctor.service';
import { HttpErrorResponse } from '@angular/common/http/src/response';
import { DiseaseService } from '../../services/diseases/disease.service';
import { IDisease } from '../../model/IDisease';
import { DiseaseType } from '../../model/DiseaseType';
import { ISymptom } from '../../model/ISymptom';

@Component({
  selector: 'app-organize-diseases',
  templateUrl: './organize-diseases.component.html',
  styleUrls: ['./organize-diseases.component.css']
})
export class OrganizeDiseasesComponent implements OnInit {

  diseases: IDisease[];
  addForm: FormGroup;
  chack: Boolean;
  DiseaseType: typeof DiseaseType = DiseaseType;
  options:any;
  active:IDisease;
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private diseaseService: DiseaseService, private route: ActivatedRoute, private fb: FormBuilder) {
  }

  ngOnInit() {
    this.active = null;    
    
    this.chack = false;
    this.createForm();
    this.criteria.sortColumn = 'name';
    this.criteria.sortDirection = 'asc';

    this.getAllDiseases();
    this.toggleAdd = false;
  }


  add() {
    this.toggleAdd = !this.toggleAdd;

    this.createForm();
  }

  onSorted($event) {
    console.log($event);
    this.criteria = $event;
    this.getAllDiseases();
  }

  sortDiseases(criteria: SearchCriteria): IDisease[] {
    console.log(criteria);
    return this.diseases.sort((a, b) => {
      if (criteria.sortDirection === 'desc') {
        return a[criteria.sortColumn] < b[criteria.sortColumn] ? 1 : -1;
        // return a < b ? -1 : 1;
      } else {
        return a[criteria.sortColumn] > b[criteria.sortColumn] ? 1 : -1;
      }
    });
  }

  createForm() {

    const x = DiseaseType;
    const options = Object.keys(DiseaseType);
    this.options = options.slice(options.length / 2);

    if(this.active!==null){
      this.addForm = this.fb.group({
        name: [this.active.name, Validators.compose([Validators.required])]
      });
    }
    else{
    this.addForm = this.fb.group({
      name: ['', Validators.compose([Validators.required])],
      typeOfDisease: ['', Validators.compose([Validators.required])],
      sym: ['', Validators.compose([Validators.required])],
      symptoms: ['', Validators.compose([Validators.required])]

    });
  }


  }
  listOfSymptoms: ISymptom[] = new Array;
  Symptom: ISymptom = new ISymptom;
  Disease: IDisease = new IDisease;

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

  getAllDiseases() {
    this.diseaseService.getAll(this.page, this.limit)
      .subscribe(data => {
        this.diseases = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.diseases = this.sortDiseases(this.criteria);
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  getById(id: any) {
    this.diseaseService.getById(id)
      .subscribe(data => {
        console.log(data);
        this.toggleAdd = true;
        this.active=data;        
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
  addD() {
    if (this.chack === false) {
      console.log(this.addForm.value);
      this.Disease.name = this.addForm.value['name'];
      this.Disease.typeOfDisease = this.addForm.value['typeOfDisease'];
      this.Disease.symptoms = this.listOfSymptoms;

      this.diseaseService.add(this.Disease)
        .subscribe(data => {
          console.log(data);
          this.getAllDiseases();
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
  }

  updateD(){
    console.log(this.addForm.value);
    this.diseaseService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAllDiseases();
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
    this.getAllDiseases();
  }

  onNext(): void {
    this.page++;
    this.getAllDiseases();
  }

  onPrev(): void {
    this.page--;
    this.getAllDiseases();
  }

  onChangePerPage(n: number): void {
    console.log(n);
    this.limit = n;
    this.getAllDiseases();
  }


}
