import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../servicios/api.service';
import { Area } from '../modelos/area';
import { AreaFormComponent } from '../area-form/area-form.component';

@Component({
  selector: 'app-area-lista',
  standalone: true,
  imports: [CommonModule, AreaFormComponent],
  templateUrl: './area-lista.component.html',
})
export class AreaListaComponent implements OnInit {
  private apiService = inject(ApiService);

  areas: Area[] = [];
  loading = true;
  showModal = false;
  selectedArea: Partial<Area> | null = null;

  ngOnInit() {
    this.loadAreas();
  }

  loadAreas() {
    this.loading = true;
    this.apiService.getAreas().subscribe(data => {
      this.areas = data;
      this.loading = false;
    });
  }

  openModal(area: Partial<Area> | null = null) {
    this.selectedArea = area ? { ...area } : null;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.selectedArea = null;
  }

  saveArea(area: Partial<Area>) {
    const action = area.id
      ? this.apiService.updateArea(area.id, area)
      : this.apiService.createArea(area);

    action.subscribe(() => {
      this.loadAreas();
      this.closeModal();
    });
  }

  deleteArea(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta área?')) {
      this.apiService.deleteArea(id).subscribe(() => {
        this.loadAreas();
      });
    }
  }
}
