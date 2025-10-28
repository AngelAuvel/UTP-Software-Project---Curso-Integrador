import { PedidoDetalle } from './pedido-detalle';

export type EstadoPedido = 'PENDIENTE' | 'APROBADO' | 'RECHAZADO' | 'ENTREGADO';

export interface Pedido {
  id: number;
  solicitadoPorId: number;
  solicitadoPorNombre: string;
  motivo: string;
  estado: EstadoPedido;
  fechaSolicitud: string; // O Date
  fechaRespuesta: string; // O Date
  respondidoPorId: number;
  respondidoPorNombre: string;
  detalles: PedidoDetalle[];
}
