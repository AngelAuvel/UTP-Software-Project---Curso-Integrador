export interface Usuario {
  idUsuario: number;
  correo?: string; // Ahora opcional
  rol?: string;    // Ahora opcional
  fechaRegistro: string; // O Date
  nombres?: string;
  apellidos?: string;
  dni?: string;
  telefono?: string;
  direccion?: string;
  area?: string;
  estado?: string;
}
