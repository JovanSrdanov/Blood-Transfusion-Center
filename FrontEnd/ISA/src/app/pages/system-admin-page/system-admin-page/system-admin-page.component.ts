import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-system-admin-page',
  templateUrl: './system-admin-page.component.html',
  styleUrls: ['./system-admin-page.component.css']
})
export class SystemAdminPageComponent implements OnInit {

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  LogOut(): void {
    this.loginService.logout();
  }

}
