package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.UpdateTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateTaskUseCaseTest {
    @InjectMocks
    private UpdateTaskUseCase updateTaskUseCase;
    @Mock
    private UpdateTask updateTask;
    @Mock
    private Task task;

    @Test
    void testWillSendToUpdateTask() {
        updateTaskUseCase.update(1234L, task);

        verify(updateTask).update(1234L, task);
    }
}