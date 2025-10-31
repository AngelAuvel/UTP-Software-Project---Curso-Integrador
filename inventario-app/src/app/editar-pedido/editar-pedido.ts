import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common'; 
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'; 
import { catchError, of, tap } from 'rxjs';

// Modelos (Asegúrate de que tus modelos están actualizados para incluir 'area' y 'estado' como objetos)
import { Pedido } from '../modelos/pedido.model'; 
import { Area } from '../modelos/area.model';
import { Estado } from '../modelos/estado.model';
import { PedidoDetalle } from '../modelos/pedido.detalle.model';
import { Articulo } from '../modelos/articulo.model';

// Servicios
import { PedidoServicio } from '../servicios/pedido.service'; 
import { AreaServicio } from '../servicios/area.service';
import { EstadoServicio } from '../servicios/estado.service';
import { Auth } from '../servicios/auth'; 
import { ArticuloServicio } from '../servicios/articulo.service';

@Component({
  selector: 'app-editar-pedido',
  standalone: true, 
  imports: [FormsModule, CommonModule, RouterModule, HttpClientModule],
  // Asegúrate que la ruta al template sea correcta si no usas './'
  templateUrl: './editar-pedido.html' 
})
export class EditarPedido implements OnInit {

  // Asegúrate de que Pedido() inicialice el array 'items' como []
  pedido: Pedido = this.inicializarNuevoPedido();
  areas: Area[] = [];
  estados: Estado[] = [];
  
  idPedidoAEditar: number | undefined; 
  idUsuarioAutenticado: number | null = 1;

  nuevoItem: PedidoDetalle = this.inicializarNuevoItem();
  articulos: Articulo[] = []; 
  
  // Inyección de servicios
  private pedidoServicio = inject(PedidoServicio);
  private areaServicio = inject(AreaServicio);
  private estadoServicio = inject(EstadoServicio);
  private enrutador = inject(Router);
  private rutaActiva = inject(ActivatedRoute); 
  private articuloServicio = inject(ArticuloServicio);
  private authService = inject(Auth); 


  ngOnInit(): void {
    // 1. Obtener el ID del pedido desde la URL (ruta)
    this.rutaActiva.params.subscribe(params => {
      const id = params['id']; 
      if (id) {
        this.idPedidoAEditar = Number(id);
        console.log(`Editando pedido con ID: ${this.idPedidoAEditar}`);
        this.cargarPedido(this.idPedidoAEditar);
      } else {
        console.error("No se encontró el ID del pedido en la URL. Volviendo a la lista.");
        this.enrutador.navigate(['/listar-pedidos']);
      }
    });

    // 2. Cargar catálogos (áreas y estados)
    this.cargarAreas();
    this.cargarEstados();
    this.cargarArticulos();
  }

  inicializarNuevoPedido(): Pedido {
    // Establece la fecha actual
    const today = new Date().toISOString().split('T')[0];
    
    // --- INICIALIZACIÓN COMPLETA DE TODAS LAS PROPIEDADES REQUERIDAS ---
    // Usamos esta inicialización para evitar errores al acceder a propiedades anidadas o arrays.
    return {
      id: null,
      fechaAprobacion: '', // Corregido: Usar camelCase según la API
      fechaSolicitud: today, // Corregido: Usar camelCase según la API
      id_area: null, 
      id_estado: null, 
      id_usuario: null,
      area: null, // Debe existir para evitar errores en la lógica de precarga
      estado: null, // Debe existir para evitar errores en la lógica de precarga
      usuario: null,
      items: [] as PedidoDetalle[] // Array vacío, CRUCIAL para que el @for no falle
    } as Pedido; // La aserción final es obligatoria para el objeto literal.
  }

  /**
   * Inicializa un objeto PedidoDetalle vacío para el formulario de adición.
   */
  inicializarNuevoItem(): PedidoDetalle {
    return {
      glosa: '',
      nombreArticulo: '',
      cantidad: 1,
      id_articulo: null, // Usar la propiedad del modelo PedidoDetalle que se usa en el template
    } as PedidoDetalle;
  }
  
  /**
  * Carga los datos del pedido y realiza la conversión de tipo necesaria
  * para la preselección de los campos select.
  */
  cargarPedido(id: number): void {
    this.pedidoServicio.obtenerPedidoPorId(id).subscribe({
      next: (datos: Pedido) => {
        // Asignar los datos al modelo
        this.pedido = datos;
        console.log("Datos del pedido recibidos:", this.pedido);

        // 1. Área
        if (this.pedido.area?.id) {
            this.pedido.id_area = this.pedido.area.id;
        } else {
             this.pedido.id_area = null;
        }

        // 2. Estado
        if (this.pedido.estado?.id) {
            this.pedido.id_estado = this.pedido.estado.id;
        } else {
             this.pedido.id_estado = null;
        }

        // 3. Detalle (Items): Si la propiedad 'items' no viene o es null/undefined, 
        // aseguramos que sea un array vacío.
        console.log("Detalle de items antes de la verificación:", this.pedido.items);
        if (!this.pedido.items) {
             this.pedido.items = [];
        }

        // Formatear las fechas para inputs tipo 'date' (usando camelCase)
        if (this.pedido.fechaSolicitud) {
          this.pedido.fechaSolicitud = this.formatearFecha(this.pedido.fechaSolicitud);
        }
        if (this.pedido.fechaAprobacion) {
          this.pedido.fechaAprobacion = this.formatearFecha(this.pedido.fechaAprobacion);
        }
        
        console.log("Pedido cargado y IDs mapeados para edición:", this.pedido);
      },
      error: (error) => {
        console.error(`Error al cargar el pedido con ID ${id}:`, error);
        this.enrutador.navigate(['/listar-pedidos']);
      }
    });
  }

  /**
    * Función de utilidad para formatear la fecha a 'YYYY-MM-DD',
    * necesario para los inputs tipo 'date'.
    */
  formatearFecha(fecha: string | Date): string {
    const dateObj = new Date(fecha); 
    // Usar métodos getUTCFullYear, getUTCMonth, getUTCDate para consistencia
    const year = dateObj.getUTCFullYear();
    const month = String(dateObj.getUTCMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getUTCDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }


  onSubmit() {
    this.actualizarPedido();
  }

  /**
    * Envía los datos modificados al servicio para su actualización (PUT).
    */
  actualizarPedido() {
    console.log("Actualizando pedido:", this.pedido);

    if (this.idPedidoAEditar === undefined) {
      console.error("No se puede actualizar: ID del pedido es indefinido.");
      return;
    }

    // Antes de enviar, limpiamos los objetos 'area' y 'estado' si tu API solo espera los IDs
    // Opcional, dependiendo de si la API acepta los objetos anidados o no.
    // Si tu API falla al enviar 'area' y 'estado', descomenta lo siguiente:
    /*
    const pedidoParaEnviar = { 
        ...this.pedido,
        area: undefined,
        estado: undefined,
        usuario: undefined 
    };
    */


    this.pedidoServicio.editarPedido(this.idPedidoAEditar, this.pedido).subscribe({
      next: (datos) => {
        console.log("Pedido actualizado exitosamente:", datos);
        this.irListarPedidos();
      },
      error: (error) => {
        console.error("Error al actualizar pedido: ", error);
      }
    });
  }


  irListarPedidos() {
    this.enrutador.navigate(['/listar-pedidos']); 
  }

  /**
    * Llama al servicio de Area para obtener la lista de áreas
    */
  cargarAreas() {
    this.areaServicio.listarArea().subscribe({ 
      next: (datos) => {
        this.areas = datos;
      },
      error: (error) => console.error('Error al cargar áreas:', error)
    });
  }

  /**
    * Llama al servicio de Estado para obtener la lista de estados
    */
  cargarEstados() {
    this.estadoServicio.listarEstado().subscribe({ 
      next: (datos) => {
        this.estados = datos; 
      },
      error: (error) => console.error('Error al cargar estados:', error)
    });
  }

  cargarArticulos(): void {
    this.articuloServicio.listarArticulos().subscribe({
      next: (datos) => {
        this.articulos = datos;
      },
      error: (error) => console.error('Error al cargar artículos:', error)
    });
  }

  seleccionarArticulo(): void {
    if (this.nuevoItem.id_articulo) {
      const articuloSeleccionado = this.articulos.find(a => a.id == this.nuevoItem.id_articulo);
      if (articuloSeleccionado) {
        // Asignamos el ID al nuevoItem (ya hecho)
        this.nuevoItem.id_articulo = articuloSeleccionado.id; 
        // Asignamos el nombre del artículo para mostrarlo en la tabla
        this.nuevoItem.nombreArticulo = articuloSeleccionado.nombre; 
        // Opcional: Asignamos el nombre como glosa inicial si el usuario no pone una
        if (!this.nuevoItem.glosa) {
             this.nuevoItem.glosa = articuloSeleccionado.nombre;
        }
        console.log("Artículo encontrado y datos listos:", this.nuevoItem);
      } else {
        // Limpiar si no se encuentra (debería ser raro si los datos son correctos)
        this.limpiarNuevoItem(); 
      }
    } else {
      // Si se deselecciona
      this.limpiarNuevoItem(); 
    }
  }

  limpiarNuevoItem(): void {
    this.nuevoItem = this.inicializarNuevoItem();
  }
  
  /**
    * Agrega el nuevo ítem a la lista de detalles del pedido.
    */
  agregarItem(): void {
    if (this.nuevoItem.cantidad <= 0 || !this.nuevoItem.id_articulo) {
      console.warn('Debe seleccionar un Artículo y especificar una Cantidad válida.');
      return;
    }

    // 1. Aseguramos que 'items' esté inicializado como array.
    if (!this.pedido.items) {
        this.pedido.items = [];
    }
    
    // 2. Usamos el spread operator para agregar una copia del objeto y evitar referencias
    this.pedido.items.push({...this.nuevoItem});
    console.log("Ítem agregado. Detalle actual:", this.pedido.items);
    
    // 3. Reinicializar el formulario de adición para el siguiente ítem
    this.nuevoItem = this.inicializarNuevoItem();
  }

  /**
    * Elimina un ítem de la lista de detalles por su índice.
    * @param index Índice del ítem a eliminar.
    */
  eliminarItem(index: number): void {
    if (this.pedido.items && index >= 0 && index < this.pedido.items.length) {
      this.pedido.items.splice(index, 1);
      console.log(`Ítem en posición ${index} eliminado.`);
    }
  }
}
