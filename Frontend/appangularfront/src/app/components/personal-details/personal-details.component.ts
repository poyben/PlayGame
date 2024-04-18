import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '../../services/auth/user';
import { UserService } from '../../services/user/user.service';
import { environment } from '../../../environments/environments';
import { LoginService } from '../../services/auth/login.service';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.css']
})
export class PersonalDetailsComponent {
  errorMessage: String = "";
  user?: User;
  userLoginOn: boolean = false;
  editMode: boolean = false;

  registerForm = this.formBuilder.group({
    id: [''],
    lastname: ['', Validators.required],
    firstname: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    username: ['', Validators.required]
  })

  constructor(private userService: UserService, private formBuilder: FormBuilder, private loginService: LoginService) {
    this.userService.getUser(environment.userId).subscribe({
      next: (userData) => {
        this.user = userData;
        this.registerForm.controls.id.setValue(userData.id.toString());
        this.registerForm.controls.firstname.setValue(userData.firstname);
        this.registerForm.controls.lastname.setValue(userData.lastname);
        this.registerForm.controls.email.setValue(userData.email);
      },
      error: (errorData) => {
        this.errorMessage = errorData
      },
      complete: () => {
        console.info("User Data ok");
      }
    })

    this.loginService.userLoginOn.subscribe({
      next: (userLoginOn) => {
        this.userLoginOn = userLoginOn;
      }
    })
  }

  get firstname() {
    return this.registerForm.controls.firstname;
  }

  get lastname() {
    return this.registerForm.controls.lastname;
  }

  get email() {
    return this.registerForm.controls.email;
  }
  get username() {
    return this.registerForm.controls.username;
  }

  savePersonalDetailsData() {
    if (this.registerForm.valid) {
      this.userService.updateUser(this.registerForm.value as unknown as User).subscribe({
        next: () => {
          this.editMode = false;
          this.user = this.registerForm.value as unknown as User;
        },
        error: (errorData) => console.error(errorData)
      })
    }
  }
}
