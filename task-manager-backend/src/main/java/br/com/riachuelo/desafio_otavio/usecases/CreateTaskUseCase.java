package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.CreateTask;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskUseCase {
    private final CreateTask createTask;

    public CreateTaskUseCase(final CreateTask createTask) {
        this.createTask = createTask;
    }

    public Task create(final Task task) {
        return createTask.create(task);
    }
}
