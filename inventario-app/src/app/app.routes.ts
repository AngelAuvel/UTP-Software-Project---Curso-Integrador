import { Routes } from '@angular/router';
import { ProductLista } from './product-lista/product-lista';
import { AgregarProducto } from './agregar-producto/agregar-producto';
import { EditarProducto } from './editar-producto/editar-producto';
import { Login } from './login/login';
import { ListarPedido } from './listar-pedido/listar-pedido';
import { AgregarPedido } from './agregar-pedido/agregar-pedido';
import { EditarPedido } from './editar-pedido/editar-pedido';

export const routes: Routes = [
    {path: 'productos', component: ProductLista},
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: Login},
    {path: 'agregar-productos', component: AgregarProducto},
    {path: 'editar-producto/:id', component: EditarProducto},
    {path: 'pedidos', component: ListarPedido},
    {path: 'agregar-pedidos', component: AgregarPedido},
    {path: 'listar-pedidos', component: ListarPedido},
    {path: 'editar-pedidos/:id', component: EditarPedido}
];
