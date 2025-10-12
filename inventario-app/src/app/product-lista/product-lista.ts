import { Component, inject } from '@angular/core';
import { Producto } from '../modelos/producto.model';
import { ProductoServicio } from '../servicios/producto.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-lista',
  imports: [],
  templateUrl: './product-lista.html'
})
export class ProductLista {
  productos!: Producto[];
  private productoServicio =  inject(ProductoServicio);
  private enrutador = inject(Router);

  ngOnInit() {
    this.obtenerProductos();
  }

  private obtenerProductos() :void{
    this.productoServicio.obtenerProductos().subscribe({
      next: (datos) => {
        this.productos = datos;
      },
      error: (error) => {
        console.error('Error al obtener los productos:', error);
      } 
    });
  }

  editarProducto(id: number){
    this.enrutador.navigate(['editar-producto', id])
  }

  eliminarProducto(id: number){
    this.productoServicio.eliminarProducto(id).subscribe({
      next:(datos)=>this.obtenerProductos(),
      error:(error)=>console.error('Error:', error)
    });
  }
}
