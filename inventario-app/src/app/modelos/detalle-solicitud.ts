import { Producto } from './producto';

export enum EstadoItem {
  PENDIENTE = 'PENDIENTE',
  APROBADO = 'APROBADO',
  DESPACHADO = 'DESPACHADO',
  RECIBIDO = 'RECIBIDO',
  RECHAZADO = 'RECHAZADO'
}

export interface DetalleSolicitud {
  id: number;
  producto: Producto;
  cantidadSolicitada: number;
  cantidadAprobada?: number;
  cantidadDespachada?: number;
  cantidadRecibida?: number;
  especificaciones?: string;
  precioUnitarioEstimado?: number;
  estadoItem: EstadoItem;
}
