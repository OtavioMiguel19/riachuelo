package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.exceptions.TaskNotFoundException;
import br.com.riachuelo.desafio_otavio.usecases.ports.UpdateTask;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateTaskFacade implements UpdateTask {
    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;

    public UpdateTaskFacade(TaskRepository taskRepository, AuthenticationService authenticationService) {
        this.taskRepository = taskRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Task update(long id, Task task) {
        final UserModel currentUser = authenticationService.getLoggedUser();
        final Optional<TaskModel> optionalTaskModel = taskRepository.findByIdAndUserId(id, currentUser.getId());
        if (optionalTaskModel.isEmpty()) {
            throw new TaskNotFoundException("No task was found with id " + id);
        }
        final TaskModel taskModel = optionalTaskModel.get();

        taskModel.setTitle(task.title());
        taskModel.setDescription(task.description());
        taskModel.setDueDate(task.dueDate());
        taskModel.setStatus(task.status().name());

        return taskRepository.save(taskModel).toTask();
    }
}
