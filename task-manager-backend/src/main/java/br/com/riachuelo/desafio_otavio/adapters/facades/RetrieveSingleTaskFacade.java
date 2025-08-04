package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveSingleTask;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RetrieveSingleTaskFacade implements RetrieveSingleTask {
    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;

    public RetrieveSingleTaskFacade(TaskRepository taskRepository, AuthenticationService authenticationService) {
        this.taskRepository = taskRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Task retrieve(long id) {
        final UserModel currentUser = authenticationService.getLoggedUser();
        final Optional<TaskModel> optionalTaskModel = taskRepository.findByIdAndUserId(id, currentUser.getId());
        if (optionalTaskModel.isEmpty()) {
            throw new TaskNotFoundException("No task was found with id " + id);
        }
        return optionalTaskModel.get().toTask();
    }
}
