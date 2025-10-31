export class Area {
    id?: number | null; 
    nombre!: string;
    constructor(nombre: string, id?: number | null) {
        this.id = id === undefined ? null : id;
        this.nombre = nombre;
    }
}
