import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { IPatient } from '../../model/IPatient';
import { IMedicine } from '../../model/IMedicine';
import { IAlergie } from '../../model/IAlergie';
import { IReport } from '../../model/IReport';
import { IMedicalRecord } from '../../model/IMedicalRecord';

@Injectable()
export class PatientService {

  constructor(private http: HttpClient) { }

  add(patient: IPatient): Observable<IPatient> {
    const urlPath = '/api/patients';
    return this.http.post<IPatient>(urlPath, patient);
  }

  addAlergie(id: any, name:String): Observable<IPatient> {
    const urlPath = '/api/patients/alergies/'+id+"/"+name;
    return this.http.put<IPatient>(urlPath,null);
  }

  update(patient: IPatient, id:any): Observable<IPatient> {
    const urlPath = '/api/patients/'+id;
    return this.http.put<IPatient>(urlPath, patient);
  }

  getReport1(): Observable<IReport[]> {
    const urlPath = '/api/patients/report1';
    return this.http.get<IReport[]>(urlPath);
  }

  getReport2(): Observable<IReport[]> {
    const urlPath = '/api/patients/report2';
    return this.http.get<IReport[]>(urlPath);
  }

  getReport3(): Observable<IReport[]> {
    const urlPath = '/api/patients/report3';
    return this.http.get<IReport[]>(urlPath);
  }
  getAll(page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/patients?&page=' + page + '&size=' + limit;
    return this.http.get<IPageable>(urlPath);
  }

  getById(id:any): Observable<IPatient> {
    const urlPath = '/api/patients/'+id;
    return this.http.get<IPatient>(urlPath);
  }

  getAlergies(id:any): Observable<IAlergie> {
    const urlPath = '/api/patients/alergies/'+id;
    return this.http.get<IAlergie>(urlPath);
  }

  getMR(id:any): Observable<IMedicalRecord[]> {
    const urlPath = '/api/patients/mrecords/'+id;
    return this.http.get<IMedicalRecord[]>(urlPath);
  }

  getByFN(fn:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));   
    params = params.append('page', String(page));
    params = params.append('size', String(limit));

    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByLN(ln:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('lastname',String(ln));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNLN(fn:String,ln:String,  page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));   
    params = params.append('lastname',String(ln));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }


}
