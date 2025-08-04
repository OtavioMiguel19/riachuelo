import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { TaskRequestDTO, TaskResponseDTO } from '../../models/task.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.scss'
})
export class TaskForm implements OnInit {
  taskData: TaskRequestDTO = {
    title: '',
    description: '',
    dueDate: '',
    status: 'PENDING'
  };
  isEditMode = false;
  taskId: number | null = null;
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
        this.isEditMode = true;
        this.taskId = +id;
        this.getTaskById(this.taskId);
      }
    });
  }

  getTaskById(id: number): void {
    this.isLoading = true;
    this.apiService.getTask(id).subscribe({
      next: (task) => {
        this.taskData = {
          title: task.title,
          description: task.description,
          dueDate: task.dueDate,
          status: task.status
        };
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;
        console.error('Error fetching task', error);
        this.errorMessage = 'Erro ao buscar a tarefa.';
      }
    });
  }

  onSubmit(): void {
    this.errorMessage = null;
    this.isLoading = true;

    if (this.isEditMode && this.taskId) {
      this.apiService.updateTask(this.taskId, this.taskData).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/tasks']);
        },
        error: (error: HttpErrorResponse) => {
          this.isLoading = false;
          console.error('Error updating task', error);
          this.errorMessage = 'Erro ao atualizar a tarefa.';
        }
      });
    } else {
      this.apiService.createTask(this.taskData).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/tasks']);
        },
        error: (error: HttpErrorResponse) => {
          this.isLoading = false;
          console.error('Error creating task', error);
          this.errorMessage = 'Erro ao criar a tarefa.';
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/tasks']);
  }
}
