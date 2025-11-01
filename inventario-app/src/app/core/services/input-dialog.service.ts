import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

interface InputDialogState {
  isVisible: boolean;
  title: string;
  message: string;
  resolve: ((value: string | null) => void) | null; // Puede ser null inicialmente
}

@Injectable({
  providedIn: 'root'
})
export class InputDialogService {
  private state = new BehaviorSubject<InputDialogState>({
    isVisible: false,
    title: '',
    message: '',
    resolve: null
  });
  public state$ = this.state.asObservable();

  prompt(title: string, message: string): Observable<string | null> {
    return new Observable(observer => {
      const resolve = (value: string | null) => {
        this.state.next({ isVisible: false, title: '', message: '', resolve: null });
        observer.next(value);
        observer.complete();
      };

      this.state.next({ isVisible: true, title, message, resolve });
    });
  }
}
