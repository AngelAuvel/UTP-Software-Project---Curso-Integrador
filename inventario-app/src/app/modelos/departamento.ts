export interface Departamento {
  id: number;
  nombre: string;
  codigo: string;
  descripcion?: string;
  presupuestoMensual?: number;
  jefeDepartamentoId?: number;
  estado: string;
}
