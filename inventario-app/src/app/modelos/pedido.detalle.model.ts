import { Articulo } from "./articulo.model";

/**
 * Modelo Detalle de Pedido
 */
export class PedidoDetalle {
    // ID de la fila. Es opcional al crear uno nuevo.
    id?: number | null = undefined; 

    // Campos simples
    cantidad: number = 0;
    glosa: string = '';
    
    // CORRECCIÓN: Ahora acepta 'undefined' para evitar el error de tipado de TS.
    // Esto permite que el campo esté inicialmente vacío sin un valor asignado.
    id_articulo?: number | null = undefined; 

    // NUEVO CAMPO VIEW-ONLY (Transient): 
    // Este campo se usa solo para mostrar el nombre del artículo en la tabla 
    // del frontend, no forma parte del DTO que se serializa y se envía a Spring Boot.
    nombreArticulo?: string | null = null;
}

