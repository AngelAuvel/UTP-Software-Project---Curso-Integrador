import { Routes } from '@angular/router';
import { ProductLista } from './product-lista/product-lista';
import { AgregarProducto } from './agregar-producto/agregar-producto';
import { EditarProducto } from './editar-producto/editar-producto';

export const routes: Routes = [
    {path: 'productos', component: ProductLista},
    {path: '', redirectTo: 'productos', pathMatch: 'full'},
    {path: 'agregar-productos', component: AgregarProducto},
    {path: 'editar-producto/:id', component: EditarProducto}
];
