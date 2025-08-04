package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.CreateTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {

    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private CreateTask createTask;

    @Mock
    private Task task;

    @Test
    void testCreateShouldDelegateToCreateTaskPortAndReturnResult() {
        when(createTask.create(task)).thenReturn(task);

        Task result = createTaskUseCase.create(task);

        verify(createTask).create(task);
        assertEquals(task, result);
    }
}