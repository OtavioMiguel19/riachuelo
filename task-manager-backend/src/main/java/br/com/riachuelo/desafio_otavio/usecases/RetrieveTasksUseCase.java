package br.com.riachuelo.desafio_otavio.usecases;

import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveAllTasksForCurrentUser;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveSingleTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveTasksUseCase {
    private final RetrieveSingleTask retrieveSingleTask;
    private final RetrieveAllTasksForCurrentUser retrieveAllTasksForCurrentUser;

    public RetrieveTasksUseCase(RetrieveSingleTask retrieveSingleTask, RetrieveAllTasksForCurrentUser retrieveAllTasksForCurrentUser) {
        this.retrieveSingleTask = retrieveSingleTask;
        this.retrieveAllTasksForCurrentUser = retrieveAllTasksForCurrentUser;
    }

    public Task retrieveSingle(long id) {
        return retrieveSingleTask.retrieve(id);
    }

    public List<Task> retrieveAllForCurrentUser() {
        return retrieveAllTasksForCurrentUser.retrieveAllForCurrentUser();
    }
}
