import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common'; 
import { Router, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'; 
import { catchError, of, tap } from 'rxjs'; // Importamos catchError, of, tap

//Modelo
import { Pedido } from '../modelos/pedido.model'; 
import { Area } from '../modelos/area.model';
import { Estado } from '../modelos/estado.model';
import { PedidoDetalle } from '../modelos/pedido.detalle.model';
import { Articulo } from '../modelos/articulo.model';
import { Usuario } from '../modelos/usuario.model';

//Servicios
import { PedidoServicio } from '../servicios/pedido.service'; 
import { AreaServicio } from '../servicios/area.service';
import { EstadoServicio } from '../servicios/estado.service';
import { Auth } from '../servicios/auth'; 
import { ArticuloServicio } from '../servicios/articulo.service';

@Component({
 selector: 'app-agregar-pedido',
 standalone: true, 
 imports: [FormsModule, CommonModule, RouterModule, HttpClientModule],
 templateUrl: './agregar-pedido.html' 
})
export class AgregarPedido implements OnInit {

 // Inicialización del nuevo pedido con estructura de detalles
    pedido: Pedido = this.inicializarNuevoPedido();
    areas: Area[] = [];
    estados: Estado[] = [];

  // Ahora usará null, que es el tipo de retorno de getUserId()
  idUsuarioAutenticado: number | null = 1;

  nuevoItem: PedidoDetalle = this.inicializarNuevoItem();
  articulos: Articulo[] = []; 
  
  private pedidoServicio = inject(PedidoServicio);
  private areaServicio = inject(AreaServicio);
  private estadoServicio = inject(EstadoServicio);
  private authService = inject(Auth);
private articuloServicio = inject(ArticuloServicio);
  private enrutador = inject(Router);

  ngOnInit(): void {
    // 1. Obtener el ID del usuario A TRAVÉS DEL SERVICIO AUTH
    this.obtenerIdUsuario();
    // 2. Cargar catálogos
    this.cargarAreas();
    this.cargarEstados();
    this.cargarArticulos(); 
  }

inicializarNuevoPedido(): Pedido {
        // Establece la fecha actual
        const today = new Date().toISOString().split('T')[0];
        
        // --- INICIALIZACIÓN COMPLETA DE TODAS LAS PROPIEDADES REQUERIDAS ---
        return {
            id: null,
            fecha_aprobacion: '',
            fecha_solicitud: today,
            id_area: null, 
            id_estado: null, 
            id_usuario: null,
            items: [] as PedidoDetalle[] // Array vacío
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
      idArticulo: null, 
    } as PedidoDetalle;
  }
  
  // =======================================================
  // LÓGICA DE DETALLE DE PEDIDO (CRUD Local)
  // =======================================================

  /**
   * Se dispara cuando el usuario selecciona un artículo del dropdown.
   * Auto-completa la glosa y el precio unitario.
   */
    seleccionarArticulo(): void {
        // CORRECCIÓN 2: Reemplazamos 'this.nuevoItemDetalle.id_articulo' por 'this.nuevoItem.idArticulo'
        if (this.nuevoItem.id_articulo) {
            // 1. Encontrar el artículo seleccionado
            const articuloSeleccionado = this.articulos.find(a => a.id == this.nuevoItem.id_articulo);
            if (articuloSeleccionado) {
                
                // Usar 'nombre' como la 'glosa' del detalle del pedido.
                this.nuevoItem.id_articulo = articuloSeleccionado.id;
                this.nuevoItem.nombreArticulo = articuloSeleccionado.nombre;
                console.log("Artículo encontrado:", articuloSeleccionado);
                
                console.log("Artículo seleccionado. Glosa establecida.");
                
            } else {
                // Si deseleccionan o no se encuentra
                this.limpiarNuevoItem(); // CORRECCIÓN 3: Limpiar usando el nombre correcto
            }

        } else {
            // Si no hay id_articulo seleccionado
            this.limpiarNuevoItem(); // CORRECCIÓN 3: Limpiar usando el nombre correcto
        }
    }

  /**
   * Limpia el objeto 'nuevoItem' y lo reinicializa.
   * Método Auxiliar, asumo que quieres mantenerlo con este nombre, aunque no estaba en tu código original.
   */
  limpiarNuevoItem(): void {
    this.nuevoItem = this.inicializarNuevoItem();
  }
  
  /**
   * Calcula el subtotal del ítem actualmente en el formulario de adición.
   */

  /**
   * Agrega el nuevo ítem a la lista de detalles del pedido.
   */
  agregarItem(): void {
    // 1. Validación básica (ya se maneja parcialmente con el botón disabled en HTML)
    if (this.nuevoItem.cantidad <= 0) {
      // Usamos console.warn en lugar de alert()
      console.warn('Debe especificar Glosa, Cantidad y Precio Unitario válidos para el detalle.');
      return;
    }

    this.pedido.items.push({...this.nuevoItem});
    console.log("Ítem agregado:", this.nuevoItem);
    
    // 4. Reinicializar el formulario de adición para el siguiente ítem
    this.nuevoItem = this.inicializarNuevoItem();
  }

  /**
   * Elimina un ítem de la lista de detalles por su índice.
   * @param index Índice del ítem a eliminar.
   */
  eliminarItem(index: number): void {
    if (this.pedido.items && index >= 0 && index < this.pedido.items.length) {
      this.pedido.items.splice(index, 1);
    }
  }



  obtenerIdUsuario(): void {
    // Obtiene el ID del usuario del servicio Auth
    this.idUsuarioAutenticado = this.authService.getUserId();
    console.log("ID de usuario autenticado obtenido:", this.idUsuarioAutenticado);
    if (this.idUsuarioAutenticado === null || !this.authService.isAuthenticated()) {
        console.error('Error: Usuario no autenticado o ID no encontrado. Redirigiendo a login.');
        this.pedido.id_usuario = 1; // Valor temporal para pruebas
        //this.enrutador.navigate(['/login']);
    } else {
        this.pedido.id_usuario = this.idUsuarioAutenticado;
    }
  }


  onSubmit() {
    this.guardarPedido();
  }

  guardarPedido() {

    console.log("Guardando pedido:", this.pedido);
    
    // Utilizamos la variable ya cargada, que ahora se actualiza cada vez que se carga el componente
    /*if (this.idUsuarioAutenticado === null) {
        console.error("Error: ID de usuario no disponible para guardar el pedido. Posiblemente sesión expirada.");
        //this.enrutador.navigate(['/login']);
        return;
    }*/
    
    // ASIGNACIÓN CORRECTA: Asignamos el ID dinámico antes de enviar
    //this.pedido.usuario.id = this.idUsuarioAutenticado;
    this.pedido.id_usuario = 1;

    this.pedidoServicio.agregarPedido(this.pedido).subscribe({
      next: (datos) => {
        console.log("Pedido guardado exitosamente:", datos);
        this.irListarPedidos();
      },
      error: (error) => {
        console.error("Error al guardar pedido: ", error);
        // Cambiamos el alert por un console.error o un mensaje en el template
        // alert('Error al guardar el pedido. Revisa la consola para más detalles.');
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
        console.log('Áreas cargadas:', datos);
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
}
