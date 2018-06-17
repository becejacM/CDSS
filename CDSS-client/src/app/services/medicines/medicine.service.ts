import { Injectable } from '@angular/core';
import { IMedicine } from '../../model/IMedicine';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
@Injectable()
export class MedicineService {

  constructor(private http: HttpClient) { }
  
    add(doc: IMedicine): Observable<IMedicine> {
      const urlPath = '/api/medicines';
      return this.http.post<IMedicine>(urlPath, doc);
    }
  
    update(doc: IMedicine,id:any): Observable<IMedicine> {
      const urlPath = '/api/medicines/'+id;
      return this.http.put<IMedicine>(urlPath, doc);
    }
  
    getAll(page: number, limit: number): Observable<IPageable> {
      page = page - 1;
      const urlPath = '/api/medicines?&page=' + page + '&size=' + limit;
      return this.http.get<IPageable>(urlPath);
    }
  
    getById(id:any): Observable<IMedicine> {
      const urlPath = '/api/medicines/'+id;
      return this.http.get<IMedicine>(urlPath);
    }
  
    checkIngredient(name:String): Observable<IMedicine> {
      console.log(name);
      const urlPath = '/api/medicines/checkIngredient/'+name;
      console.log(name);
      return this.http.get<IMedicine>(urlPath);
    }
    checkMedicine(name:String): Observable<IMedicine> {
      console.log(name);
      const urlPath = '/api/medicines/checkMedicine/'+name;
      console.log(name);
      return this.http.get<IMedicine>(urlPath);
    }
    delete(id:any) {
      const urlPath = '/api/medicines/'+id;
      return this.http.delete<IMedicine>(urlPath);
    }
}
