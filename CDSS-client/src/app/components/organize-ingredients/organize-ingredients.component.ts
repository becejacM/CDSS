import { Component, OnInit } from '@angular/core';
import { SearchCriteria } from '../../model/SearchCriteria';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import { DoctorService } from '../../services/doctor/doctor.service';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { ISymptom } from '../../model/ISymptom';
import { SymptomService } from '../../services/symptoms/symptom.service';
import { IIngredient } from '../../model/IIngredient';
import { IngredientService } from '../../services/ingredients/ingredient.service';

@Component({
  selector: 'app-organize-ingredients',
  templateUrl: './organize-ingredients.component.html',
  styleUrls: ['./organize-ingredients.component.css']
})
export class OrganizeIngredientsComponent implements OnInit {

  ingredients: IIngredient[];
  active: IIngredient;
  addForm: FormGroup;
  
  // Pagable
  loading = false;
  total = 0;
  page = 1;
  limit = 20;
  criteria = new SearchCriteria();
  toggleAdd: boolean;
  constructor(private router: Router, private toastr: ToastrService,
    private ingredientService: IngredientService, private route: ActivatedRoute, private fb: FormBuilder) {
}

  ngOnInit() {
    this.active = null;    
    this.createForm();
    this.criteria.sortColumn = 'name';
    this.criteria.sortDirection = 'asc';
    
    this.getAllIngredients();
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
    this.getAllIngredients();
  }

  sortIngredients(criteria: SearchCriteria): IIngredient[] {
    console.log(criteria);
    return this.ingredients.sort((a, b) => {
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



  getAllIngredients(){
    this.ingredientService.getAll(this.page, this.limit)
    .subscribe(data => {
        this.ingredients = data['content'];
        this.total = data['totalElements'];
        this.loading = false;
        this.ingredients = this.sortIngredients(this.criteria);
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
    this.ingredientService.getById(id)
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
    this.ingredientService.add(this.addForm.value)
    .subscribe(data => {
        console.log(data);
        this.getAllIngredients();
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
    this.ingredientService.update(this.addForm.value,this.active.id)
    .subscribe(data => {
        console.log(data);
        this.getAllIngredients();
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
    this.getAllIngredients();
  }

  onNext(): void {
    this.page++;
    this.getAllIngredients();
  }

  onPrev(): void {
    this.page--;
    this.getAllIngredients();
  }

  onChangePerPage(n: number): void{
    console.log(n);
    this.limit=n;
    this.getAllIngredients();
  }

}
