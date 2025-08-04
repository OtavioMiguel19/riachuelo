package br.com.riachuelo.desafio_otavio.usecases.ports;

import br.com.riachuelo.desafio_otavio.domains.Task;

import java.util.List;

public interface RetrieveAllTasksForCurrentUser {
    List<Task> retrieveAllForCurrentUser();
}
