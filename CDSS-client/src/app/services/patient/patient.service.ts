import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { IPatient } from '../../model/IPatient';

@Injectable()
export class PatientService {

  constructor(private http: HttpClient) { }

  getAll(page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/patients?&page=' + page + '&size=' + limit;
    return this.http.get<IPageable>(urlPath);
  }

  getById(id:any): Observable<IPatient> {
    const urlPath = '/api/patients/'+id;
    return this.http.get<IPatient>(urlPath);
  }

  getByFN(fn:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/patients/filter?firstname='+fn+'&page=' + page + '&size=' + limit;
    console.log(urlPath);
    return this.http.get<IPageable>(urlPath);
  }

  getByLN(ln:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/patients/filter?lastname='+ln+'&page=' + page + '&size=' + limit;
    return this.http.get<IPageable>(urlPath);
  }

  getByA(a: String,page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/patients/filter?address='+a+'&page=' + page + '&size=' + limit;
    return this.http.get<IPageable>(urlPath);
  }

  
  getByFNLNA(fn:String,ln:String,a:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname',String(fn));        
    params = params.append('lastname', String(ln));   
    params = params.append('address',String(a));        
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNLNMCN(fn:String,ln:String,mcn:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname',String(fn));        
    params = params.append('lastname', String(ln));   
    params = params.append('medicalCardNumber',String(mcn));        
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByMCN(mcn:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('medicalCardNumber',String(mcn));
    params = params.append('page', String(page));
    params = params.append('size', String(limit));

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNLNMCNA(fn:String,ln: String,mcn:String, a:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));
    params = params.append('lastname', String(ln));   
    params = params.append('medicalCardNumber', String(mcn));    
    params = params.append('address',String(a));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNMCNA(fn:String,mcn:String,a:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));   
    params = params.append('medicalCardNumber',String(mcn));    
    params = params.append('address',String(a));        
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByAMCN(a:String,mcn:String,  page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('address', String(a));   
    params = params.append('medicalCardNumber',String(mcn));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByLNMCNA(ln:String,mcn:String,a:String, page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('lastname', String(ln));   
    params = params.append('medicalCardNumber',String(mcn));    
    params = params.append('address',String(a));        
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByLNA(ln:String,a: String,page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('lastname', String(ln));
    params = params.append('address', String(a));
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNA(fn:String,a:String,  page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));   
    params = params.append('address',String(a));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByFNMCN(fn:String,mcn:String,  page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('firstname', String(fn));   
    params = params.append('medicalCardNumber',String(mcn));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
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
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }

  getByLNMCN(ln:String,mcn:String,  page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    let params = new HttpParams();
    params = params.append('lastname', String(ln));   
    params = params.append('medicalCardNumber',String(mcn));    
    params = params.append('page', String(page));
    params = params.append('size', String(limit));
    

    // const urlPath = '/api/center/logs/regex?regex=' + reg + '&page=' + page + '&size=' + limit;
    const urlPath = '/api/patients/filter';    
    return this.http.get<IPageable>(urlPath,{params:params});
  }
}
