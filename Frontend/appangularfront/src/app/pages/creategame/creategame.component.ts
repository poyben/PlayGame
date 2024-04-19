import { Component, OnInit } from '@angular/core';
import { Juego } from '../../services/juego/juego';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JuegoService } from '../../services/juego/juego.service';

@Component({
  selector: 'app-creategame',
  templateUrl: './creategame.component.html',
  styleUrl: './creategame.component.css'
})
export class CreategameComponent implements OnInit {
  juego?: Juego;
  

  createForm = this.formBuilder.group({
    name: ['', Validators.required],
    price: ['', Validators.required],
    stock: ['', Validators.required],
    categoriaDto: ['', Validators.required]
  })

  constructor(private router: Router,private route: ActivatedRoute, private juegoService: JuegoService,private formBuilder:FormBuilder) { }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      const formValue = this.createForm.value;
    const juegoData: Juego = {
      id: 0, // Puedes asignar cualquier valor a id, ya que se generará automáticamente en el servidor
      name: formValue.name || '', // Si formValue.name es null o undefined, asigna una cadena vacía como valor predeterminado
      price: parseFloat(formValue.price || '0'), // Convierte formValue.price a un número usando parseFloat
      stock: parseInt(formValue.stock || '0'), // Convierte formValue.stock a un número entero usando parseInt
      categoriaDto: { name: formValue.categoriaDto || '' }
    };
      this.juegoService.createJuego(juegoData).subscribe(
        juego => {
          console.log('Juego creado exitosamente:', juego);
          // Aquí podrías redirigir al usuario a otra página o mostrar un mensaje de éxito
          this.router.navigate(['/inicio']); // Redirige al usuario a la página de inicio
        },
        error => {
          console.error('Error al crear el juego:', error);
          // Aquí podrías mostrar un mensaje de error al usuario
        }
      );
    }
  }
}