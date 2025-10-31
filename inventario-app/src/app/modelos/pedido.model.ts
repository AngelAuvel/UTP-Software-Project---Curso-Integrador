import { PedidoDetalle } from './pedido.detalle.model';
import { Area } from './area.model';
import { Estado } from './estado.model';
import { Usuario } from './usuario.model';

/**
 * Modelo Pedido alineado con el DTO de Java para enviar IDs planos.
 */
export class Pedido {
    // Usamos idPedido para coincidir con la convención del DTO de Java
    id?: number | null; 
    
    // Cambios a snake_case y nombres de IDs
    fechaAprobacion?: string | null; 
    fechaSolicitud?: string | null;
    
    // Claves Foráneas (IDs planos)
    id_area: number | null;
    id_estado: number | null;
    id_usuario: number | null;

    area?: Area | null;
    estado?: Estado | null;
    usuario?: Usuario | null;

    items: PedidoDetalle[] = [];

    // Constructor para inicializar los campos
    constructor() {
        this.fechaAprobacion = new Date().toISOString().slice(0, 10); // Inicializa con fecha de hoy
        this.fechaSolicitud = null; // Inicializa como nulo
        this.id_area = null; 
        this.id_estado = 1; // Podría inicializar el estado como "Pendiente" (si 1 es Pendiente)
        this.id_usuario = null;
    }
}
