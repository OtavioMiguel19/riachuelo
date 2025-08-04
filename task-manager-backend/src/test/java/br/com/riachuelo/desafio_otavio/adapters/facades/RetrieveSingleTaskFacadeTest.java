package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveSingleTaskFacadeTest {

    @InjectMocks
    private RetrieveSingleTaskFacade retrieveSingleTaskFacade;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserModel currentUser;

    @Mock
    private TaskModel taskModel;

    @Mock
    private Task task;

    @Test
    void testRetrieveShouldReturnTaskIfExists() {
        long taskId = 10L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(123L);
        when(taskRepository.findByIdAndUserId(taskId, 123L)).thenReturn(Optional.of(taskModel));
        when(taskModel.toTask()).thenReturn(task);

        Task result = retrieveSingleTaskFacade.retrieve(taskId);

        verify(taskRepository).findByIdAndUserId(taskId, 123L);
        verify(taskModel).toTask();
        assertEquals(task, result);
    }

    @Test
    void testRetrieveShouldThrowExceptionIfTaskNotFound() {
        long taskId = 10L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(123L);
        when(taskRepository.findByIdAndUserId(taskId, 123L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> retrieveSingleTaskFacade.retrieve(taskId));

        assertEquals("No task was found with id " + taskId, exception.getMessage());
    }
}