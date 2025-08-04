package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.domains.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveAllTasksForCurrentUserFacadeTest {

    @InjectMocks
    private RetrieveAllTasksForCurrentUserFacade retrieveAllTasksForCurrentUserFacade;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserModel currentUser;

    @Mock
    private TaskModel taskModel1;

    @Mock
    private TaskModel taskModel2;

    @Mock
    private Task task1;

    @Mock
    private Task task2;

    @Test
    void testRetrieveAllForCurrentUserShouldReturnConvertedTasks() {
        long userId = 123L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(userId);
        when(taskRepository.findAllByUserIdOrderByIdAsc(userId)).thenReturn(List.of(taskModel1, taskModel2));
        when(taskModel1.toTask()).thenReturn(task1);
        when(taskModel2.toTask()).thenReturn(task2);

        List<Task> result = retrieveAllTasksForCurrentUserFacade.retrieveAllForCurrentUser();

        verify(taskRepository).findAllByUserIdOrderByIdAsc(userId);
        assertEquals(List.of(task1, task2), result);
    }

    @Test
    void testRetrieveAllForCurrentUserShouldReturnEmptyListIfNoTasksFound() {
        long userId = 123L;

        when(authenticationService.getLoggedUser()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(userId);
        when(taskRepository.findAllByUserIdOrderByIdAsc(userId)).thenReturn(List.of());

        List<Task> result = retrieveAllTasksForCurrentUserFacade.retrieveAllForCurrentUser();

        verify(taskRepository).findAllByUserIdOrderByIdAsc(userId);
        assertTrue(result.isEmpty());
    }
}