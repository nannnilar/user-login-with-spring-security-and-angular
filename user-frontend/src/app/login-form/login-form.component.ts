import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent implements OnInit{

  loginForm: FormGroup
  loginResponse : any
  message : any  = ''
  success = ''
  showPassword: boolean = false;

  constructor(private fb: FormBuilder, private userService: UserService,
     private router: Router, private authService: AuthService){
    this.loginForm = this.fb.group({
      loginId: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(4)]],

    })
  }
  ngOnInit(): void {
      this.showMessage(this.message , 10000)
  }
  // login() {
  //   const { loginId, password } = this.loginForm.value;

  //   this.authService.login(this.loginForm.value).subscribe({
  //     next: (val: any) => {
  //       this.loginResponse = val;
  //       console.log("LoginResponse: ", val);

  //       // Check if the entered credentials match the backend data
  //       if (loginId !== this.loginResponse.user.loginId || password !== this.loginResponse.user.password) {
  //         this.message = 'Invalid login ID or password!';
  //         // Optionally clear form fields or perform other actions
  //         this.loginForm.reset();
  //       } else {
  //         // Valid credentials, proceed with login
  //         if (typeof localStorage !== 'undefined') {
  //           localStorage.setItem('jwt', this.loginResponse.jwt);
  //           localStorage.setItem('roles', this.loginResponse.user.roles[0].type);
  //         }
  //         this.router.navigateByUrl('/user-list');
  //       }
  //     },
  //     error: (err) => {
  //       if (err.status === 401) {
  //         // this.message = 'Invalid login ID or password!';
  //         // this.loginForm.reset();
  //       } else {
  //         // this.message = 'An error occurred while logging in. Please try again later.';
  //       }
  //     },
  //   });
  // }

  login() {
    // const { loginId, password } = this.loginForm.value;
    this.authService.login(this.loginForm.value).subscribe({
      next: (val: any) => {
        this.loginResponse = val;
        console.log("LoginResponse:  ", val)
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem('jwt', this.loginResponse.jwt);
          localStorage.setItem('roles', this.loginResponse.user.roles[0].type);
        }
        // alert('Login successful!')
        this.router.navigateByUrl('/user-list');

      } ,error : (err)=>{
        // alert('Invalid login ID or password!!!')
        if (err.status === 401) {
          this.message = 'Invalid login ID or password!!!';
        } else {
          this.message = 'An error occurred while logging in. Please try again later.';
        }
      }
    });
  }
    onSubmit(){
      this.login();
    }

    get passwordFieldType(): string {
      return this.showPassword ? 'text' : 'password';
    }

    togglePasswordVisibility(): void {
      this.showPassword = !this.showPassword;
    }

    showMessage(message: string, duration: number) {
      this.message = message;

      // Set a timeout to clear the message after the specified duration
      setTimeout(() => {
        this.message = null;
      }, duration);
    }


//   onSubmit() {
//   if (this.loginForm.valid) {
//     console.log(this.loginForm.value);

//     this.authService.login(this.loginForm.value).subscribe(
//       (response) => {
//         this.authService.setRoles(response.user.role);
//         this.authService.setToken(response.jwtToken);
//         console.log('Login Successful:', response);
//         this.router.navigate(['/user-list']);
//       },
//       (error) => {
//         console.error('Login error:', error);
//         // this.router.navigate(['/user-list']);
//       }
//     );
//   }
// }

  }
