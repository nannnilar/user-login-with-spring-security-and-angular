import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import * as jwt from 'jsonwebtoken';

export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem('jwt');
  const roles = localStorage.getItem('roles')
  const router = inject(Router);

  if (token) {
    return true;
  }else{
    router.navigate(['/login-form']);
      return false;
  }


}


