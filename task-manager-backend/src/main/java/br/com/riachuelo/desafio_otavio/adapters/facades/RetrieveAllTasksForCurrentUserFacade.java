package br.com.riachuelo.desafio_otavio.adapters.facades;

import br.com.riachuelo.desafio_otavio.adapters.models.TaskModel;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.TaskRepository;
import br.com.riachuelo.desafio_otavio.domains.Task;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.usecases.ports.RetrieveAllTasksForCurrentUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetrieveAllTasksForCurrentUserFacade implements RetrieveAllTasksForCurrentUser {
    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;

    public RetrieveAllTasksForCurrentUserFacade(TaskRepository taskRepository, AuthenticationService authenticationService) {
        this.taskRepository = taskRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Task> retrieveAllForCurrentUser() {
        final UserModel currentUser = authenticationService.getLoggedUser();
        final List<TaskModel> taskModels = taskRepository.findAllByUserIdOrderByIdAsc(currentUser.getId());
        return taskModels.stream().map(TaskModel::toTask).collect(Collectors.toList());
    }
}
