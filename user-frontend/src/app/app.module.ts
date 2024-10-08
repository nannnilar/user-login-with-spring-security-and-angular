import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './user/user.component';
import { RoleComponent } from './role/role.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { UserListComponent } from './user-list/user-list.component';
import { MatTableModule } from '@angular/material/table';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomeComponent } from './home/home.component';
import { TokenInterceptor } from './util/token.interceptor';
import { AuthInterceptor } from './util/auth.interceptor';
import { NavComponent } from './nav/nav.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { FormsModule } from '@angular/forms'; 


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    RoleComponent,
    UserListComponent,
    LoginFormComponent,
    HomeComponent,
    NavComponent,
    UserDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    MatTableModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
