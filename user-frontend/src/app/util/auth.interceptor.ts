import {
  HttpErrorResponse,
HttpEvent,
HttpHandler,
HttpInterceptor,
HttpRequest,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
private token: string | null = null;

constructor(private authService: AuthService,
  private router:Router) {}

intercept(
  req: HttpRequest<any>,
  next: HttpHandler
): Observable<HttpEvent<any>> {
  if (req.headers.get('No-Auth') === 'True') {
    return next.handle(req.clone());
  }

  const token: string | null = this.authService.getToken();

  if (token !== null) {
     req = this.addToken(req, token);
    } else {
      console.error("Token is null. Unable to add it to the request.");
    }

  return next.handle(req).pipe(
      catchError(
          (err:HttpErrorResponse) => {
              console.log(err.status);
              if(err.status === 401) {
                  this.router.navigate(['/login-form']);
              } else if(err.status === 403) {
                  this.router.navigate(['/forbidden']);
              }
              return throwError("Something is wrong");
          }
      )
  );
}


private addToken(request:HttpRequest<any>, token:string) {
    return request.clone(
        {
            setHeaders: {
                Authorization : `Bearer ${token}`
            }
        }
    );
}
}
