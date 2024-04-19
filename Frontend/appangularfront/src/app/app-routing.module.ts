import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { EditgameComponent } from './pages/editgame/editgame.component';
import { CreategameComponent } from './pages/creategame/creategame.component';

const routes: Routes = [

  {path:"", redirectTo:"login", pathMatch:"full"},
  {path:"inicio", component:DashboardComponent},
  {path:"login", component:LoginComponent},
  {path:"registro", component:RegisterComponent},
  {path:"perfil", component:ProfileComponent},
  {path:"editar/:id", component:EditgameComponent},
  {path:"crear", component:CreategameComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
