export interface Producto {
  id: number;
  nombre: string;
  categoriaId: number | null;
  categoriaNombre: string | null;
  cantidadStock: number;
  stockMinimo: number;
  precio: number;
  precioTotal?: number;
  codigo: string;
  proveedorId: number | null;
  proveedorNombre: string | null;
  descripcion: string;
  estado: string;
  fechaRegistro: string; // O Date
}
