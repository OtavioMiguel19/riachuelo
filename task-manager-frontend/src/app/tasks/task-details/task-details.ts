import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { TaskResponseDTO } from '../../models/task.model';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-details.html',
  styleUrl: './task-details.scss'
})
export class TaskDetails implements OnInit {
  task: TaskResponseDTO | null = null;
  errorMessage: string | null = null;
  isLoading = false;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.getTaskById(+id);
      }
    });
  }

  getTaskById(id: number): void {
    this.isLoading = true;
    this.apiService.getTask(id).subscribe({
      next: (task) => {
        this.task = task;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;
        console.error('Erro ao buscar tarefa', error);
        this.errorMessage = 'Erro ao buscar os detalhes da tarefa.';
        if (error.status === 404) {
          this.errorMessage = 'Tarefa não encontrada.';
        }
      }
    });
  }

  onEditTask(): void {
    if (this.task) {
      this.router.navigate(['/tasks/edit', this.task.id]);
    }
  }

  onDeleteTask(): void {
    if (this.task && confirm(`Tem certeza de que deseja excluir a tarefa "${this.task.title}"?`)) {
      this.apiService.deleteTask(this.task.id).subscribe({
        next: () => {
          this.router.navigate(['/tasks']);
        },
        error: (error) => {
          console.error('Erro ao excluir a tarefa', error);
          this.errorMessage = 'Erro ao excluir a tarefa. Tente novamente.';
        }
      });
    }
  }

  onBackToList(): void {
    this.router.navigate(['/tasks']);
  }
}
