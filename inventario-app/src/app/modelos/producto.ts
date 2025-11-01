export interface Categoria {
  id: number;
  nombre: string;
  descripcion?: string;
  colorIdentificador?: string;
  icono?: string;
}

export enum UnidadMedida {
  UNIDAD = 'UNIDAD',
  CAJA = 'CAJA',
  PAQUETE = 'PAQUETE',
  RESMA = 'RESMA',
  LITRO = 'LITRO',
  KILO = 'KILO',
  METRO = 'METRO'
}

export interface Producto {
  id: number;
  codigo: string;
  nombre: string;
  descripcion?: string;
  categoria: Categoria;
  unidadMedida: UnidadMedida;
  precioUnitario: number;
  stockMinimo: number;
  stockMaximo: number;
  imagen?: string;
  estado: string;
}
