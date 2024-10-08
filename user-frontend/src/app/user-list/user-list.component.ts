import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { ActivatedRoute } from '@angular/router';
import { Page } from '../dto/page';
import { User } from '../dto/user';
import { ExcelService } from '../services/excel.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{
  users: User[] = [];
  sub?: Subscription
  admin!: any
  currentPage = 0;
  pageSize = 5
  totalPages = 0;
  searchKey: string = '';
  role: any

  displayedColumns: string[] = ['loginId', 'name', 'email', 'address','roleId', 'action'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userService: UserService, private excelService : ExcelService,
     private authService : AuthService){

    }
    userId! : number
  ngOnInit(): void {
    // this.getUsers()
    this.loadUsers()
    this.getLoginUserRole()

  }
  getLoginUserRole(){
     this.role = localStorage.getItem('roles')
    console.log("Login User Role: ", this.role);

  }

  getUsers() {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      console.log("Users List: ", this.users)
    });

  }
  loadUsers(): void {
    this.userService.getAllUsers(this.searchKey, this.currentPage, this.pageSize)
      .subscribe((usersPage: Page<User>) => {
        this.users = usersPage.content;
        this.totalPages = usersPage.totalPages;
      });
  }
  // loadUsers(): void {
  //   this.userService.getAllUsersByPage(this.currentPage, this.pageSize)
  //     .subscribe((usersPage: Page<User>) => {
  //       this.users = usersPage.content;
  //       this.totalPages = usersPage.totalPages;
  //     });
  // }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadUsers();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadUsers();
    }
  }

  goToPage(pageNumber: number): void {
    if (pageNumber >= 0 && pageNumber < this.totalPages) {
      this.currentPage = pageNumber;
      this.loadUsers();
    }
  }
  getPagesArray(): number[] {
    return Array(this.totalPages).fill(0).map((_, index) => index);
  }


  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(
      () => {
        console.log('User deleted successfully');
      },
      (error) => {
        console.error('Failed to delete user:', error);
      }
    );
  }

  exportToExcel(): void {
    this.excelService.exportUsersToExcel().subscribe((data: Blob) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'users.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    });
  }

  selectedFile: File | undefined;
  onFileChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement?.files?.length) {
      this.selectedFile = inputElement.files[0];
    }
  }

  importFile(): void {
    if (this.selectedFile) {
      this.excelService.importUsersFromExcel(this.selectedFile).subscribe(
        () => {
          console.log('File uploaded successfully');
        },
        error => {
          console.error('Error uploading file', error);
        }
      );
    } else {
      console.error('No file selected');

    }
    // window.location.reload()
  }


}
