export class Usuario {
    id?: number | null; 
    nombre: string;
    clave: string;
    constructor(nombre: string, clave: string, id: number) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
    }
}
