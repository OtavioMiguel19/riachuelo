export interface TaskRequestDTO {
  title: string;
  description: string;
  dueDate: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
}

export interface TaskResponseDTO {
  id: number;
  title: string;
  description: string;
  creationDate: string;
  dueDate: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED';
}
