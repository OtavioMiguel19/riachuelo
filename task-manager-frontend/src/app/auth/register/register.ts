import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { RegisterUserDTO } from '../../models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerData: RegisterUserDTO = {
    fullName: '',
    email: '',
    password: ''
  };
  errorMessage: string | null = null;
  isLoading = false;

  constructor(private apiService: ApiService, private router: Router) { }

  onRegister(): void {
    this.errorMessage = null;
    this.isLoading = true;

    this.apiService.register(this.registerData).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Registration failed', error);
        this.errorMessage = 'Erro no registro. Verifique os dados e tente novamente.';
      }
    });
  }
}