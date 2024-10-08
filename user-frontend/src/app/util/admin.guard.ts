import { CanActivateFn } from '@angular/router';

export const adminGuard: CanActivateFn = (route, state) => {
  // Check if the user is an admin based on your criteria
  // For example, you might have a user role property in your login response
  const isAdmin = localStorage.getItem('jwt') ? true : false; // Replace this with your actual logic

  return isAdmin;
};
