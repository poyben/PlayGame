import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../../services/auth/register.service';
import { RegisterRequest } from '../../services/auth/registerRequest';
import { catchError, throwError } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerError: string = "";
  registerForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required]],
    password: ['', Validators.required],
    firstname: ['', Validators.required],
    lastname: ['', Validators.required],
    rol: ['', Validators.required]
  });
  roles: any[] = [];

  constructor(private formBuilder: FormBuilder, private router: Router, private registerService: RegisterService) { }

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.registerService.getRoles().subscribe(
      roles => {
        this.roles = roles;
      },
      error => {
        console.error('Error al cargar los roles:', error);
      }
    );
  }

  get email() {
    return this.registerForm.controls.email;
  }

  get username() {
    return this.registerForm.controls.username;
  }

  get password() {
    return this.registerForm.controls.password;
  }

  get firstname() {
    return this.registerForm.controls.firstname;
  }

  get lastname() {
    return this.registerForm.controls.lastname;
  }

  get rol() {
    return this.registerForm.controls.rol;
  }

  /*
  register() {
    if (this.registerForm.valid) {
      this.registerError = "";
      this.registerService.register(this.registerForm.value as RegisterRequest).subscribe({
        next: (userData) => {
          console.log(userData);
        },
        error: (errorData) => {
          console.error(errorData);
          this.registerError = errorData;
        },
        complete: () => {
          console.info("Registro completo");
          this.router.navigateByUrl('/inicio');
          this.registerForm.reset();
        }
      });
    } else {
      this.registerForm.markAllAsTouched();
      alert("Error al ingresar los datos.");
    }
  }
  */

  register(): void {
    if (this.registerForm.valid) {
      this.registerError = "";
      const { email, username, password, firstname, lastname, rol } = this.registerForm.value;

      // Verificar que rol no sea nulo o indefinido
      if (rol) {
        const registerRequest: RegisterRequest = {
          email: email ||"",
          username: username || '',
          password: password || '',
          firstname: firstname || '',
          lastname: lastname || '',
          rol:rol,
        };

        this.registerService.register(registerRequest)
          .pipe(
            catchError((error) => {
              console.error(error);
              this.registerError = "Error en el registro: " + error.message;
              return throwError(error);
            })
          )
          .subscribe({
            next: (userData) => {
              console.log(userData);
              this.router.navigateByUrl('/inicio');
              this.registerForm.reset();
            },
            complete: () => {
              console.info("Registro completo");
            }
          });
      } else {
        this.registerError = "Error en el registro: El rol es requerido";
      }
    } else {
      this.registerForm.markAllAsTouched();
      alert("Error al ingresar los datos.");
    }
  }


}
