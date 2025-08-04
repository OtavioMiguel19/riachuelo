package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.domains.TaskStatus;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTaskFacadeTest {

    @InjectMocks
    private UpdateTaskFacade updateTaskFacade;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserModel currentUser;

    @Mock
    private TaskModel existingTaskModel;

    @Mock
    private Task updatedTask;

    @Test
    void testUpdateShouldUpdateTaskAndReturnUpdatedTask() {
        long taskId = 42L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(123L);

        when(taskRepository.findByIdAndUserId(taskId, 123L)).thenReturn(Optional.of(existingTaskModel));

        when(updatedTask.title()).thenReturn("New Title");
        when(updatedTask.description()).thenReturn("New Description");
        when(updatedTask.dueDate()).thenReturn(LocalDate.of(2025, 12, 31));
        when(updatedTask.status()).thenReturn(TaskStatus.COMPLETED);

        Task expectedTaskDomain = Task.builder()
                .title("New Title")
                .description("New Description")
                .dueDate(LocalDate.of(2025, 12, 31))
                .status(TaskStatus.COMPLETED)
                .build();
        when(existingTaskModel.toTask()).thenReturn(expectedTaskDomain);
        when(taskRepository.save(existingTaskModel)).thenReturn(existingTaskModel);

        Task result = updateTaskFacade.update(taskId, updatedTask);

        verify(taskRepository).findByIdAndUserId(taskId, 123L);
        verify(existingTaskModel).setTitle("New Title");
        verify(existingTaskModel).setDescription("New Description");
        verify(existingTaskModel).setDueDate(LocalDate.of(2025, 12, 31));
        verify(existingTaskModel).setStatus(TaskStatus.COMPLETED.name());
        verify(taskRepository).save(existingTaskModel);

        assertEquals(expectedTaskDomain, result);
    }

    @Test
    void testUpdateShouldThrowWhenTaskNotFound() {
        long taskId = 42L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(123L);
        when(taskRepository.findByIdAndUserId(taskId, 123L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> updateTaskFacade.update(taskId, updatedTask));

        assertEquals("No task was found with id " + taskId, exception.getMessage());
    }
}