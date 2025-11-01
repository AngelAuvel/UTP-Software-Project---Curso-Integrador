import { Departamento } from './departamento';

export enum Rol {
  ADMIN_LOGISTICA = 'ADMIN_LOGISTICA',
  PERSONAL_ALMACEN = 'PERSONAL_ALMACEN',
  JEFE_DEPARTAMENTO = 'JEFE_DEPARTAMENTO',
  EMPLEADO_GENERAL = 'EMPLEADO_GENERAL'
}

export interface Usuario {
  id: number;
  nombre: string;
  apellido: string;
  email: string;
  departamento?: Departamento;
  puesto?: string;
  rol: Rol;
  estado: string;
  fechaRegistro: Date;
  ultimoAcceso?: Date;
}
