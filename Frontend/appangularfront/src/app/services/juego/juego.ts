export interface Juego {
    id:number,
    name: string,
    price: number,
    stock: number,
    categoriaDto: {
      name: string
    }
  }