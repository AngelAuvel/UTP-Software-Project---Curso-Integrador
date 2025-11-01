import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardService } from '../../../../core/services/dashboard.service';
import { DashboardKpis } from '../../../../modelos/dashboard-kpis';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private dashboardService = inject(DashboardService);

  public kpis$: Observable<DashboardKpis> | undefined;

  ngOnInit(): void {
    this.kpis$ = this.dashboardService.getKpis();
  }
}
