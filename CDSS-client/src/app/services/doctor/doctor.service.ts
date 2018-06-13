import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {IPageable} from '../../model/IPageable';
import { IAppUser } from '../../model/IAppUser';

@Injectable()
export class DoctorService {

  constructor(private http: HttpClient) { }

  add(doc: IAppUser): Observable<IAppUser> {
    const urlPath = '/api/users';
    return this.http.post<IAppUser>(urlPath, doc);
  }

  update(doc: IAppUser,id:any): Observable<IAppUser> {
    const urlPath = '/api/users/'+id;
    return this.http.put<IAppUser>(urlPath, doc);
  }

  getAll(page: number, limit: number): Observable<IPageable> {
    page = page - 1;
    const urlPath = '/api/users?&page=' + page + '&size=' + limit;
    return this.http.get<IPageable>(urlPath);
  }

  getById(id:any): Observable<IAppUser> {
    const urlPath = '/api/users/'+id;
    return this.http.get<IAppUser>(urlPath);
  }

  delete(id:any) {
    const urlPath = '/api/users/'+id;
    return this.http.delete<IAppUser>(urlPath);
  }
}
