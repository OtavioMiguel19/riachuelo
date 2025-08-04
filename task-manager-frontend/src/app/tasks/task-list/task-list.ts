import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { TaskResponseDTO } from '../../models/task.model';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './task-list.html',
  styleUrl: './task-list.scss'
})
export class TaskList implements OnInit {
  tasks: TaskResponseDTO[] = [];
  filteredTasks: TaskResponseDTO[] = [];
  errorMessage: string | null = null;
  isLoading = false;

  filterByStatus: string = '';
  filterByCreationDate: string = '';
  filterByDueDate: string = '';

  constructor(private apiService: ApiService, private router: Router) { }

  ngOnInit(): void {
    this.checkAuthentication();
    this.getAllTasks();
  }

  checkAuthentication(): void {
    if (!localStorage.getItem('jwt_token')) {
      this.router.navigate(['/login']);
    }
  }

  getAllTasks(): void {
    this.isLoading = true;
    this.apiService.getAllTasks().subscribe({
      next: (data) => {
        this.tasks = data;
        this.filteredTasks = data;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.isLoading = false;
        console.error('Error fetching tasks', error);
        this.errorMessage = 'Erro ao buscar tarefas. Por favor, tente novamente.';
        if (error.status === 401 || error.status === 403) {
          this.router.navigate(['/login']);
        }
      }
    });
  }

  onLogout(): void {
    localStorage.removeItem('jwt_token');
    this.router.navigate(['/login']);
  }
  
  onAddNewTask(): void {
    this.router.navigate(['/tasks/new']);
  }

  onEditTask(id: number): void {
    this.router.navigate(['/tasks/edit', id]);
  }

  onDeleteTask(id: number): void {
    if (confirm('Tem certeza de que deseja excluir esta tarefa?')) {
      this.apiService.deleteTask(id).subscribe({
        next: () => {
          this.getAllTasks();
        },
        error: (error) => {
          console.error('Error deleting task', error);
          this.errorMessage = 'Erro ao excluir a tarefa. Tente novamente.';
        }
      });
    }
  }
  
  onToggleTaskStatus(task: TaskResponseDTO): void {
    const newStatus: 'PENDING' | 'COMPLETED' = task.status === 'COMPLETED' ? 'PENDING' : 'COMPLETED';
    
    const updatedTask = {
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
      status: newStatus
    };

    this.apiService.updateTask(task.id, updatedTask).subscribe({
      next: () => {
        this.getAllTasks();
      },
      error: (error) => {
        console.error('Error updating task status', error);
        this.errorMessage = 'Erro ao atualizar o status da tarefa.';
      }
    });
  }

  applyFilter(): void {
    let tempTasks = [...this.tasks];

    if (this.filterByStatus) {
      tempTasks = tempTasks.filter(task => task.status === this.filterByStatus);
    }

    if (this.filterByCreationDate) {
      tempTasks = tempTasks.filter(task => task.creationDate === this.filterByCreationDate);
    }

    if (this.filterByDueDate) {
      tempTasks = tempTasks.filter(task => task.dueDate === this.filterByDueDate);
    }

    this.filteredTasks = tempTasks;
  }
}
