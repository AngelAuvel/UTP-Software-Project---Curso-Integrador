import { Component, signal } from '@angular/core';
import { ProductLista } from './product-lista/product-lista';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AgregarProducto } from './agregar-producto/agregar-producto';

@Component({
  selector: 'app-root',
  imports: [ProductLista, AgregarProducto, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('inventario-app');
}
