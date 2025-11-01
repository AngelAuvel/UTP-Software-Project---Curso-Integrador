import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Solicitud } from '../../../../modelos/solicitud';
import { SolicitudService } from '../../../../core/services/solicitud.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-lista-solicitudes',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './lista-solicitudes.component.html',
  styleUrls: ['./lista-solicitudes.component.css']
})
export class ListaSolicitudesComponent implements OnInit {

  private solicitudService = inject(SolicitudService);

  public solicitudes$: Observable<Solicitud[]> | undefined;

  ngOnInit(): void {
    this.solicitudes$ = this.solicitudService.getSolicitudes();
  }

  eliminarSolicitud(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta solicitud?')) {
      this.solicitudService.eliminarSolicitud(id).subscribe(() => {
        // Actualizar la lista después de eliminar
        this.solicitudes$ = this.solicitudService.getSolicitudes();
      });
    }
  }
}
