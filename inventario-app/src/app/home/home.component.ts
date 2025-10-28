import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../servicios/api.service';
import { Usuario } from '../modelos/usuario';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  private apiService = inject(ApiService);

  stats: any = null;
  currentUser: Usuario | null = null;
  loading = true;

  ngOnInit() {
    // En una app real, obtendríamos el usuario de un servicio de estado global
    this.apiService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.loadStatistics();
  }

  loadStatistics() {
    this.loading = true;
    this.apiService.getStatistics().subscribe(response => {
      this.stats = response;
      this.loading = false;
    });
    // Mock data por ahora, hasta implementar el servicio
    // this.stats = {
    //   totalUsuarios: 10,
    //   totalProductos: 150,
    //   totalProveedores: 25,
    //   solicitudesPendientes: 5,
    //   productosStockBajo: 12,
    //   movimientosMes: 88,
    //   valorInventario: 125340.50
    // };
    // this.currentUser = { nombres: 'Admin' } as Usuario; // Mock
    // this.loading = false;
  }
}
