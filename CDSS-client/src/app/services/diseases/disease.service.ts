import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { IDisease } from '../../model/IDisease';

@Injectable()
export class DiseaseService {

  constructor(private http: HttpClient) { }
  
    add(doc: IDisease): Observable<IDisease> {
      const urlPath = '/api/diseases';
      return this.http.post<IDisease>(urlPath, doc);
    }
  
    update(doc: IDisease,id:any): Observable<IDisease> {
      const urlPath = '/api/diseases/'+id;
      return this.http.put<IDisease>(urlPath, doc);
    }
  
    getAll(page: number, limit: number): Observable<IPageable> {
      page = page - 1;
      const urlPath = '/api/diseases?&page=' + page + '&size=' + limit;
      return this.http.get<IPageable>(urlPath);
    }
  
    getById(id:any): Observable<IDisease> {
      const urlPath = '/api/diseases/'+id;
      return this.http.get<IDisease>(urlPath);
    }
  
    checkSymptom(name:String): Observable<IDisease> {
      console.log(name);
      const urlPath = '/api/diseases/checkSymptom/'+name;
      console.log(name);
      return this.http.get<IDisease>(urlPath);
    }

    delete(id:any) {
      const urlPath = '/api/diseases/'+id;
      return this.http.delete<IDisease>(urlPath);
    }
}
