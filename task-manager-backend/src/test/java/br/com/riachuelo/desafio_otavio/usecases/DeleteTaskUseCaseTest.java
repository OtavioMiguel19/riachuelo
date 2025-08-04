package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.usecases.ports.DeleteTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseTest {

    @InjectMocks
    private DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private DeleteTask deleteTask;

    @Test
    void testDeleteTaskShouldDelegateToDeleteTaskPort() {
        long taskId = 9876L;

        deleteTaskUseCase.deleteTask(taskId);

        verify(deleteTask).deleteTask(taskId);
    }
}