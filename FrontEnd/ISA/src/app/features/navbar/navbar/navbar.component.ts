import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  imageSrc = "/ISA//src//assets//images//favicon.ico"

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goTo(path: string) {
    this.router.navigate([path]);
  }
}
