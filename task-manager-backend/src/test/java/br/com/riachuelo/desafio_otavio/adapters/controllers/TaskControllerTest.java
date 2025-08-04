package br.com.riachuelo.desafio_otavio.adapters.controllers;

import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.TaskRequestDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.TaskResponseDTO;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.domains.TaskStatus;
import br.com.riachuelo.desafio_otavio.usecases.CreateTaskUseCase;
import br.com.riachuelo.desafio_otavio.usecases.DeleteTaskUseCase;
import br.com.riachuelo.desafio_otavio.usecases.RetrieveTasksUseCase;
import br.com.riachuelo.desafio_otavio.usecases.UpdateTaskUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private RetrieveTasksUseCase retrieveTasksUseCase;

    @Mock
    private UpdateTaskUseCase updateTaskUseCase;

    @Mock
    private DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private TaskRequestDTO taskRequestDTO;


    @Test
    void createTask_ReturnsCreatedTaskResponse() {
        Task taskDomain = Task.builder()
                .id(1L)
                .title("Título teste")
                .description("Descrição teste")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(5))
                .status(TaskStatus.PENDING)
                .build();

        when(taskRequestDTO.toDomain()).thenReturn(taskDomain);
        TaskResponseDTO taskResponseDTO = TaskResponseDTO.fromTask(taskDomain);

        when(createTaskUseCase.create(taskDomain)).thenReturn(taskDomain);

        ResponseEntity<TaskResponseDTO> response = taskController.createTask(taskRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(taskResponseDTO, response.getBody());

        verify(createTaskUseCase).create(taskDomain);
    }

    @Test
    void getTask_ReturnsSingleTaskResponse() {
        long taskId = 10L;

        Task taskDomain = Task.builder()
                .id(taskId)
                .title("Título teste")
                .description("Descrição teste")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(5))
                .status(TaskStatus.PENDING)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.fromTask(taskDomain);

        when(retrieveTasksUseCase.retrieveSingle(taskId)).thenReturn(taskDomain);

        ResponseEntity<TaskResponseDTO> response = taskController.getTask(taskId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(taskResponseDTO, response.getBody());

        verify(retrieveTasksUseCase).retrieveSingle(taskId);
    }

    @Test
    void getAllTasks_ReturnsListOfTaskResponses() {
        Task task1 = Task.builder()
                .id(1L)
                .title("Título 1")
                .description("Descrição 1")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(3))
                .status(TaskStatus.PENDING)
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .title("Título 2")
                .description("Descrição 2")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(4))
                .status(TaskStatus.COMPLETED)
                .build();

        List<Task> taskList = List.of(task1, task2);
        List<TaskResponseDTO> expectedResponseList = taskList.stream()
                .map(TaskResponseDTO::fromTask)
                .collect(Collectors.toList());

        when(retrieveTasksUseCase.retrieveAllForCurrentUser()).thenReturn(taskList);

        ResponseEntity<java.util.Collection<TaskResponseDTO>> response = taskController.getAllTasks();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponseList, new ArrayList<>(Objects.requireNonNull(response.getBody())));

        verify(retrieveTasksUseCase).retrieveAllForCurrentUser();
    }

    @Test
    void updateTask_ReturnsUpdatedTaskResponse() {
        long taskId = 15L;

        Task taskDomain = Task.builder()
                .id(taskId)
                .title("Título atualizado")
                .description("Descrição atualizada")
                .creationDate(LocalDate.now().minusDays(1))
                .dueDate(LocalDate.now().plusDays(10))
                .status(TaskStatus.IN_PROGRESS)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.fromTask(taskDomain);

        when(taskRequestDTO.toDomain()).thenReturn(taskDomain);
        when(updateTaskUseCase.update(taskId, taskDomain)).thenReturn(taskDomain);

        ResponseEntity<TaskResponseDTO> response = taskController.updateTask(taskId, taskRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(taskResponseDTO, response.getBody());

        verify(updateTaskUseCase).update(taskId, taskDomain);
    }

    @Test
    void deleteTask_ReturnsNoContent() {
        long taskId = 20L;

        doNothing().when(deleteTaskUseCase).deleteTask(taskId);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(deleteTaskUseCase).deleteTask(taskId);
    }
}