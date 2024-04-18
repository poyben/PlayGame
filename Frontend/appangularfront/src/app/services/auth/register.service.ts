import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user';
import { environment } from '../../../environments/environments';
import { RegisterRequest } from './registerRequest';
import { LoginService } from './login.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  currentUserData: BehaviorSubject<String> =new BehaviorSubject<String>("");

  constructor(private http: HttpClient, private loginService: LoginService,private router: Router) { 
    this.currentUserLoginOn=new BehaviorSubject<boolean>(sessionStorage.getItem("token")!=null);
    this.currentUserData=new BehaviorSubject<String>(sessionStorage.getItem("token") || "");
  }

  getRoles(): Observable<any[]> {
    return this.http.get<any[]>(environment.urlApi + '/rol');
  }

  register(registerRequest: RegisterRequest): Observable<any> {
    return this.http.post<any>(environment.urlHost + 'auth/register', registerRequest).pipe(
      tap(() => {
        // After successful registration, login the user using the same credentials
        this.loginService.login({
          username: registerRequest.username,
          password: registerRequest.password
        }).subscribe({
          next: () => {
            console.log('Registro exitoso y sesión iniciada automáticamente.');
            this.router.navigate(['/inicio']);
          },error: (error) => {
            console.error('Error during automatic login after registration:', error);
          }
        });
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error ', error.error);
    } else {
      console.error('Backend retornó el código de estado ', error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }
}
