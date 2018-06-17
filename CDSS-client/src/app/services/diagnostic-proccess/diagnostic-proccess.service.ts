import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { ITherapy } from '../../model/ITherapy';
@Injectable()
export class DiagnosticProccessService {

  constructor(private http: HttpClient) { }
  
    add(doc: ITherapy): Observable<ITherapy> {
      const urlPath = '/api/diagnostic_proccess';
      return this.http.post<ITherapy>(urlPath, doc);
    }
  
    getAll(page: number, limit: number): Observable<IPageable> {
      page = page - 1;
      const urlPath = '/api/diagnostic_proccess?&page=' + page + '&size=' + limit;
      return this.http.get<IPageable>(urlPath);
    }
  
    getById(id:any): Observable<ITherapy> {
      const urlPath = '/api/diagnostic_proccess/'+id;
      return this.http.get<ITherapy>(urlPath);
    }

    validate(doc: ITherapy): Observable<ITherapy> {
      const urlPath = '/api/diagnostic_proccess/validate';
      return this.http.post<ITherapy>(urlPath, doc);
    }
}
