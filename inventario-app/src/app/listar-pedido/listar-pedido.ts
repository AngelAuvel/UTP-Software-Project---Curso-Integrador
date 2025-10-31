import { Component, inject, OnInit } from '@angular/core';
import { Pedido } from '../modelos/pedido.model';         // 1. Importa el modelo Pedido
import { PedidoServicio } from '../servicios/pedido.service'; // 2. Importa el servicio Pedido
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common'; // Necesario para *ngFor en el template

@Component({
  selector: 'app-listar-pedido',
  standalone: true, // Componente Standalone
  imports: [CommonModule, RouterLink], // Importa módulos necesarios
  templateUrl: './listar-pedido.html', // Template HTML asociado
})
export class ListarPedido implements OnInit {
  
  pedidos!: Pedido[]; // 3. Array para almacenar la lista de pedidos
  
  // 4. Inyección de dependencias (Servicio y Router)
  private pedidoServicio = inject(PedidoServicio);
  private enrutador = inject(Router);

  ngOnInit() {
    this.obtenerPedidos(); // 5. Llama a obtenerPedidos al inicializar
  }

  /**
   * Carga la lista de pedidos desde el servicio.
   */
  private obtenerPedidos(): void {
    this.pedidoServicio.obtenerPedidos().subscribe({
      next: (datos) => {
        this.pedidos = datos;
      },
      error: (error) => {
        console.error('Error al obtener los pedidos:', error);
      }
    });
  }

  /**
   * Navega a la ruta de edición para un pedido específico.
   * @param id El ID del pedido a editar.
   */
  editarPedido(id: number) {
    // 6. Navega a la ruta de edición (ej. /editar-pedido/1)
    this.enrutador.navigate(['editar-pedidos', id]);
  }

  /**
   * Llama al servicio para eliminar un pedido y refresca la lista.
   * @param id El ID del pedido a eliminar.
   */
  eliminarPedido(id: number) {
    this.pedidoServicio.eliminarPedido(id).subscribe({
      next: (datos) => {
        // 7. Vuelve a cargar la lista de pedidos después de eliminar
        this.obtenerPedidos(); 
      },
      error: (error) => console.error('Error al eliminar pedido:', error)
    });
  }
}

