package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.UpdateTask;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {
    private final UpdateTask updateTask;

    public UpdateTaskUseCase(final UpdateTask updateTask) {
        this.updateTask = updateTask;
    }

    public Task update(long id, Task task) {
        return updateTask.update(id, task);
    }
}
