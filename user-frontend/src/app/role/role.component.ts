import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RoleService } from '../services/role.service';
import { Subscription } from 'rxjs';
import { log } from 'console';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrl: './role.component.css'
})
export class RoleComponent implements OnInit{
  roleForm: FormGroup
  types: any
  sub?: Subscription
  roles: any

  constructor(private fb: FormBuilder, private roleService: RoleService){
    this.roleForm = this.fb.group({
      type: [''],
      name: ['', Validators.required]
    });
    this.sub = roleService.getRoles().subscribe(
      data => this.types = data
    )
  }
  ngOnInit(): void {
    this.getRoles()
  }

  getRoles(){
    this.roleService.getRoles().subscribe(
      data => this.roles = data
    )
    console.log("Roles: ", this.roles);


  }

  getSelectedType($event: Event){
    return ($event.currentTarget as HTMLSelectElement).selectedOptions[0].id
   }

  onSubmit(){
    if(this.roleForm.valid){
      console.log(this.roleForm.value);
      this.roleService.saveRoles(this.roleForm.value).subscribe(
        (response) => {
          console.log('Roles created successfully:', response);
          // this.router.navigate(['/category-list']);
        },
        (error) => {
          console.error('Error creating roles:', error);
        }
      );
    }
  }

}
