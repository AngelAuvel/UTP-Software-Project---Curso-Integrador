import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { DashboardLayoutComponent } from './layouts/dashboard-layout/dashboard-layout.component';
import { Login } from './login/login';
import { InitialPasswordChangeComponent } from './initial-password-change/initial-password-change.component';
import { HomeComponent } from './home/home.component';
import { PerfilComponent } from './perfil/perfil.component';
import { ProductLista } from './product-lista/product-lista';
import { ProveedorListaComponent } from './proveedor-lista/proveedor-lista.component';
import { CategoriaListaComponent } from './categoria-lista/categoria-lista.component';

export const routes: Routes = [
    // Rutas de autenticación (sin barra lateral)
    {
        path: '',
        component: AuthLayoutComponent,
        children: [
            { path: 'login', component: Login },
            { path: 'initial-password-change', component: InitialPasswordChangeComponent },
            { path: '', redirectTo: 'login', pathMatch: 'full' }
        ]
    },

    // Rutas del dashboard (con barra lateral)
    {
        path: '',
        component: DashboardLayoutComponent,
        children: [
            { path: 'home', component: HomeComponent },
            { path: 'perfil', component: PerfilComponent },
            { path: 'productos', component: ProductLista },
            { path: 'proveedores', component: ProveedorListaComponent },
            { path: 'categorias', component: CategoriaListaComponent },
        ]
    },

    // Redirección por defecto si ninguna ruta coincide
    { path: '**', redirectTo: 'login' }
];
