import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { ISymptom } from '../../model/ISymptom';

@Injectable()
export class SymptomService {

  constructor(private http: HttpClient) { }
  
    add(doc: ISymptom): Observable<ISymptom> {
      const urlPath = '/api/symptoms';
      return this.http.post<ISymptom>(urlPath, doc);
    }
  
    update(doc: ISymptom,id:any): Observable<ISymptom> {
      const urlPath = '/api/symptoms/'+id;
      return this.http.put<ISymptom>(urlPath, doc);
    }
  
    getAll(page: number, limit: number): Observable<IPageable> {
      page = page - 1;
      const urlPath = '/api/symptoms?&page=' + page + '&size=' + limit;
      return this.http.get<IPageable>(urlPath);
    }
  
    getById(id:any): Observable<ISymptom> {
      const urlPath = '/api/symptoms/'+id;
      return this.http.get<ISymptom>(urlPath);
    }
  
    delete(id:any) {
      const urlPath = '/api/symptoms/'+id;
      return this.http.delete<ISymptom>(urlPath);
    }

}
