import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, of, retry, throwError } from 'rxjs';
import { User } from '../auth/user';
import { environment } from '../../../environments/environments';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../auth/login.service';
import e from 'cors';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  
  errorMessage:String="";
  user?:User;
  userLoginOn:boolean=false;
  id: string = '';
  rol:string='';

  private jwtHelper: JwtHelperService = new JwtHelperService();
  private rolesSubject: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);
  roles$ = this.rolesSubject.asObservable();

  registerForm=this.formBuilder.group({
    id:[''],
    lastname:['',Validators.required],
    firstname:['', Validators.required],
    username:['',Validators.required],
    email: ['', [Validators.required, Validators.email]]
  })



  constructor( private loginService:LoginService,
    private formBuilder:FormBuilder, 
    private router:Router,
    private http:HttpClient,) {
    /*this.jwtHelper = new JwtHelperService();*/

    const token = sessionStorage.getItem('token'); // Suponiendo que el token está almacenado en el localStorage
    if (token) {
      this.extractRolesFromToken(token);
    }
  }
/*
  private extractRolesFromToken(token: string): void {
    const decodedToken = this.jwtHelper.decodeToken(token);
    const roles = decodedToken?.roles || [];
    this.rolesSubject.next(roles);
  }
*/


private extractRolesFromToken(token: string): void {
  const decodedToken = this.jwtHelper.decodeToken(token);
  const roles = decodedToken?.roles || [];
  if (roles.length > 0) {
    // Obtener solo el primer rol del array de roles
    const primerRol = roles[0];
    // Actualizar el BehaviorSubject con el primer rol
    this.rolesSubject.next([primerRol]);
  } else {
    // Si no hay roles en el token, dejar el BehaviorSubject vacío
    this.rolesSubject.next([]);
  }
}


    
    ngOnInit(): void {
      this.loginService.currentUserLoginOn.subscribe({
        next:(userLoginOn) => {
          this.userLoginOn=userLoginOn;
          if (userLoginOn) {
            // Si el usuario ha iniciado sesión, obtén el nombre de usuario del token
            const token = this.loginService.userToken;
            if (token) {
              // Decodifica el token para obtener los datos del usuario
              const decodedToken = this.decodeToken(token);
              console.log("decodedToken:"+decodedToken);
              if (decodedToken && decodedToken.id) {
                // Establece el nombre de usuario en el formulario
                this.id = decodedToken.username;
                this.rol = decodedToken.rol;
                
              }
              
              console.log("rol tOKEN:aaaaaa");
              console.log("rol tOKEN:"+decodedToken.rol);
              console.log("username tOKEN:"+decodedToken.username);
              
            }
          }
        }
      });
    }
    

    private decodeToken(token: string): any {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload;
      } catch (error) {
        console.error('Error al decodificar el token:', error);
        return null;
      }
    }
    

  getUser(id:number):Observable<User>{
    return this.http.get<User>(environment.urlApi+"/user/"+id).pipe(
      catchError(this.handleError)
    ) 
  }

  updateUser(userRequest:User):Observable<any>
  {
    const token = this.loginService.userToken;
    console.log("Token de autorización:", token);
    if (!token) {
      console.error("Token de autorización no disponible. La solicitud no se enviará.");
      // Si no hay token disponible, lanzar un error
      return throwError(() => new Error('Token de autorización no disponible.'));
    }

    // Construir el encabezado con el token de autorización
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    console.log("UserRequest:"+userRequest.id.toString());
    return this.http.put(environment.urlApi+"/user/"+userRequest.id, userRequest,{headers}).pipe(
      catchError(this.handleError)
    )
  }

  private handleError(error:HttpErrorResponse){
    if(error.status===0){
      console.error('Se ha producio un error ', error.error);
    }
    else{
      console.error('Backend retornó el código de estado ', error.status, error.error);
    }
    return throwError(()=> new Error('Algo falló. Por favor intente nuevamente.'));
  }

  getRol(): string[] {
    return this.rolesSubject.value;
  }
}