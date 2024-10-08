import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {
  private apiUrl = 'http://localhost:8090/excel';

  constructor(private http: HttpClient) { }

  exportUsersToExcel(): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    return this.http.get(this.apiUrl + '/export/users', {
      responseType: 'blob',
      headers: headers
    });
  }

  importUsersFromExcel(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    const headers = new HttpHeaders();

    return this.http.post(`${this.apiUrl}/import/users`, formData, { headers, responseType: 'text' }).pipe(
      catchError((error) => {
        console.error('Error uploading file', error);
        return throwError('Import failed', error.message);
      })
    );
  }
}
