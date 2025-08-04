package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveAllTasksForCurrentUser;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveSingleTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveTasksUseCaseTest {

    @InjectMocks
    private RetrieveTasksUseCase retrieveTasksUseCase;

    @Mock
    private RetrieveSingleTask retrieveSingleTask;

    @Mock
    private RetrieveAllTasksForCurrentUser retrieveAllTasksForCurrentUser;

    @Mock
    private Task task;

    @Test
    void testRetrieveSingleCallsRetrieveSingleTask() {
        long taskId = 1234L;

        when(retrieveSingleTask.retrieve(taskId)).thenReturn(task);

        Task result = retrieveTasksUseCase.retrieveSingle(taskId);

        verify(retrieveSingleTask).retrieve(taskId);
        assertEquals(task, result);
    }

    @Test
    void testRetrieveAllForCurrentUserCallsRetrieveAllTasksForCurrentUser() {
        when(retrieveTasksUseCase.retrieveAllForCurrentUser()).thenReturn(List.of(this.task));

        List<Task> result = retrieveTasksUseCase.retrieveAllForCurrentUser();

        verify(retrieveAllTasksForCurrentUser).retrieveAllForCurrentUser();
        assertEquals(List.of(this.task), result);
    }
}