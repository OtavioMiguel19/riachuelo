package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.domains.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskFacadeTest {

    @InjectMocks
    private CreateTaskFacade createTaskFacade;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserModel userModel;

    @Test
    void testCreateTaskSuccessfully() {
        Task inputTask = Task.builder()
                .title("Título teste")
                .description("Descrição teste")
                .creationDate(null)
                .dueDate(LocalDate.of(2025, 8, 10))
                .status(TaskStatus.PENDING)
                .build();

        TaskModel savedModel = mock(TaskModel.class);

        Task expectedTask = Task.builder()
                .id(1L)
                .title("Título teste")
                .description("Descrição teste")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.of(2025, 8, 10))
                .status(TaskStatus.PENDING)
                .build();

        when(authenticationService.getLoggedUser()).thenReturn(userModel);
        when(taskRepository.save(argThat(model ->
                model.getUser() == userModel &&
                        model.getTitle().equals("Título teste") &&
                        model.getDescription().equals("Descrição teste") &&
                        model.getDueDate().equals(LocalDate.of(2025, 8, 10)) &&
                        model.getCreationDate().equals(LocalDate.now())
        ))).thenReturn(savedModel);
        when(savedModel.toTask()).thenReturn(expectedTask);

        Task result = createTaskFacade.create(inputTask);

        assertEquals(expectedTask, result);
        verify(authenticationService).getLoggedUser();
        verify(taskRepository).save(any(TaskModel.class));
        verify(savedModel).toTask();
    }
}