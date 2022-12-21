import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import { getMatFormFieldMissingControlError } from '@angular/material/form-field';
import { Router } from '@angular/router';
import { SystemAdminService } from 'src/app/http-services/system-admin.service';

@Component({
  selector: 'app-system-admin-registration',
  templateUrl: './system-admin-registration.component.html',
  styleUrls: ['./system-admin-registration.component.css']
})
export class SystemAdminRegistrationComponent implements OnInit {

  registrationForm : FormGroup = new FormGroup({
    email: new FormControl<string>(''),
  });

  get email(){
    return this.registrationForm.controls['email'].value;
  }
  constructor(private readonly sysAdminService: SystemAdminService, private readonly router: Router) { }

  ngOnInit(): void {
  }


  register = () : void =>
  {
      const predefinedPassword = "password";

      this.sysAdminService.registerSystemAdmin(this.email, predefinedPassword).subscribe({
        next : _ => {
          alert('Password for new system admin :"' + predefinedPassword + '"');
          this.router.navigate(['blood-center-view']);
        },
        error : (response : HttpErrorResponse) =>  {
          if(response.status == 409)
          {
            alert("Email already taken!");
          }
          else if(response.status == 400)
          {
            alert("Bad email format!");
          }
        }
      })
  }

}
