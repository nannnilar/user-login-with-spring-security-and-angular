import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
    // Check if the application is running in a browser environment
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('jwt');

      if (token) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`,
          },
        });
      }
    }

    return next.handle(request);
  }
}
