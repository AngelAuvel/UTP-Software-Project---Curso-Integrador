import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

interface ConfirmationState {
  isVisible: boolean;
  title: string;
  message: string;
  resolve: ((value: boolean) => void) | null; // Puede ser null inicialmente
}

@Injectable({
  providedIn: 'root'
})
export class ConfirmationService {
  private state = new BehaviorSubject<ConfirmationState>({
    isVisible: false,
    title: '',
    message: '',
    resolve: null
  });
  public state$ = this.state.asObservable();

  confirm(title: string, message: string): Observable<boolean> {
    return new Observable(observer => {
      const resolve = (value: boolean) => {
        this.state.next({ isVisible: false, title: '', message: '', resolve: null });
        observer.next(value);
        observer.complete();
      };

      this.state.next({ isVisible: true, title, message, resolve });
    });
  }
}
