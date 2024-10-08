import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../dto/page';
import { User } from '../dto/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8090/user';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl+ '/all');
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`)
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`)
  }
  saveUser(user: any): Observable<any> {
    return this.http.post(this.apiUrl + '/save', user);
  }

  updateUser(id: number, user : any): Observable<any>{
    return this.http.put(`${this.apiUrl}/${id}`, user)
  }

  getAllUsersByPage(page: number = 0, size: number = 5): Observable<Page<User>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<User>>(`${this.apiUrl}/byPage`, { params });
  }

  getAllUsers(searchKey: string, page: number = 0, size: number = 5): Observable<Page<User>> {
    const params = new HttpParams()
      .set('searchKey', searchKey.toString())
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<User>>(`${this.apiUrl}/users`, { params });
  }


}
