import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { RoleService } from '../services/role.service';
import { UserService } from '../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit{
  isUpdate =  false
  userForm: FormGroup
  roles?: any []
  sub: Subscription
  role: any
  showPassword: boolean = false;
  showComfirmPassword: boolean = false


  constructor(private fb: FormBuilder, private roleService: RoleService,
    private authService: AuthService, private activatedRoute: ActivatedRoute,
     private userService: UserService, private router: Router){
    this.userForm = this.fb.group({
      id : [0],
      loginId: ['', Validators.required],
      password: ['', [Validators.required, Validators.min(4)]],
      comfirmPassword: ['', [Validators.required, Validators.min(4), this.matchPassword('password'),]],
      name: ['', Validators.required],
      email: ['', Validators.email],
      address: ['', Validators.required],
      roleId: ['', Validators.required]
    },{
      // validator: this.passwordsMatchValidator // Add a custom validator for password match
    })
    this.sub = roleService.getRoles().subscribe(
      data => this.roles = data
    )

    this.activatedRoute.params.subscribe((params) => {
      if (params['id']) {
        this.isUpdate = true;
        this.userService.getUserById(params['id']).subscribe(
          (user) => {
            this.userForm.patchValue({
              id: user.id,
              loginId: user.loginId,
              password: user.password,
              comfirmPassword: user.password,
              name: user.name,
              email: user.email,
              address: user.address,
              roleId: user.roleId,
            });
          },
          (error) => {
            console.error('Failed to get user:', error);
          }
        );
      }
    });
  }

  matchPassword(passwordKey: string) {
    return (control: AbstractControl) => {
      const passwordControl = control.root.get(passwordKey);
      const confirmPassword = control.value;

      // Return null if both password and confirm password are empty
      if (!passwordControl || !confirmPassword) {
        return null;
      }

      // Return null if passwords match, otherwise, return the error object
      return passwordControl.value === confirmPassword ? null : { mismatch: true };
    };
  }


  passwordsMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (password && confirmPassword && password.value !== confirmPassword.value) {
      return { 'passwordsNotMatch': true };
    }

    return null;
  }

  getLoginUserRole(){
    this.role = localStorage.getItem('roles')
 }
 get passwordFieldType(): string {
  return this.showPassword ? 'text' : 'password';
}
get comfirmPasswordFieldType(): string {
  return this.showComfirmPassword ? 'text' : 'password';
}

togglePasswordVisibility(): void {
  this.showPassword = !this.showPassword;
}

toggleComfirmPasswordVisibility(): void {
  this.showComfirmPassword = !this.showComfirmPassword;
}


  getSelectedRole($event: Event){
    return ($event.currentTarget as HTMLSelectElement).selectedOptions[0].id
   }
   userId! : number
   ngOnInit(): void {
       this.activatedRoute.params.subscribe(params => {
        if ( params['id']) {
          this.userId = +params['id']
        }
       })
       this.getLoginUserRole()
       this.onSubmit()
   }

  onSubmit(){
    // debugger
    if(this.userForm.valid){
      console.log(this.userForm.value);
      // if (this.isUpdate && this.userForm.value.password === '') {
      //   delete this.userForm.value.password;
      //   delete this.userForm.value.confirmPassword;
      // }
      if(this.isUpdate){
        this.userService.updateUser(this.userId , this.userForm.value).subscribe(
          (response) => {
            console.log('user updated successfully:', response);
            this.router.navigate(['/user-list']);
          },
          (error) => {
            console.error('Error updating user:', error);
            this.router.navigate(['/user-list']);
          }
        )
      } else {
      this.userService.saveUser(this.userForm.value).subscribe(
        (response) => {
          console.log('User created successfully:', response);
          this.router.navigate(['/user-list']);
        },
        (error) => {
          console.error('Error creating user:', error);
          this.router.navigate(['/user-list']);
        }
      );
    }
    }
  }

}
