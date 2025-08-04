import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginUserDTO, LoginResponseDTO, RegisterUserDTO, UserDTO } from '../models/user.model';
import { TaskRequestDTO, TaskResponseDTO } from '../models/task.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt_token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    });
  }

  login(credentials: LoginUserDTO): Observable<LoginResponseDTO> {
    return this.http.post<LoginResponseDTO>(`${this.apiUrl}/auth/login`, credentials);
  }

  register(user: RegisterUserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/auth/register`, user);
  }

  getAllTasks(): Observable<TaskResponseDTO[]> {
    return this.http.get<TaskResponseDTO[]>(`${this.apiUrl}/tasks`, { headers: this.getAuthHeaders() });
  }

  getTask(id: number): Observable<TaskResponseDTO> {
    return this.http.get<TaskResponseDTO>(`${this.apiUrl}/tasks/${id}`, { headers: this.getAuthHeaders() });
  }

  createTask(task: TaskRequestDTO): Observable<TaskResponseDTO> {
    return this.http.post<TaskResponseDTO>(`${this.apiUrl}/tasks`, task, { headers: this.getAuthHeaders() });
  }

  updateTask(id: number, task: TaskRequestDTO): Observable<TaskResponseDTO> {
    return this.http.put<TaskResponseDTO>(`${this.apiUrl}/tasks/${id}`, task, { headers: this.getAuthHeaders() });
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/tasks/${id}`, { headers: this.getAuthHeaders() });
  }
}
