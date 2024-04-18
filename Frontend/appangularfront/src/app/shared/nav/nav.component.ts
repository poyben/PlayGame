import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';
import { User } from '../../services/auth/user';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user/user.service';
import { environment } from '../../../environments/environments';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  errorMessage:String="";
username: string = '';
email: string = '';
  user?:User;
  userLoginOn:boolean=false;

  registerForm=this.formBuilder.group({
    id:[''],
    lastname:['',Validators.required],
    firstname:['', Validators.required],
    username:['',Validators.required]
  })

/*
  constructor(private loginService:LoginService,private userService:UserService, private formBuilder:FormBuilder, private router:Router) { 
  this.userService.getUser(this.user?.id).subscribe({
    next: (userData) => {
      this.user=userData;
      this.registerForm.controls.id.setValue(userData.id.toString());
      this.registerForm.controls.firstname.setValue( userData.firstname);
      this.registerForm.controls.lastname.setValue( userData.lastname);
      //this.registerForm.controls.country.setValue( userData.country);
    },
    error: (errorData) => {
      this.errorMessage=errorData
    },
    complete: () => {
      console.info("User Data ok");
    }
  })  
  }
  */


  constructor(
    private loginService: LoginService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

/*
  ngOnInit(): void {
    this.loginService.currentUserLoginOn.subscribe(
      {
        next:(userLoginOn) => {
          this.userLoginOn=userLoginOn;
        }
      }
    )
  }
*/

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
          if (decodedToken && decodedToken.username) {
            // Establece el nombre de usuario en el formulario
            this.username = decodedToken.username;
            
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

  logout()
  {
    this.loginService.logout();
    this.router.navigate(['/login'])
  }


}