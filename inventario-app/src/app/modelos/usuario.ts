export interface Usuario {
  idUsuario: number;
  nombres: string;
  apellidos: string;
  dni: string;
  correo: string;
  telefono: string;
  direccion: string;
  fechaRegistro: string; // O Date
  ultimoAcceso: string; // O Date
  debeCambiarContraseña: boolean;
  rol: 'ADMIN' | 'LOGISTICA' | 'USUARIO';
  area: string;
  estado: string;
}
