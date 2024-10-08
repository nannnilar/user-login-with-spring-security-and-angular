import { UserComponent } from './user/user.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleComponent } from './role/role.component';
import { UserListComponent } from './user-list/user-list.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomeComponent } from './home/home.component';
import { authGuard } from './util/auth.guard';
import { adminGuard } from './util/admin.guard';
import { UserDetailsComponent } from './user-details/user-details.component';

const routes: Routes = [
  {path: '', component: HomeComponent, title: 'Home'},
  {path: 'user-list', component: UserListComponent, title: 'User List', canActivate: [authGuard]},
  {path: 'user', component: UserComponent, title: 'User'},
  {path: 'update/:id', component: UserComponent, title : 'Update', canActivate: [adminGuard]},
  {path: 'role', component: RoleComponent, title: 'Role'},
  {path: 'login-form', component: LoginFormComponent, title: 'Login Form'},
  {path: 'updated/:id', component: UserDetailsComponent, title: 'User Details'},
  {path: '', redirectTo: '' , pathMatch: 'full'},
  // { path: '**', redirectTo: '/login-form' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
