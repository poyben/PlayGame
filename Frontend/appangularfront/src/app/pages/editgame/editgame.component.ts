import { Component, OnInit } from '@angular/core';
import { Juego } from '../../services/juego/juego';
import { LoginService } from '../../services/auth/login.service';
import { JuegoService } from '../../services/juego/juego.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { JuegoResponse } from '../../services/juego/juegoResponse';

@Component({
  selector: 'app-editgame',
  templateUrl: './editgame.component.html',
  styleUrl: './editgame.component.css'
})
export class EditgameComponent implements OnInit {
  juegoResponse?: JuegoResponse;
  juego?: Juego;
  originalCategoria: any;

  editForm = this.formBuilder.group({
    id: [''],
    name: ['', Validators.required],
    price: ['', Validators.required],
    stock: [""],
    categoria: [""]
    
  })



  constructor(private router: Router,private route: ActivatedRoute, private juegoService: JuegoService,private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      if (!isNaN(id)) {
        this.getJuego(id);
      }
    });
  }

  getJuego(id: number): void {
    this.juegoService.getJuegoResponse(id).subscribe(
      juego => {
        this.juegoResponse = juego;
        //this.juego = juego;

        this.editForm.patchValue({ 
          id: id.toString(),
          name: juego.name,
          price: juego.price.toString(),
          stock: juego.stock.toString(),
          //categoriaDto: juego.categoriaDto.name // Asegúrate de asignar el valor de la categoría al formulario
          categoria: juego.categoria.name
        });
      },
      error => {
        console.error('Error al obtener los detalles del juego:', error);
      }
    );
  }


  confirmEdit(): void {
    const id = this.editForm.get('id')?.value;
    const parsedId = id ? parseInt(id, 10) : undefined;
    const price = this.editForm.get('price')?.value;
    const parsedPrice = price ? parseFloat(price) : undefined;
  
    if (parsedId !== undefined) {
      const updatedJuego: Juego = {
        id: parsedId || 0,
        name: this.editForm.get('name')?.value || '',
        price: parsedPrice || 0,
        stock: this.juego?.stock || 0,
        categoriaDto: {
          name: this.editForm.get('categoria')?.value || this.juego?.categoriaDto?.name || '' // Utiliza el valor del formulario si está presente, de lo contrario, utiliza el valor original        }
      }};
  
      this.juegoService.updateJuego(parsedId, updatedJuego).subscribe(
        juego => {
          console.log('Juego actualizado:', juego);
          this.router.navigate(['/inicio']);
        },
        error => {
          console.error('Error al actualizar el juego:', error);
        }
      );
    } else {
      console.error('El id del juego no es un número válido.');
    }
  }

/*
  confirmEdit(): void {
    const id = this.editForm.get('id')?.value;
    const parsedId = id ? parseInt(id, 10) : undefined;
    const price = this.editForm.get('price')?.value;
  const parsedPrice = price ? parseFloat(price) : undefined;


  if (parsedId !== undefined) {
    const updatedJuego: Juego = {
      id: parsedId || 0,
      name: this.editForm.get('name')?.value || '',
      price: parsedPrice || 0,
      stock: this.juegoResponse?.stock || 0,
      categoriaDto: this.originalCategoria // Utiliza la categoría original
    };

    this.juegoService.updateJuego(parsedId, updatedJuego).subscribe(
      juego => {
        console.log('Juego actualizado:', juego);
        this.router.navigate(['/inicio']);
        // Aquí podrías redirigir al usuario a otra página o mostrar un mensaje de éxito
      },
      error => {
        console.error('Error al actualizar el juego:', error);
        // Aquí podrías mostrar un mensaje de error al usuario
      }
    );
  } else {
    console.error('El id del juego no es un número válido.');
  }
  }
*/
  confirmDelete(): void {
    const id = this.editForm.get('id')?.value;
    const parsedId = id ? parseInt(id, 10) : undefined;
    if (confirm('¿Estás seguro de que quieres eliminar este juego?')) {
      this.juegoService.deleteJuego(parsedId||0).subscribe(
        () => {
          console.log('Juego eliminado correctamente');
          this.router.navigate(['/inicio']);
          // Aquí podrías redirigir al usuario a otra página o mostrar un mensaje de éxito
        },
        error => {
          console.error('Error al eliminar el juego:', error);
          // Aquí podrías mostrar un mensaje de error al usuario
        }
      );
    }
  }
}


