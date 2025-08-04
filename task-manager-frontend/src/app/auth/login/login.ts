import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { LoginUserDTO } from '../../models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  loginData: LoginUserDTO = {
    email: '',
    password: ''
  };
  errorMessage: string | null = null;
  isLoading = false;

  constructor(private apiService: ApiService, private router: Router) { }

  onLogin(): void {
    this.errorMessage = null;
    this.isLoading = true;

    this.apiService.login(this.loginData).subscribe({
      next: (response) => {
        localStorage.setItem('jwt_token', response.token);
        this.isLoading = false;
        this.router.navigate(['/tasks']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Login failed', error);
        this.errorMessage = 'Credenciais inválidas. Por favor, tente novamente.';
      }
    });
  }
}