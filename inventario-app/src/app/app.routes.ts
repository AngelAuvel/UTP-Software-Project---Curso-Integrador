import { Routes } from '@angular/router';
import { ProductLista } from './product-lista/product-lista';
import { AgregarProducto } from './agregar-producto/agregar-producto';
import { EditarProducto } from './editar-producto/editar-producto';
import { Login } from './login/login';

export const routes: Routes = [
    {path: 'productos', component: ProductLista},
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'agregar-productos', component: AgregarProducto},
    {path: 'editar-producto/:id', component: EditarProducto}
];
