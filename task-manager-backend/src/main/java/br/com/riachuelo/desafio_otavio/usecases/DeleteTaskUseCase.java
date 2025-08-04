package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.usecases.ports.DeleteTask;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {
    private final DeleteTask deleteTask;

    public DeleteTaskUseCase(DeleteTask deleteTask) {
        this.deleteTask = deleteTask;
    }

    public void deleteTask(long id) {
        deleteTask.deleteTask(id);
    }

}
