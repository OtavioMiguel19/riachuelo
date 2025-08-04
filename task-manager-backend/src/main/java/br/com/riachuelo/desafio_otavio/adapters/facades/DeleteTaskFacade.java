package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import br.com.riachuelo.desafio_otavio.usecases.ports.DeleteTask;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteTaskFacade implements DeleteTask {
    private final AuthenticationService authenticationService;
    private final TaskRepository taskRepository;
    public DeleteTaskFacade(AuthenticationService authenticationService, TaskRepository taskRepository) {
        this.authenticationService = authenticationService;
        this.taskRepository = taskRepository;
    }
    @Override
    public void deleteTask(long id) {
        final UserModel userModel = authenticationService.getLoggedUser();
        final Optional<TaskModel> optionalTaskModel = taskRepository.findByIdAndUserId(id, userModel.getId());
        if (optionalTaskModel.isEmpty()) {
            throw new TaskNotFoundException("No task was found with id " + id);
        }
        taskRepository.deleteById(optionalTaskModel.get().getId());
    }
}
