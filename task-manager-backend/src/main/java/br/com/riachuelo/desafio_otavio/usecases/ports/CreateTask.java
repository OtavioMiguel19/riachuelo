package br.com.riachuelo.desafio_otavio.usecases.ports;

import br.com.riachuelo.desafio_otavio.domains.Task;

public interface CreateTask {
    Task create(final Task task);
}
