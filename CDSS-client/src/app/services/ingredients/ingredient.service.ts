import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { IIngredient } from '../../model/IIngredient';

@Injectable()
export class IngredientService {

  constructor(private http: HttpClient) { }
  
    add(doc: IIngredient): Observable<IIngredient> {
      const urlPath = '/api/ingredients';
      return this.http.post<IIngredient>(urlPath, doc);
    }
  
    update(doc: IIngredient,id:any): Observable<IIngredient> {
      const urlPath = '/api/ingredients/'+id;
      return this.http.put<IIngredient>(urlPath, doc);
    }
  
    getAll(page: number, limit: number): Observable<IPageable> {
      page = page - 1;
      const urlPath = '/api/ingredients?&page=' + page + '&size=' + limit;
      return this.http.get<IPageable>(urlPath);
    }
  
    getById(id:any): Observable<IIngredient> {
      const urlPath = '/api/ingredients/'+id;
      return this.http.get<IIngredient>(urlPath);
    }
  
    delete(id:any) {
      const urlPath = '/api/ingredients/'+id;
      return this.http.delete<IIngredient>(urlPath);
    }

}
