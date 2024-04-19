import { Injectable } from '@angular/core';
import { Juego } from './juego';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginService } from '../auth/login.service';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environments';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { JuegoResponse } from './juegoResponse';
@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  juegoResponse?:JuegoResponse;
  juego?:Juego;
  userId: string = '';
  userLoginOn:boolean=false;



  juegoForm=this.formBuilder.group({
    id:[''],
    name:['',Validators.required],
    price:['', Validators.required],
    stock:['',Validators.required],
    categoria:['',Validators.required]
    
  })


  constructor(private formBuilder:FormBuilder, private loginService: LoginService,private http:HttpClient) { }


  
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
              this.userId = decodedToken.username;
              
            }
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


  getJuego(id:number):Observable<Juego>{
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
    return this.http.get<Juego>(environment.urlApi+"/juego/"+id,{headers}).pipe(
      catchError(this.handleError)
    ) 
  }

  getJuegoResponse(id:number):Observable<JuegoResponse>{
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
    return this.http.get<JuegoResponse>(environment.urlApi+"/juego/"+id,{headers}).pipe(
      catchError(this.handleError)
    ) 
  }
    
  getJuegos(): Observable<JuegoResponse[]> {
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
    return this.http.get<JuegoResponse[]>(environment.urlApi + "/juego",{headers}).pipe(
      catchError(this.handleError)
    );
  }

  updateJuego(id: number, juego: Juego): Observable<Juego> {
    const token = this.loginService.userToken;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json' // Especifica el tipo de contenido como JSON
    });
    return this.http.put<Juego>(`${environment.urlApi}/juego/${id}`, juego, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  
  deleteJuego(id: number): Observable<void> {
    const token = this.loginService.userToken;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${environment.urlApi}/juego/${id}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  

  createJuego(juego: Juego): Observable<Juego> {
    const token = this.loginService.userToken;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json' // Especifica el tipo de contenido como JSON
    });
    return this.http.post<Juego>(`${environment.urlApi}/juego`, juego, { headers }).pipe(
      catchError(this.handleError)
    );
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


}
