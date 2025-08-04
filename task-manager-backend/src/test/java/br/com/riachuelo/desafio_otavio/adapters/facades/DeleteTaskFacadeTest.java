package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskFacadeTest {

    @InjectMocks
    private DeleteTaskFacade deleteTaskFacade;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserModel userModel;

    @Mock
    private TaskModel taskModel;

    @Test
    void testDeleteTaskSuccessfully() {
        long taskId = 1L;
        long userId = 10L;

        when(authenticationService.getLoggedUser()).thenReturn(userModel);
        when(userModel.getId()).thenReturn(userId);
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.of(taskModel));
        when(taskModel.getId()).thenReturn(taskId);

        deleteTaskFacade.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testDeleteTaskWhenTaskNotFoundShouldThrowException() {
        long taskId = 1L;
        long userId = 10L;

        when(authenticationService.getLoggedUser()).thenReturn(userModel);
        when(userModel.getId()).thenReturn(userId);
        when(taskRepository.findByIdAndUserId(taskId, userId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            deleteTaskFacade.deleteTask(taskId);
        });

        assertEquals("No task was found with id " + taskId, exception.getMessage());
        verify(taskRepository, never()).deleteById(anyLong());
    }
}