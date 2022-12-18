import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/auth/services/login.service';

@Component({
  selector: 'app-blood-donor-page',
  templateUrl: './blood-donor-page.component.html',
  styleUrls: ['./blood-donor-page.component.css']
})
export class BloodDonorPageComponent implements OnInit {

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }


  LogOut(): void {
    this.loginService.logout();
  }

}
