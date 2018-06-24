import { Component, OnInit } from '@angular/core';
import { IMedicine } from '../../model/IMedicine';
import { SearchCriteria } from '../../model/SearchCriteria';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DoctorService } from '../../services/doctor/doctor.service';
import { HttpErrorResponse } from '@angular/common/http/src/response';
import { MedicineService } from '../../services/medicines/medicine.service';
import { IIngredient } from '../../model/IIngredient';
import { MedicineType } from '../../model/MedicineType';

@Component({
  selector: 'app-organize-medicines',
  templateUrl: './organize-medicines.component.html',
  styleUrls: ['./organize-medicines.component.css']
})
export class OrganizeMedicinesComponent implements OnInit {


  medicines: IMedicine[];
  addForm: FormGroup;
  checkForm: FormGroup;
  chack: Boolean;
  damageType: MedicineType;
  MedicineType: typeof MedicineType = MedicineType;
  options:any;
  active:IMedicine;
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private medicineService: MedicineService, private route: ActivatedRoute, private fb: FormBuilder) {
  }

  ngOnInit() {
    this.chack = false;
    this.active=null;
    
    this.createForm();
    this.criteria.sortColumn = 'name';
    this.criteria.sortDirection = 'asc';
    this.getAllMedicines();
    this.toggleAdd = false;
  }


  add() {
    this.toggleAdd = !this.toggleAdd;

    this.createForm();
  }

  onSorted($event) {
    console.log($event);
    this.criteria = $event;
    this.getAllMedicines();
  }

  sortMedicines(criteria: SearchCriteria): IMedicine[] {
    console.log(criteria);
    return this.medicines.sort((a, b) => {
      if (criteria.sortDirection === 'desc') {
        return a[criteria.sortColumn] < b[criteria.sortColumn] ? 1 : -1;
        // return a < b ? -1 : 1;
      } else {
        return a[criteria.sortColumn] > b[criteria.sortColumn] ? 1 : -1;
      }
    });
  }

  createForm() {
    const x = MedicineType;
    const options = Object.keys(MedicineType);
    this.options = options.slice(options.length / 2);
    
    if(this.active!==null){
      this.addForm = this.fb.group({
        name: [this.active.name, Validators.compose([Validators.required])]
      });
    }
    else{
    this.addForm = this.fb.group({
      name: ['', Validators.compose([Validators.required])],
      typeOfMedicine: ['', Validators.compose([Validators.required])],
      mi: ['', Validators.compose([Validators.required])],
      ingredient: ['', Validators.compose([Validators.required])]

    });
  }


  }
  listOfIngredients: IIngredient[] = new Array;
  Ingredient: IIngredient = new IIngredient;
  Medicine: IMedicine = new IMedicine;

  checkIngredient() {
    this.chack = true;
    if (this.addForm.value['ingredient'] != "") {
      console.log(this.addForm.value['ingredient']);
      this.Ingredient.name = this.addForm.value['ingredient'];
      this.medicineService.checkIngredient(this.Ingredient.name)
        .subscribe(data => {
          console.log(data);
          let newValue =this.addForm.value["mi"] + " " + this.Ingredient.name;
          this.addForm.controls["mi"].setValue(newValue);
          this.listOfIngredients.push(this.Ingredient);
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

  getAllMedicines() {
    this.medicineService.getAll(this.page, this.limit)
      .subscribe(data => {
        this.medicines = data['content'];
        this.total = data['totalElements'];
        console.log(this.medicines);
        this.loading = false;
        this.medicines = this.sortMedicines(this.criteria);
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
    this.medicineService.getById(id)
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
      this.Medicine.name = this.addForm.value['name'];
      this.Medicine.typeOfMedicine = this.addForm.value['typeOfMedicine'];
      this.Medicine.mi = this.listOfIngredients;

      this.medicineService.add(this.Medicine)
        .subscribe(data => {
          console.log(data);
          this.getAllMedicines();
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
    this.medicineService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAllMedicines();
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

  update(id: any) {
    console.log(id);
    this.getById(id);
  }

  goToPage(n: number): void {
    this.page = n;
    this.getAllMedicines();
  }

  onNext(): void {
    this.page++;
    this.getAllMedicines();
  }

  onPrev(): void {
    this.page--;
    this.getAllMedicines();
  }

  onChangePerPage(n: number): void {
    console.log(n);
    this.limit = n;
    this.getAllMedicines();
  }

}
