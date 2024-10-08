import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit{
  isLoggedIn: boolean = false;
  token : any

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.signIn()
  }

  signIn():void {
    this.token = localStorage.getItem('jwt')
    if (this.token) {
    this.isLoggedIn = true
    }
    // console.log("JWT: ", this.token);

  }

  logout(): void {
    this.isLoggedIn = false
    localStorage.clear()
    this.authService.logout();
  }

}
