export interface JuegoResponse {
    id:number,
    name: string,
    price: number,
    stock: number,
    categoria: {
      name: string
    }
  }