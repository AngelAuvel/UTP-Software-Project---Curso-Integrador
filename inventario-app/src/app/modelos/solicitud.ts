import { Departamento } from './departamento';
import { Usuario } from './usuario';
import { DetalleSolicitud } from './detalle-solicitud';

export enum EstadoSolicitud {
  PENDIENTE = 'PENDIENTE',
  EN_REVISION = 'EN_REVISION',
  APROBADA = 'APROBADA',
  DESPACHADA = 'DESPACHADA',
  ENTREGADA = 'ENTREGADA',
  RECHAZADA = 'RECHAZADA',
  CANCELADA = 'CANCELADA'
}

export enum PrioridadSolicitud {
  NORMAL = 'NORMAL',
  URGENTE = 'URGENTE'
}

export enum OrigenSolicitud {
  MANUAL = 'MANUAL',
  LINK_COMPARTIDO = 'LINK_COMPARTIDO',
  OCR_IMAGEN = 'OCR_IMAGEN'
}

export interface Solicitud {
  id: number;
  numeroSolicitud: string;
  departamento: Departamento;
  solicitante: Usuario;
  fechaSolicitud: Date;
  fechaRequerida: Date;
  estado: EstadoSolicitud;
  prioridad: PrioridadSolicitud;
  justificacion?: string;
  aprobador?: Usuario;
  fechaAprobacion?: Date;
  motivoRechazo?: string;
  despachador?: Usuario;
  fechaDespacho?: Date;
  receptor?: Usuario;
  fechaRecepcion?: Date;
  observacionesRecepcion?: string;
  costoTotalEstimado?: number;
  origen: OrigenSolicitud;
  detalles: DetalleSolicitud[];
}
