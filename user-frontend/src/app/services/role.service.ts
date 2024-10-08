import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private apiUrl = 'http://localhost:8090/roles';

  constructor(private http: HttpClient) { }

  saveRoles(role: any): Observable<any> {
    return this.http.post(this.apiUrl + '/save', role);
  }

  getRoles(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl+ '/all');
  }

}
