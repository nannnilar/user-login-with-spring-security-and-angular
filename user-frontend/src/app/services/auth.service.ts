import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/auth';
  private token: string | null = null;

  constructor(private http: HttpClient) { }

  public setRoles(roles: []) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): string[] {
    const rolesString = localStorage.getItem('roles');

    if (rolesString !== null) {
      return JSON.parse(rolesString);
    } else {
      return [];
    }
  }


  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string | null{
    return localStorage.getItem('jwtToken');
  }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn() {
    return this.getRoles() && this.getToken();
  }

  register(user: any): Observable<any> {
    return this.http.post(this.apiUrl + '/register', user);
  }
  loginUser(username: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }, { observe: 'response' })
      .pipe(tap(response => {
        // Extract and store the token from the response header
        const authToken = response.headers.get('Authorization');
        if (authToken && authToken.startsWith('Bearer ')) {
          this.token = authToken.substring(7);
        }
      }));
  }


  logout(): void {
  }

  login(user: any): Observable<any> {
    return this.http.post(this.apiUrl + "/login", user)
  }

  getUserByType(type: string): Observable<any> {
    const url = `${this.apiUrl}/type/${type}`;
    return this.http.get(url);
  }


}
