import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Producto } from '../modelos/producto.model';
import { ProductoServicio } from '../servicios/producto.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-agregar-producto',
  imports: [FormsModule],
  templateUrl: './agregar-producto.html'  
})
export class AgregarProducto {
  producto: Producto = new Producto();
  private productoServicio = inject(ProductoServicio);
  private enrutador = inject(Router);
  
  onSubmit(){
    this.guardarProducto();
  }
  guardarProducto(){
    this.productoServicio.agregarProducto(this.producto).subscribe({
      next: (datos) => this.irListarProductos(),
      error: (error) => console.log("Error: ", error)
    });
  }
  irListarProductos(){
    this.enrutador.navigate(['/productos']);
  }
}
