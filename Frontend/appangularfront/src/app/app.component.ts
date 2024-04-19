import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  showHeader: boolean = true;
  constructor(private router: Router) {

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.showHeader = !this.isNormalRoute(event.url);
      }
    });

  }

  isNormalRoute(url: string): boolean {
    console.log(url);
    return url === '/inicio' || url === "/crear" || url === '/perfil' ||  /^\/editar\/\d+$/.test(url); 
  }

  shouldShowHeader(): boolean {
    return !this.isNormalRoute(window.location.pathname);
  }
}