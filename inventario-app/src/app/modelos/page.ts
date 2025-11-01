export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number; // El número de la página actual
  numberOfElements: number;
  first: boolean;
  last: boolean;
}
