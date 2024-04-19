import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoginService } from '../../services/auth/login.service';
import { Juego } from '../../services/juego/juego';
import { JuegoService } from '../../services/juego/juego.service';
import { Router } from '@angular/router';
import { JuegoResponse } from '../../services/juego/juegoResponse';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  juegoResponse?: JuegoResponse;
  juegosResponse: JuegoResponse[] = [];
  juegos: Juego[] = [];
  juego?: Juego;
  userLoginOn:boolean=false;
  constructor(private router:Router, private loginService:LoginService, private juegoService:JuegoService) { }

  ngOnInit(): void {
    this.loginService.currentUserLoginOn.subscribe({
      next:(userLoginOn) => {
        this.userLoginOn=userLoginOn;
      }
    });
    this.getJuegos();
  }
  getJuegos(): void {
    this.juegoService.getJuegos().subscribe(
      juegos => {
        this.juegosResponse = juegos;
      },
      error => {
        console.error('Error al obtener la lista de juegos:', error);
      }
    );
  }

  navigateToCreateGame(): void {
    this.router.navigate(['/crear']);
  }
}