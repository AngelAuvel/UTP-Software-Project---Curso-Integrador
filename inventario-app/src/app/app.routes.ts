import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { DashboardComponent } from './features/dashboard/components/dashboard/dashboard.component';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { ListaProductosComponent } from './features/productos/components/lista-productos/lista-productos.component';
import { ProductoFormComponent } from './features/productos/components/producto-form/producto-form.component';
import { LayoutComponent } from './shared/components/layout/layout.component';
import { ListaSolicitudesComponent } from './features/solicitudes/components/lista-solicitudes/lista-solicitudes.component';
import { SolicitudFormComponent } from './features/solicitudes/components/solicitud-form/solicitud-form.component';
import { ListaUsuariosComponent } from './features/usuarios/components/lista-usuarios/lista-usuarios.component';
import { UsuarioFormComponent } from './features/usuarios/components/usuario-form/usuario-form.component';
import { UserProfileComponent } from './features/usuarios/components/user-profile/user-profile.component';
import { Rol } from './modelos/usuario';

export const routes: Routes = [
  // Rutas públicas (sin layout)
  { path: 'login', component: LoginComponent },

  // Rutas privadas (con layout)
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard], // Protege todas las rutas hijas
    children: [
      { path: 'dashboard', component: DashboardComponent },
      {
        path: 'productos',
        component: ListaProductosComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA, Rol.PERSONAL_ALMACEN] }
      },
      {
        path: 'productos/nuevo',
        component: ProductoFormComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA] }
      },
      {
        path: 'productos/editar/:id',
        component: ProductoFormComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA] }
      },
      {
        path: 'solicitudes',
        component: ListaSolicitudesComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA, Rol.PERSONAL_ALMACEN, Rol.JEFE_DEPARTAMENTO, Rol.EMPLEADO_GENERAL] }
      },
      {
        path: 'solicitudes/nuevo',
        component: SolicitudFormComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.JEFE_DEPARTAMENTO, Rol.EMPLEADO_GENERAL] }
      },
      {
        path: 'solicitudes/:id',
        component: SolicitudFormComponent, // Para ver detalles
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA, Rol.PERSONAL_ALMACEN, Rol.JEFE_DEPARTAMENTO, Rol.EMPLEADO_GENERAL] }
      },
      {
        path: 'usuarios',
        component: ListaUsuariosComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA] }
      },
      {
        path: 'usuarios/nuevo',
        component: UsuarioFormComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA] }
      },
      {
        path: 'usuarios/editar/:id',
        component: UsuarioFormComponent,
        canActivate: [roleGuard],
        data: { roles: [Rol.ADMIN_LOGISTICA] }
      },
      {
        path: 'perfil',
        component: UserProfileComponent,
        canActivate: [authGuard] // Cualquier usuario autenticado puede ver su perfil
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'login' }
];
