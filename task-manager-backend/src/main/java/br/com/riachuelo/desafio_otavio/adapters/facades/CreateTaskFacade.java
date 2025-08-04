package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.ports.CreateTask;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateTaskFacade implements CreateTask {
    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;

    public CreateTaskFacade(TaskRepository taskRepository, AuthenticationService authenticationService) {
        this.taskRepository = taskRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Task create(Task task) {
        final UserModel user = authenticationService.getLoggedUser();
        final TaskModel taskModel = TaskModel.from(task);
        taskModel.setUser(user);
        taskModel.setCreationDate(LocalDate.now());
        return taskRepository.save(taskModel).toTask();
    }
}
