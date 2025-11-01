import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { ModalService } from '../../../../core/services/modal.service';
import { PasswordChangeComponent } from '../../../../shared/components/password-change/password-change.component';
import { Usuario } from '../../../../modelos/usuario';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  private usuarioService = inject(UsuarioService);
  private modalService = inject(ModalService);

  public usuario$: Observable<Usuario> | undefined;

  ngOnInit(): void {
    this.usuario$ = this.usuarioService.getPerfilUsuario();
  }

  openPasswordChangeModal(): void {
    this.modalService.open(PasswordChangeComponent);
  }
}
